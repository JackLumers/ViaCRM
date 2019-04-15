package ru.jacklumers.dao.schoolworkConstructorDao;

import ru.jacklumers.dao.CrudDao;
import ru.jacklumers.models.schoolworkConstructor.LearningStage;
import ru.jacklumers.models.schoolworkConstructor.Specialization;

import java.util.List;
import java.util.Optional;

public interface LearningStageDao extends CrudDao<LearningStage> {

    /**
     * Найти этапы конкретного направления.
     * @param specialization - направление
     * @return Список этапов учебного плана по этой специализации.
     */
    List<LearningStage> findStagesBySpecialization(Specialization specialization);

    /**
     * Найти этап по названию.
     * @param stageName - название этапа
     * @return Этап с таким названием, либо пустой Optional.
     */
    Optional<LearningStage> findStageByName(String stageName);
}
