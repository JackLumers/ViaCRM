package ru.jacklumers.dao.schoolworkConstructorDao;

import ru.jacklumers.dao.CrudDao;
import ru.jacklumers.models.schoolworkConstructor.SubtopicForm;

import java.util.Optional;

public interface SubtopicFormDao extends CrudDao<SubtopicForm> {

    /**
     * Найти вид подтемы по названию.
     * @param formName - название вида подтемы
     * @return Вид подтемы с таким названием, либо пустой Optional, если такого не было найдено.
     */
    Optional<SubtopicForm> findSubtopicFormByName(String formName);

}
