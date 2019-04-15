package ru.jacklumers.dao.adminsPartDao;

import ru.jacklumers.dao.CrudDao;
import ru.jacklumers.models.Student;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
     * Найти ученика по полному имени.
     * <p>
     * Учеников с одинаковыми именами и фамилиями пока не может быть, поэтому этот метод рабочий
     * Но в будующем нужно заменить, создав уникальный отличительный признак, который будет видно
     * на клиенткой части
     *
     * @param firstName - имя
     * @param lastName  - фамилия
     * @return Ученик с таким именем, либо пустой Optional.
     */
    Optional<Student> findByFullName(String firstName, String lastName);

    /**
     * Найти учеников, отсортировав по заданным колонкам.
     *
     * @param orderColumns - колонки по которым проводится сортировка
     * @return Ученики с данными значениями в данных колонках.
     */
    List<Student> findAllByOrder(String[] orderColumns);

    List<Student> findAllWithOnlySelfAttributesByArguments(Map<String, String> columnsAndArgs);
}
