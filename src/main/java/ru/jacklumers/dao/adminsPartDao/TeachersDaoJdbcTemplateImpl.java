package ru.jacklumers.dao.adminsPartDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.jacklumers.models.DatedLesson;
import ru.jacklumers.models.Student;
import ru.jacklumers.models.Teacher;
import ru.jacklumers.utils.SqlSelectQueryGenerator;

import javax.sql.DataSource;
import java.util.*;

/**
 * Реализация методов доступа к данным об учителях через JDBC Template
 *
 * @see org.springframework.jdbc.core.JdbcTemplate
 */
public class TeachersDaoJdbcTemplateImpl implements TeachersDao {

    //language=SQL
    private final String SQL_SELECT_ALL =
            "SELECT teacher.*, student.*, dated_lesson_id, dated_lesson_date " +
                    "FROM teacher " +
                    "LEFT JOIN dated_lesson ON teacher.teacher_id = dated_lesson.teacher_id " +
                    "LEFT JOIN student ON student.student_id = dated_lesson.student_id";

    //language=SQL
    private final String SQL_SELECT_BY_ID =
            "SELECT teacher.*, student.*, dated_lesson_id, dated_lesson_date " +
                    "FROM teacher " +
                    "LEFT JOIN dated_lesson ON teacher.teacher_id = dated_lesson.teacher_id " +
                    "LEFT JOIN student ON student.student_id = dated_lesson.student_id " +
                    "WHERE teacher.teacher_id = ?";

    //language=SQL
    private final String SQL_SELECT_BY_FULL_NAME =
            "SELECT teacher.*, student.*, dated_lesson_id, dated_lesson_date " +
                    "FROM teacher " +
                    "LEFT JOIN dated_lesson ON teacher.teacher_id = dated_lesson.teacher_id " +
                    "LEFT JOIN student ON student.student_id = dated_lesson.student_id " +
                    "WHERE (teacher.teacher_first_name, teacher.teacher_last_name) = (?, ?)";

    //language=SQL
    private final String SQL_DELETE_BY_ID =
            "DELETE FROM teacher WHERE teacher_id = ?";

    //language=SQL
    private final String SQL_INSERT_TEACHER =
            "INSERT INTO teacher (teacher_first_name, teacher_last_name) " +
                    "VALUES (?, ?)";

    //language=SQL
    private final String SQL_UPDATE_TEACHER_BY_ID =
            "UPDATE teacher " +
                    "SET (teacher_first_name, teacher_last_name) = (?, ?) " +
                    "WHERE teacher_id = ?";

    private JdbcTemplate jdbcTemplate;
    private DatedLessonsDao datedLessonsDao;

    /* Map'ы для временного хранения отображенных сущностей.
     * Очищаются после отображения ResultSet'а одной транзакции,
     * чтобы всегда содержать актуальные данные */
    private Map<Long, Teacher> teacherMap = new HashMap<>();
    private Map<Long, DatedLesson> datedLessonMap = new HashMap<>();

    private RowMapper<Teacher> rowMapper = (rs, rowNum) -> {
        Long teacherId = rs.getLong("teacher_id");
        Long datedLessonId = rs.getLong("dated_lesson_id");

        Teacher teacher;

        // Если есть у преподавателя есть назначенное занятие,
        // то отображаем его с учеником этого занятия
        if (datedLessonId != 0) {
            DatedLesson datedLesson;
            /* Проверка, был ли отображен урок до этого.
             * Будет работать в случае обработки группы преподавателей
             * по типу "все преподаватели с данным назначенным занятием" */
            if (!datedLessonMap.containsKey(datedLessonId)) {
                Optional<DatedLesson> datedLessonOptional = datedLessonsDao.find(datedLessonId);
                datedLesson = datedLessonOptional.get();
                datedLessonMap.put(datedLessonId, datedLesson);

            }
            /* Если этот урок уже был отображен до этого, то можно его использовать повторно. */
            else {
                datedLesson = datedLessonMap.get(datedLessonId);
            }

            teacher = new Teacher(
                    teacherId,
                    rs.getString("teacher_first_name"),
                    rs.getString("teacher_last_name"),
                    new HashMap<DatedLesson, Student>());
            teacher.getDatedLessons().put(datedLesson, datedLesson.getStudent());
            teacherMap.put(teacherId, teacher);
            return teacher;
        }
        // Иначе отображаем преподавателя без назначенных уроков
        else {
            // Если преподаватель еще не был отображен, то отображаем и добавляем в Map
            if (!teacherMap.containsKey(teacherId)) {
                teacher = new Teacher(
                        teacherId,
                        rs.getString("teacher_first_name"),
                        rs.getString("teacher_last_name"),
                        new HashMap<DatedLesson, Student>());
                teacherMap.put(teacherId, teacher);
                return teacher;
            }
            // Идеальный случай, когда у преподавателя нет назначенных уроков и он уже был отображен ранее
            else {
                return teacherMap.get(teacherId);
            }
        }
    };

    /**
     * RawMapper, предназначенный для отображения сущности,
     * включая только её собственные атрибуты. Т.е. без отображения свзяей с другими сущностями.
     */
    private RowMapper<Teacher> onlySelfAttributesRowMapper = (rs, rawNum) -> {
        //тело mapRow
        return new Teacher(rs.getLong("teacher_id"),
                rs.getString("teacher_first_name"),
                rs.getString("teacher_last_name"));
    };

    /**
     * Конструктор DAO с подключением к базе данных через интерфейс DataSource.
     * SQLException отлавливется в JdbcTemplate
     *
     * @param dataSource - фабрика, позволяющая получить источник данных,
     *                   который эта DataSource представляет.
     */
    public TeachersDaoJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        datedLessonsDao = new DatedLessonsJdbcTemplateImpl(dataSource);
    }

    @Override
    public Optional<Teacher> find(Long id) {
        jdbcTemplate.query(SQL_SELECT_BY_ID, onlySelfAttributesRowMapper, id);
        return getMappedValueAndCleanMaps(id);
    }

    @Override
    public Optional<Teacher> findByFullName(String firstName, String lastName) {
        jdbcTemplate.query(SQL_SELECT_BY_FULL_NAME, rowMapper, firstName, lastName);
        try{
            Teacher teacher = getAllMappedValuesAndCleanMaps().get(0);
            return Optional.ofNullable(teacher);
        } catch (IndexOutOfBoundsException e){ // Список оказался пустой, преподаватель не найден
            return Optional.empty();
        }
    }

    @Override
    public List<Teacher> findAll() {
        jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
        return getAllMappedValuesAndCleanMaps();
    }

    @Override
    public List<Teacher> findAllWithOnlySelfAttributesByArgs(Map<String, String> columnsAndArgs) {
        return jdbcTemplate.query(SqlSelectQueryGenerator.generateSelectQuery("teacher", columnsAndArgs), onlySelfAttributesRowMapper);
    }

    @Override
    public void save(Teacher teacher) {
        jdbcTemplate.update(SQL_INSERT_TEACHER,
                teacher.getFirstName(),
                teacher.getLastName());
    }

    @Override
    public void update(Teacher teacherWithId) {
        if (teacherWithId.getId() == null) {
            throw new NullPointerException
                    ("This model object has no id, but you are trying to update entity by it's id");
        } else {
            jdbcTemplate.update(SQL_UPDATE_TEACHER_BY_ID,
                    teacherWithId.getId(),
                    teacherWithId.getFirstName(),
                    teacherWithId.getLastName());
        }
    }

    //TODO: Проверить работу удаления
    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }


    private List<Teacher> getAllMappedValuesAndCleanMaps() {
        List<Teacher> teacherList = new ArrayList<>(teacherMap.values());
        teacherMap.clear();
        datedLessonMap.clear();
        return teacherList;
    }

    private Optional<Teacher> getMappedValueAndCleanMaps(Long id) {
        Optional<Teacher> optionalTeacher = Optional.ofNullable(teacherMap.get(id));
        teacherMap.clear();
        datedLessonMap.clear();
        return optionalTeacher;
    }
}
