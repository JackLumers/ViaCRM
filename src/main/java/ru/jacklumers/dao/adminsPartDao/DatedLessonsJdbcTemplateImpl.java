package ru.jacklumers.dao.adminsPartDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.jacklumers.models.DatedLesson;
import ru.jacklumers.models.Student;
import ru.jacklumers.models.Teacher;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DatedLessonsJdbcTemplateImpl implements DatedLessonsDao {

    //language=SQL
    private final String SQL_SELECT_ALL =
            "SELECT dated_lesson_id, dated_lesson_date, student.*, teacher.* FROM dated_lesson " +
                    "JOIN teacher ON dated_lesson.teacher_id = teacher.teacher_id " +
                    "JOIN student ON dated_lesson.student_id = student.student_id";

    //language=SQL
    private final String SQL_SELECT_BY_ID =
            "SELECT dated_lesson_id, dated_lesson_date, student.*, teacher.* FROM dated_lesson " +
                    "JOIN teacher ON dated_lesson.teacher_id = teacher.teacher_id " +
                    "JOIN student ON dated_lesson.student_id = student.student_id WHERE dated_lesson_id = ?";

    //language=SQL
    private final String SQL_SELECT_BY_DATE =
            "SELECT dated_lesson_id, dated_lesson_date, student.*, teacher.* FROM dated_lesson " +
                    "JOIN teacher ON dated_lesson.teacher_id = teacher.teacher_id " +
                    "JOIN student ON dated_lesson.student_id = student.student_id WHERE dated_lesson_date = ?";

    //language=SQL
    private final String SQL_UPDATE_BY_ID =
            "UPDATE dated_lesson SET (dated_lesson_id, student_id, teacher_id, dated_lesson_date) " +
                    "= (?, ?, ?, ?) WHERE dated_lesson_id = ?";

    //language=SQL
    private final String SQL_INSERT =
            "INSERT INTO dated_lesson (student_id, teacher_id, dated_lesson_date)" +
                    " VALUES (?, ?, ?)";

    //language=SQL
    private final String SQL_DELETE =
            "DELETE FROM dated_lesson WHERE dated_lesson_id = ?";

    private JdbcTemplate jdbcTemplate;

    /* Map'ы для временного хранения отображенных сущностей
     * здесь, в связующей сущности, после каждой транзакции их очистка не требуется,
     * так как при удалении одной из связанных сущностей удалится сама связующая сущность dated_lesson
     * и не будет отражена в объект. */
    private Map<Long, Student> studentsMap = new HashMap<>();
    private Map<Long, Teacher> teachersMap = new HashMap<>();

    private RowMapper<DatedLesson> rowMapper = (rs, rowNum) -> {
        Long studentId = rs.getLong("student_id");
        Long teacherId = rs.getLong("teacher_id");

        /* Если ученик указан в занятии.
         * Он точно должен быть указан, если занятие существует, это доп. проверка */
        if (studentId != 0) {
            /* Проверяем, есть ли этот студент уже в studentsMap,
             * и если нет, то добавляем */
            if (!studentsMap.containsKey(studentId)) {
                Student student = new Student(
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
                studentsMap.put(studentId, student);
            }
        }

        /* Если преподаватель указан в занятии.
         * Он точно должен быть указан, если занятие существует, это доп. проверка */
        if (teacherId != 0) {
            /* Проверяем, есть ли этот преподаватель уже в teachersMap,
             * и если нет, то добавляем */
            if (!teachersMap.containsKey(teacherId)) {
                Teacher teacher = new Teacher(
                        teacherId,
                        rs.getString("teacher_first_name"),
                        rs.getString("teacher_last_name"),
                        new HashMap<DatedLesson, Student>());
                teachersMap.put(teacherId, teacher);
            }
        }

        DatedLesson datedLesson = new DatedLesson(rs.getLong("dated_lesson_id"),
                rs.getTimestamp("dated_lesson_date"),
                studentsMap.get(studentId),
                teachersMap.get(teacherId));

        Teacher teacher = teachersMap.get(teacherId);
        Student student = studentsMap.get(studentId);
        teacher.getDatedLessons().put(datedLesson, student);
        student.getDatedLessons().put(datedLesson, teacher);

        return datedLesson;
    };

    public DatedLessonsJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<DatedLesson> find(Long id) {
        List<DatedLesson> datedLessons = jdbcTemplate.query(SQL_SELECT_BY_ID, rowMapper, id);
        if (!datedLessons.isEmpty()) {
            return Optional.of(datedLessons.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void save(DatedLesson datedLesson) {
        jdbcTemplate.update(SQL_INSERT,
                datedLesson.getStudent().getId(),
                datedLesson.getTeacher().getId(),
                datedLesson.getDate());
    }

    @Override
    public void update(DatedLesson datedLessonWithId) {
        if (datedLessonWithId.getId() == null) {
            throw new NullPointerException
                    ("This model object has no id, but you are trying to update entity by it's id");
        } else {
            jdbcTemplate.update(SQL_UPDATE_BY_ID,
                    datedLessonWithId.getId(),
                    datedLessonWithId.getStudent().getId(),
                    datedLessonWithId.getTeacher().getId(),
                    datedLessonWithId.getDate());
        }
    }

    //TODO: Проверить работу удаления
    @Override
    public void delete(Long id) {
        jdbcTemplate.queryForObject(SQL_DELETE, Long.class, id);
    }

    @Override
    public List<DatedLesson> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public List<DatedLesson> findAllByDate(Timestamp date) {
        return jdbcTemplate.query(SQL_SELECT_BY_DATE, rowMapper, date);
    }
}
