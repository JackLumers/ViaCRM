package ru.jacklumers.dao.schoolworkConstructorDao;

import org.postgresql.util.PGInterval;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.jacklumers.models.schoolworkConstructor.Subtopic;
import ru.jacklumers.models.schoolworkConstructor.SubtopicForm;
import ru.jacklumers.models.schoolworkConstructor.modelsBuilders.SubtopicBuilder;
import ru.jacklumers.models.schoolworkConstructor.modelsBuilders.SubtopicFormBuilder;

import javax.sql.DataSource;
import java.util.*;

public class SubtopicFormDaoJdbcTemplateImpl implements SubtopicFormDao {

    //language=SQL
    private final String SQL_SELECT_ALL = "SELECT subtopic_form.*, subtopic_id, topic_id, subtopic_learning_time " +
            "FROM subtopic_form " +
            "LEFT JOIN subtopic ON subtopic_form.subtopic_form_id = subtopic.subtopic_form_id";

    //language=SQL
    private final String SQL_SELECT_BY_ID = "SELECT subtopic_form.*, subtopic_id, topic_id, subtopic_learning_time " +
            "FROM subtopic_form " +
            "LEFT JOIN subtopic ON subtopic_form.subtopic_form_id = subtopic.subtopic_form_id " +
            "WHERE subtopic_form.subtopic_form_id = ?";

    //language=SQL
    private final String SQL_SELECT_BY_NAME = "SELECT subtopic_form.*, subtopic_id, topic_id, subtopic_learning_time " +
            "FROM subtopic_form " +
            "LEFT JOIN subtopic ON subtopic_form.subtopic_form_id = subtopic.subtopic_form_id " +
            "WHERE subtopic_form.subtopic_form_name = ?";

    //language=SQL
    private final String SQL_INSERT = "INSERT INTO subtopic_form(subtopic_form_name) VALUES (?)";

    //language=SQL
    private final String SQL_DELETE_BY_ID = "DELETE FROM subtopic_form WHERE subtopic_form_id = ?";

    //language=SQL
    private final String SQL_UPDATE_BY_ID = "UPDATE subtopic_form SET (subtopic_form_name) = ? WHERE subtopic_form_id = ?";

    private JdbcTemplate jdbcTemplate;

    /* Map для временного хранения отображенных сущностей.
     * Должны очищаться после отображения ResultSet'а одной транзакции,
     * чтобы всегда содержать актуальные данные */
    private Map<Long, SubtopicForm> subtopicFormMap = new HashMap<>();

    private RowMapper<SubtopicForm> rowMapper = (rs, rowNum) -> {
        Long subtopicFormId = rs.getLong("subtopic_form_id");
        Long subtopicId = rs.getLong("subtopic_id");

        //Если данный вид подтемы еще не был отображен, то отображаем
        if (!subtopicFormMap.containsKey(subtopicFormId)) {
            SubtopicFormBuilder subtopicFormBuilder = new SubtopicFormBuilder();
            subtopicFormBuilder
                    .setName(rs.getString("subtopic_form_name"))
                    .setId(subtopicFormId)
                    .setSubtopics(new ArrayList<Subtopic>());

            subtopicFormMap.put(subtopicFormId, subtopicFormBuilder.createSubtopicForm());
        }

        //Если с данным видом подтем связана подтема, то добавляем её в список подтем этого вида
        if (subtopicId != 0) {
            SubtopicBuilder subtopicBuilder = new SubtopicBuilder();
            subtopicBuilder
                    .setId(subtopicId)
                    .setSubtopicForm(subtopicFormMap.get(subtopicFormId))
                    .setLearningTime((PGInterval) rs.getObject("subtopic_learning_time"));

            subtopicFormMap.get(subtopicFormId)
                    .getSubtopics().add(subtopicBuilder.createSubtopic());
        }

        return subtopicFormMap.get(subtopicFormId);
    };

    /**
     * Конструктор DAO с подключением к базе данных через интерфейс DataSource.
     * SQLException отлавливется в JdbcTemplate
     *
     * @param dataSource - фабрика, позволяющая получить источник данных,
     *                   который эта DataSource представляет.
     */
    public SubtopicFormDaoJdbcTemplateImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<SubtopicForm> findSubtopicFormByName(String formName) {
        jdbcTemplate.query(SQL_SELECT_BY_NAME, rowMapper, formName);
        try {
            SubtopicForm subtopicForm = getMappedValuesAndCleanMaps().get(0);
            return Optional.ofNullable(subtopicForm);
        } catch (IndexOutOfBoundsException e) { // Список оказался пустой, ученик не найден
            return Optional.empty();
        }
    }

    @Override
    public List<SubtopicForm> findAll() {
        jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
        return getMappedValuesAndCleanMaps();
    }

    @Override
    public Optional<SubtopicForm> find(Long id) {
        jdbcTemplate.query(SQL_SELECT_BY_ID, rowMapper, id);
        try {
            SubtopicForm subtopicForm = getMappedValuesAndCleanMaps().get(0);
            return Optional.ofNullable(subtopicForm);
        } catch (IndexOutOfBoundsException e) { // Список оказался пустой, ученик не найден
            return Optional.empty();
        }
    }

    @Override
    public void save(SubtopicForm model) {
        jdbcTemplate.update(SQL_INSERT,
                model.getName());
    }

    @Override
    public void update(SubtopicForm modelWithId) {
        if (modelWithId.getId() == null) {
            throw new NullPointerException
                    ("This model object has no id, but you are trying to update entity by it's id");
        } else {
            jdbcTemplate.update(SQL_UPDATE_BY_ID,
                    modelWithId.getName(),
                    modelWithId.getId());
        }
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    private List<SubtopicForm> getMappedValuesAndCleanMaps() {
        List<SubtopicForm> subtopicForms = new ArrayList<>(subtopicFormMap.values());
        subtopicFormMap.clear();
        return subtopicForms;
    }
}
