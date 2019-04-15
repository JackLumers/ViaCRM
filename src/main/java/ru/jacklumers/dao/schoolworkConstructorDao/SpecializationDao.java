package ru.jacklumers.dao.schoolworkConstructorDao;

import ru.jacklumers.dao.CrudDao;
import ru.jacklumers.models.schoolworkConstructor.LearningStage;
import ru.jacklumers.models.schoolworkConstructor.Specialization;

import java.util.Optional;

public interface SpecializationDao extends CrudDao<Specialization> {

    /**
     * Найти направление по названию.
     * @param specName - название направления
     * @return Направление с таким названием, либо пустой Optional, если направление не было найдено.
     */
    Optional<Specialization> findSpecByName(String specName);

}
