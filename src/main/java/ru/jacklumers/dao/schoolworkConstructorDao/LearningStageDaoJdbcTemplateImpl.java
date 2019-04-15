package ru.jacklumers.dao.schoolworkConstructorDao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.jacklumers.models.schoolworkConstructor.LearningStage;
import ru.jacklumers.models.schoolworkConstructor.Specialization;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LearningStageDaoJdbcTemplateImpl implements LearningStageDao {

    //language=SQL
    private final String SQL_SELECT_ALL =
            "SELECT learning_stage.*, specialization_name FROM learning_stage " +
                    "LEFT JOIN specialization ON learning_stage.specialization_id = specialization.specialization_id";

    //language=SQL
    private final String SQL_SELECT_BY_ID =
            "SELECT learning_stage.*, specialization_name FROM learning_stage " +
                    "LEFT JOIN specialization ON learning_stage.specialization_id = specialization.specialization_id " +
                    "WHERE learning_stage_id = ?";

    //language=SQL
    private final String SQL_SELECT_BY_SPECIALIZATION =
            "SELECT learning_stage.*, specialization_name FROM learning_stage " +
                    "LEFT JOIN specialization ON learning_stage.specialization_id = specialization.specialization_id " +
                    "WHERE specialization_name = ?";

    //language=SQL
    private final String SQL_SELECT_BY_NAME =
            "SELECT learning_stage.*, specialization_name FROM learning_stage " +
                    "LEFT JOIN specialization ON learning_stage.specialization_id = specialization.specialization_id " +
                    "WHERE learning_stage_name = ?";

    //language=SQL
    private final String SQL_DELETE_BY_ID =
            "DELETE FROM learning_stage WHERE learning_stage_id = ?";

    //language=SQL
    private final String SQL_INSERT =
            "INSERT INTO learning_stage(learning_stage_name, specialization_id) " +
                    "VALUES (?, ?)";

    //language=SQL
    private final String SQL_UPDATE_BY_ID =
            "UPDATE learning_stage " +
                    "SET (learning_stage_name, specialization_id) = (?, ?) " +
                    "WHERE learning_stage_id = ?";

    private JdbcTemplate jdbcTemplate;

    /* Map для временного хранения отображенных сущностей.
     * Должны очищаться после отображения ResultSet'а одной транзакции,
     * чтобы всегда содержать актуальные данные */
    private Map<Long, Specialization> specializationMap = new HashMap<>();

    private RowMapper<LearningStage> rowMapper = (rs, rowNum) -> {
        Long specId = rs.getLong("specialization_id");

        //Если это направление не было отображено до этого, то отображаем
        if (!specializationMap.containsKey(specId)) {
            specializationMap.put(specId,
                    new Specialization(specId, rs.getString("specialization_name")));
        }

        return new LearningStage(
                rs.getLong("learning_stage_id"),
                rs.getString("learning_stage_name"),
                specializationMap.get(specId)
        );
    };

    public LearningStageDaoJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<LearningStage> findAll() {
        List<LearningStage> learningStages = jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
        cleanMaps();
        return learningStages;
    }

    @Override
    public List<LearningStage> findStagesBySpecialization(Specialization specialization) {
        List<LearningStage> learningStages = jdbcTemplate.query(SQL_SELECT_BY_SPECIALIZATION, rowMapper, specialization.getId());
        cleanMaps();
        return learningStages;
    }

    @Override
    public Optional<LearningStage> find(Long id) {
        Optional<LearningStage> optionalLearningStage =
                Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
        cleanMaps();
        return optionalLearningStage;
    }

    @Override
    public Optional<LearningStage> findStageByName(String stageName) {
        Optional<LearningStage> optionalLearningStage =
                Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, rowMapper, stageName));
        cleanMaps();
        return optionalLearningStage;
    }

    @Override
    public void save(LearningStage learningStage) {
        jdbcTemplate.update(SQL_INSERT, learningStage.getName(), learningStage.getSpecialization().getId());
    }

    @Override
    public void update(LearningStage modelWithId) {
        if (modelWithId.getId() == null) {
            throw new NullPointerException
                    ("This model object has no id, but you are trying to update entity by it's id");
        } else {
            jdbcTemplate.update(SQL_UPDATE_BY_ID,
                    modelWithId.getName(),
                    modelWithId.getSpecialization().getId(),
                    modelWithId.getId());
        }
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    private void cleanMaps() {
        specializationMap.clear();
    }
}
