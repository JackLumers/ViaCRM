package ru.jacklumers.dao;

import ru.jacklumers.models.Student;

import java.util.List;
import java.util.Map;

/**
 * Интерфейс DAO, включающий общие базовые
 * операции с объектами Student в базе данных.
 */
public interface StudentsDao extends CrudDao<Student> {

    /**
     * Найти учеников по коэффициенту успеваемости.
     *
     * @param learningRate - коэффициент успеваемости.
     * @return Ученики с таким коэффициентом.
     */
    List<Student> findAllByLearningRate(Float learningRate);

    /**
     * Найти учеников по имени.
     *
     * @param firstName - имя.
     * @return Ученики с таким именем.
     */
    List<Student> findAllByFirstName(String firstName);

    /**
     * Найти учеников по данным аргументам.
     *
     * @param columnsAndArgs - Map с названиями колонок в качестве ключей
     *                              и аргументами запроса в качестве значений.
     * @return Ученики с данными значениями в данных колонках.
     */
    List<Student> findAllByArgs(Map<String, String> columnsAndArgs);
}
