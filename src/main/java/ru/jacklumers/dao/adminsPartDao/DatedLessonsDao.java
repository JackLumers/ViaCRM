package ru.jacklumers.dao.adminsPartDao;

import ru.jacklumers.dao.CrudDao;
import ru.jacklumers.models.DatedLesson;

import java.sql.Timestamp;
import java.util.List;

public interface DatedLessonsDao extends CrudDao<DatedLesson> {

    List<DatedLesson> findAllByDate(Timestamp date);
}
