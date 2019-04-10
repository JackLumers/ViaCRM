package ru.jacklumers.dao.schoolworkConstructorDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.jacklumers.models.schoolworkConstructor.Specialization;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

public class SpecializationDaoJdbcTemplateImpl implements SpecializationDao {

    //language=SQL
    private final String SQL_SELECT_ALL =
            "SELECT * FROM specialization";

    //language=SQL
    private final String SQL_SELECT_BY_ID =
            "SELECT * FROM specialization WHERE specialization_id = ?";

    //language=SQL
    private final String SQL_DELETE_BY_ID =
            "DELETE FROM specialization WHERE specialization_id = ?";

    //language=SQL
    private final String SQL_INSERT =
            "INSERT INTO specialization(specialization_name) VALUES (?)";

    //language=SQL
    private final String SQL_UPDATE_BY_ID =
            "UPDATE specialization SET (specialization_name) = ? WHERE specialization_id = ?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Specialization> rowMapper = (rs, rowNum) -> {
        Long specId = rs.getLong("specialization_id");
        return new Specialization(
                specId,
                rs.getString("specialization_name")
        );
    };

    /**
     * Конструктор DAO с подключением к базе данных через интерфейс DataSource.
     * SQLException отлавливется в JdbcTemplate
     *
     * @param dataSource - фабрика, позволяющая получить источник данных,
     *                   который эта DataSource представляет.
     */
    public SpecializationDaoJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Specialization> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public Optional<Specialization> find(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
    }

    @Override
    public void save(Specialization specialization) {
        jdbcTemplate.update(SQL_INSERT, specialization.getName());
    }

    @Override
    public void update(Specialization specialization) {
        if (specialization.getId() == null) {
            throw new NullPointerException
                    ("This model object has no id, but you are trying to update entity by it's id");
        } else {
            jdbcTemplate.update(SQL_UPDATE_BY_ID,
                    specialization.getName(),
                    specialization.getId());
        }
    }

    //TODO: Проверить работу удаления
    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }
}
