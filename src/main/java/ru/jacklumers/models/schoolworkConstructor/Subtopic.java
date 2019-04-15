package ru.jacklumers.models.schoolworkConstructor;

import org.postgresql.util.PGInterval;

import java.util.Map;

/**
 * Модель сущности подтемы
 */
public class Subtopic {
    private Long id;
    private PGInterval learningTime; // Предполагаемое время обучения
    private Topic topic; // Тема, с которой связана данная подтема
    private SubtopicForm subtopicForm; // Вид подтемы
    private Map<Realization_Subtopic, Realization> realizations; // Реализации данной подтемы

    public Subtopic(Long id, PGInterval learningTime, Topic topic, SubtopicForm subtopicForm, Map<Realization_Subtopic, Realization> realizations) {
        this.id = id;
        this.learningTime = learningTime;
        this.topic = topic;
        this.subtopicForm = subtopicForm;
        this.realizations = realizations;
    }

    public Long getId() {
        return id;
    }

    public PGInterval getLearningTime() {
        return learningTime;
    }

    public void setLearningTime(PGInterval learningTime) {
        this.learningTime = learningTime;
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
