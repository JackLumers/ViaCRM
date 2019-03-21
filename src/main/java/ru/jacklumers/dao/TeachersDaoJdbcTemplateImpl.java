package ru.jacklumers.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.jacklumers.models.Teacher;
import ru.jacklumers.utils.SqlSelectQueryGenerator;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Реализация методов доступа к данным об учителях через JDBC Template
 *
 * @see org.springframework.jdbc.core.JdbcTemplate
 */
public class TeachersDaoJdbcTemplateImpl implements TeachersDao {

    //language=SQL
    private final String SQL_SELECT_ALL =
            "SELECT * FROM teacher";

    //language=SQL
    private final String SQL_SELECT_BY_ID =
            "SELECT * FROM teacher WHERE teacher_id = ?";

    //language=SQL
    private final String SQL_SELECT_BY_FIRST_NAME =
            "SELECT * FROM teacher WHERE teacher_first_name = ?";

    //language=SQL
    private final String SQL_DELETE_BY_ID =
            "DELETE FROM teacher WHERE teacher_id = ?";

    //language=SQL
    private final String SQL_INSERT_TEACHER =
            "INSERT INTO teacher (teacher_first_name, teacher_last_name) VALUES (?, ?)";

    //language=SQL
    private final String SQL_UPDATE_TEACHER_BY_ID =
            "UPDATE teacher SET (teacher_first_name, teacher_last_name) = (?, ?) WHERE teacher_id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Teacher> teacherRowMapper = (rs, rawNum) -> {
        //тело mapRow
        return new Teacher(rs.getLong("teacher_id"),
                rs.getString("teacher_first_name"),
                rs.getString("teacher_last_name"));
    };

    public TeachersDaoJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Teacher> find(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, teacherRowMapper, id));
    }

    @Override
    public List<Teacher> findAllByArgs(Map<String, String> columnsAndArgs) {
        return jdbcTemplate.query(SqlSelectQueryGenerator.generateSelectQuery("teacher", columnsAndArgs), teacherRowMapper);
    }

    @Override
    public void save(Teacher teacher) {
        jdbcTemplate.update(SQL_INSERT_TEACHER,
                teacher.getFirstName(),
                teacher.getLastName());
    }

    @Override
    public void update(Teacher teacherWithId) {
        jdbcTemplate.update(SQL_UPDATE_TEACHER_BY_ID,
                teacherWithId.getId(),
                teacherWithId.getFirstName(),
                teacherWithId.getLastName());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.query(SQL_DELETE_BY_ID, teacherRowMapper, id);
    }

    @Override
    public List<Teacher> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, teacherRowMapper);
    }
}
