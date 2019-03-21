package ru.jacklumers.dao;

import ru.jacklumers.models.Teacher;

import java.util.List;
import java.util.Map;

public interface TeachersDao extends CrudDao<Teacher> {

    /**
     * Найти учителей по данным аргументам.
     *
     * @param columnsAndArgs - HashMap с названиями колонок в качестве ключей
     *                              и аргументами запроса в качестве значений.
     * @return Учителя с данными значениями в данных колонках.
     */
    List<Teacher> findAllByArgs(Map<String, String> columnsAndArgs);
}
