package ru.jacklumers.dao.adminsPartDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.jacklumers.models.DatedLesson;
import ru.jacklumers.models.Student;
import ru.jacklumers.models.Teacher;
import ru.jacklumers.utils.SqlSelectQueryGenerator;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.*;

/**
 * Реализация методов доступа к данным об учениках через JDBC Template.
 * <p>
 * JdbcTemplate - прослойка между чистым JDBC и бизнес-логикой приложения.
 * Она содержит готовые шаблоны для работы с базой данных, что упрощает
 * написание кода.
 */
public class StudentsDaoJdbcTemplateImpl implements StudentsDao {

    //language=SQL
    private final String SQL_SELECT_ALL =
            "SELECT student.*, teacher.*, dated_lesson_id, dated_lesson_date FROM student " +
                    "LEFT JOIN dated_lesson ON student.student_id = dated_lesson.student_id " +
                    "LEFT JOIN teacher ON dated_lesson.teacher_id = teacher.teacher_id";

    //language=SQL
    private final String SQL_SELECT_BY_ID =
            "SELECT student.*, teacher.*, dated_lesson_id, dated_lesson_date FROM student " +
                    "LEFT JOIN dated_lesson ON student.student_id = dated_lesson.student_id " +
                    "LEFT JOIN teacher ON dated_lesson.teacher_id = teacher.teacher_id " +
                    "WHERE student.student_id = ?";

    //language=SQL
    private final String SQL_SELECT_BY_FULL_NAME =
            "SELECT student.*, teacher.*, dated_lesson_id, dated_lesson_date FROM student " +
                    "LEFT JOIN dated_lesson ON student.student_id = dated_lesson.student_id " +
                    "LEFT JOIN teacher ON dated_lesson.teacher_id = teacher.teacher_id " +
                    "WHERE (student.student_first_name, student.student_last_name) = (?, ?)";

    //language=SQL
    private final String SQL_SELECT_BY_LEARNING_RATE =
            "SELECT student.*, teacher.*, dated_lesson_id, dated_lesson_date FROM student " +
                    "LEFT JOIN dated_lesson ON student.student_id = dated_lesson.student_id " +
                    "LEFT JOIN teacher ON dated_lesson.teacher_id = teacher.teacher_id " +
                    "WHERE student.student_learning_rate = ?";

    //language=SQL
    private final String SQL_DELETE_BY_ID =
            "DELETE FROM student WHERE student_id = ?";

    //language=SQL
    private final String SQL_INSERT_STUDENT =
            "INSERT INTO student (student_first_name, student_last_name, student_phone, student_city, " +
                    "student_street, student_house_num, student_corps, student_apartment_num, student_learning_rate) " +
                    "VALUES (?,?,?,?,?,?,?,?,?)";

    //language=SQL
    private final String SQL_UPDATE_STUDENT_BY_ID =
            "UPDATE student SET (student_first_name, student_last_name, student_phone, student_city, " +
                    "student_street, student_house_num, student_corps, student_apartment_num, student_learning_rate) " +
                    "= (?,?,?,?,?,?,?,?,?) WHERE student_id = ?";

    private JdbcTemplate jdbcTemplate;
    private DatedLessonsDao datedLessonsDao;

    /* Map'ы для временного хранения отображенных сущностей.
     * Очищаются после отображения ResultSet'а одной транзакции,
     * чтобы всегда содержать актуальные данные */
    private Map<Long, Student> studentMap = new HashMap<>();
    private Map<Long, DatedLesson> datedLessonMap = new HashMap<>();

    /**
     * Анонимный класс.
     * Интерфейс RawMapper позволяет описать правило отображения значений строк из ResultSet в Java-объект,
     * при этом не вызывать {@code next()}, т.е. не писать всё в
     * цикле и нет необходимости проверки SQLException в блоке try/catch.
     * Так же этот маппинг можно использовать повторно при любых запросах к таблице учеников.
     * Всё это делает JdbcTemplate из Spring Framework.
     * <p>
     * Еще здесь использовано лямбда-выражение.
     * rs и rowNum - это параметры функции mapRow,
     * объявление которой находится в интерфейсе RawMapper
     */
    private RowMapper<Student> rowMapper = (rs, rowNum) -> {
        Long studentId = rs.getLong("student_id");
        Long datedLessonId = rs.getLong("dated_lesson_id");

        Student student;

        // Если есть у ученика есть назначенное занятие,
        // то отображаем его с преподавателем этого занятия
        if (datedLessonId != 0) {
            DatedLesson datedLesson;
            /* Проверка, был ли отображен урок до этого.
             * Будет работать в случае обработки группы студентов
             * по типу "все студенты с данным назначенным занятием" */
            if (!datedLessonMap.containsKey(datedLessonId)) {
                Optional<DatedLesson> datedLessonOptional = datedLessonsDao.find(datedLessonId);
                datedLesson = datedLessonOptional.get();
                datedLessonMap.put(datedLessonId, datedLesson);

            }
            /* Если этот урок уже был обработан до этого, то можно его использовать повторно. */
            else {
                datedLesson = datedLessonMap.get(datedLessonId);
            }

            student = new Student(
                    studentId,
                    rs.getString("student_first_name"),
                    rs.getString("student_last_name"),
                    rs.getString("student_phone"),
                    rs.getString("student_city"),
                    rs.getString("student_street"),
                    rs.getString("student_house_num"),
                    rs.getString("student_corps"),
                    rs.getString("student_apartment_num"),
                    rs.getFloat("student_learning_rate"),
                    new HashMap<DatedLesson, Teacher>());
            student.getDatedLessons().put(datedLesson, datedLesson.getTeacher());
            studentMap.put(studentId, student);
            return student;
        }
        // Иначе отображаем ученика без назначенных уроков
        else {
            // Если ученик еще не был отображен, то отображаем и добавляем в Map
            if (!studentMap.containsKey(studentId)) {
                student = new Student(
                        studentId,
                        rs.getString("student_first_name"),
                        rs.getString("student_last_name"),
                        rs.getString("student_phone"),
                        rs.getString("student_city"),
                        rs.getString("student_street"),
                        rs.getString("student_house_num"),
                        rs.getString("student_corps"),
                        rs.getString("student_apartment_num"),
                        rs.getFloat("student_learning_rate"),
                        new HashMap<DatedLesson, Teacher>());
                studentMap.put(studentId, student);
                return student;
            }
            // Идеальный случай, когда у ученика нет назначенных уроков и он уже был отображен ранее
            else {
                return studentMap.get(studentId);
            }
        }
    };

    /**
     * RawMapper, предназначенный для отображения сущности,
     * включая только её собственные атрибуты. Т.е. без отображения свзяей с другими сущностями.
     */
    private RowMapper<Student> onlySelfAttributesRawMapper = (rs, rowNum) -> {
        return new Student(
                rs.getLong("student_id"),
                rs.getString("student_first_name"),
                rs.getString("student_last_name"),
                rs.getString("student_phone"),
                rs.getString("student_city"),
                rs.getString("student_street"),
                rs.getString("student_house_num"),
                rs.getString("student_corps"),
                rs.getString("student_apartment_num"),
                rs.getFloat("student_learning_rate"));
    };

    /**
     * Конструктор DAO с подключением к базе данных через интерфейс DataSource.
     * SQLException отлавливется в JdbcTemplate
     *
     * @param dataSource - фабрика, позволяющая получить источник данных,
     *                   который эта DataSource представляет.
     */
    public StudentsDaoJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        datedLessonsDao = new DatedLessonsJdbcTemplateImpl(dataSource);
    }

    @Override
    public List<Student> findAll() {
        jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
        return getAllMappedValuesAndCleanMaps();
    }


    @Override
    public Optional<Student> find(Long id) {
        jdbcTemplate.query(SQL_SELECT_BY_ID, rowMapper, id);
        return getMappedValueAndCleanMaps(id);
    }

    @Override
    public Optional<Student> findByFullName(String firstName, String lastName) {
        jdbcTemplate.query(SQL_SELECT_BY_FULL_NAME, rowMapper, firstName, lastName);
        try {
            Student student = getAllMappedValuesAndCleanMaps().get(0);
            return Optional.ofNullable(student);
        } catch (IndexOutOfBoundsException e) { // Список оказался пустой, ученик не найден
            return Optional.empty();
        }
    }

    @Override
    public List<Student> findAllByLearningRate(Float learningRate) {
        jdbcTemplate.query(SQL_SELECT_BY_LEARNING_RATE, rowMapper, learningRate);
        return getAllMappedValuesAndCleanMaps();
    }

    @Override
    public List<Student> findAllByOrder(String[] orderColumns) {
        jdbcTemplate.query(SqlSelectQueryGenerator.addOrderToQuery(SQL_SELECT_ALL, orderColumns), rowMapper);
        return getAllMappedValuesAndCleanMaps();
    }

    @Override
    public List<Student> findAllWithOnlySelfAttributesByArguments(Map<String, String> columnsAndArgs) {
        return jdbcTemplate.query(SqlSelectQueryGenerator.generateSelectQuery("student", columnsAndArgs), onlySelfAttributesRawMapper);
    }

    @Override
    public void save(Student student) {
        jdbcTemplate.update(SQL_INSERT_STUDENT,
                student.getFirstName(),
                student.getLastName(),
                student.getPhone(),
                student.getCity(),
                student.getStreet(),
                student.getHouseNum(),
                student.getCorps(),
                student.getApartmentNum(),
                student.getLearningRate());
    }

    @Override
    public void update(Student studentWithId) {
        if (studentWithId.getId() == null) {
            throw new NullPointerException
                    ("This model object has no id, but you are trying to update entity by it's id");
        } else {
            jdbcTemplate.update(SQL_UPDATE_STUDENT_BY_ID,
                    studentWithId.getFirstName(),
                    studentWithId.getLastName(),
                    studentWithId.getPhone(),
                    studentWithId.getCity(),
                    studentWithId.getStreet(),
                    studentWithId.getHouseNum(),
                    studentWithId.getCorps(),
                    studentWithId.getApartmentNum(),
                    studentWithId.getLearningRate(),
                    studentWithId.getId());
        }
    }

    //TODO: Проверить работу удаления
    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    private List<Student> getAllMappedValuesAndCleanMaps() {
        List<Student> studentsList = new ArrayList<>(studentMap.values());
        studentMap.clear();
        datedLessonMap.clear();
        return studentsList;
    }

    private Optional<Student> getMappedValueAndCleanMaps(Long id) {
        Optional<Student> optionalStudent = Optional.ofNullable(studentMap.get(id));
        studentMap.clear();
        datedLessonMap.clear();
        return optionalStudent;
    }
}
