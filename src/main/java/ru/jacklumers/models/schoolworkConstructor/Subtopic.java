package ru.jacklumers.models.schoolworkConstructor;

import org.postgresql.util.PGInterval;

import java.util.Map;

/**
 * Модель сущности подтемы
 */
public class Subtopic {
    private long id;
    private PGInterval interval; // Предполагаемое время обучения
    private Topic topic; // Тема, с которой связана данная подтема
    private SubtopicForm subtopicForm; // Вид подтемы
    private Map<Realization_Subtopic, Realization> realizations; // Реализации данной подтемы

    public Subtopic(long id, PGInterval interval, Topic topic, SubtopicForm subtopicForm, Map<Realization_Subtopic, Realization> realizations) {
        this.id = id;
        this.interval = interval;
        this.topic = topic;
        this.subtopicForm = subtopicForm;
        this.realizations = realizations;
    }

    public long getId() {
        return id;
    }

    public PGInterval getInterval() {
        return interval;
    }

    public void setInterval(PGInterval interval) {
        this.interval = interval;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public SubtopicForm getSubtopicForm() {
        return subtopicForm;
    }

    public void setSubtopicForm(SubtopicForm subtopicForm) {
        this.subtopicForm = subtopicForm;
    }

    public Map<Realization_Subtopic, Realization> getRealizations() {
        return realizations;
    }

    public void setRealizations(Map<Realization_Subtopic, Realization> realizations) {
        this.realizations = realizations;
    }
}
