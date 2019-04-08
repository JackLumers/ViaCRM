package ru.jacklumers.dao.adminsPartDao;

import ru.jacklumers.dao.CrudDao;
import ru.jacklumers.models.Teacher;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TeachersDao extends CrudDao<Teacher> {

    /**
     * Найти учителей по данным аргументам.
     *
     * @param columnsAndArgs - Map с названиями колонок в качестве ключей
     *                       и аргументами запроса в качестве значений.
     * @return Учителя с данными значениями в данных колонках.
     */
    List<Teacher> findAllWithOnlySelfAttributesByArgs(Map<String, String> columnsAndArgs);

    /**
     * Найти перподавателя по полному имени.
     * <p>
     * Перподавателей с одинаковыми именами и фамилиями пока не может быть, поэтому этот метод рабочий
     * Но в будующем нужно заменить, создав уникальный отличительный признак, который будет видно
     * на клиенткой части
     *
     * @param firstName - имя
     * @param lastName  - фамилия
     * @return Преподатель с таким именем.
     */
    Optional<Teacher> findByFullName(String firstName, String lastName);
}
