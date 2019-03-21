package ru.jacklumers.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.jacklumers.models.DatedLesson;
import ru.jacklumers.models.Student;
import ru.jacklumers.models.Teacher;
import ru.jacklumers.utils.SqlSelectQueryGenerator;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Реализация методов доступа к данным об учениках через JDBC Template.
 * <p>
 * JdbcTemplate - прослойка между чистым JDBC и бизнес-логикой приложения.
 * Она содержит готовые шаблоны для работы с базой данных, что упрощает
 * написание кода.
 */
public class StudentsDaoJdbcTemplateImpl implements StudentsDao {

    /*  TODO: Сделать отображение преподавателей через Hibernate.
     *      Если нужно, то переписать запросы так, чтобы новый mapping мог с ними работать */

    //language=SQL
    private final String SQL_SELECT_ALL =
            "SELECT * FROM student";

    //language=SQL
    private final String SQL_SELECT_BY_ID =
            "SELECT * FROM student WHERE student_id = ?";

    //language=SQL
    private final String SQL_SELECT_BY_FIRST_NAME =
            "SELECT * FROM student WHERE student_first_name = ?";

    //language=SQL
    private final String SQL_SELECT_BY_LEARNING_RATE =
            "SELECT * FROM student WHERE student_learning_rate = ?";

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

    //language=SQL
    private final String SQL_SELECT_STUDENT_WITH_DATED_LESSONS =
            "SELECT student.*, teacher.*, dated_lesson_id, dated_lesson_date FROM student " +
                    "JOIN dated_lesson ON student.student_id = dated_lesson.student_id " +
                    "JOIN teacher ON dated_lesson.teacher_id = teacher.teacher_id " +
                    "WHERE student.student_id = ?";


    private JdbcTemplate jdbcTemplate;
    private Map<Long, Student> studentsMap = new HashMap<>();
    private Map<Long, Teacher> teachersMap = new HashMap<>();

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
    private RowMapper<Student> studentRowMapper = (ResultSet rs, int rowNum) ->
    {
        Long studentId = rs.getLong("student_id");
        Long teacherId = rs.getLong("teacher_id");

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
                    new HashMap<Teacher, DatedLesson>());
            studentsMap.put(studentId, student);
        }

        /* Проверяем, есть ли этот преподаватель уже в teachersMap,
         * и если нет, то добавляем */
        if (!teachersMap.containsKey(teacherId)) {
            Teacher teacher = new Teacher(
                    studentId,
                    rs.getString("teacher_first_name"),
                    rs.getString("teacher_last_name"),
                    new HashMap<Student, DatedLesson>());
            teachersMap.put(teacherId, teacher);
        }

        DatedLesson datedLesson = new DatedLesson(
                rs.getLong("dated_lesson_id"),
                rs.getTimestamp("dated_lesson_date"),
                studentsMap.get(studentId),
                teachersMap.get(teacherId));

        Teacher teacher = teachersMap.get(teacherId);
        Student student = studentsMap.get(studentId);
        teacher.getDatedLessons().put(student, datedLesson);
        student.getDatedLessons().put(teacher, datedLesson);

        return student;
    };


    /**
     * Конструктор для подключения к базе данных через интерфейс DataSource.
     * SQLException отлавливется в JdbcTemplate
     *
     * @param dataSource - фабрика, позволяющая получить источник данных,
     *                   который эта DataSource представляет.
     */
    public StudentsDaoJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Student> findAllByFirstName(String firstName) {
        return jdbcTemplate.query(SQL_SELECT_BY_FIRST_NAME, studentRowMapper, firstName);
    }

    @Override
    public List<Student> findAllByLearningRate(Float learningRate) {
        return jdbcTemplate.query(SQL_SELECT_BY_LEARNING_RATE, studentRowMapper, learningRate);
    }

    @Override
    public List<Student> findAllByArgs(Map<String, String> columnsAndArgsHashMap) {
        return jdbcTemplate.query(SqlSelectQueryGenerator.generateSelectQuery("student", columnsAndArgsHashMap), studentRowMapper);
    }

    @Override
    public Optional<Student> find(Long id) {
        jdbcTemplate.query(SQL_SELECT_STUDENT_WITH_DATED_LESSONS, studentRowMapper, id);
        return Optional.ofNullable(studentsMap.get(id));
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

    @Override
    public void delete(Long id) {
        jdbcTemplate.queryForObject(SQL_DELETE_BY_ID, studentRowMapper, id);
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, studentRowMapper);
    }
}
