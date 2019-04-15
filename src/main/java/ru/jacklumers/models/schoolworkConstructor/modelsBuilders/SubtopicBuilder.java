package ru.jacklumers.models.schoolworkConstructor.modelsBuilders;

import org.postgresql.util.PGInterval;
import ru.jacklumers.models.schoolworkConstructor.*;

import java.util.Map;

public class SubtopicBuilder {
    private Long id;
    private PGInterval learningTime;
    private Topic topic;
    private SubtopicForm subtopicForm;
    private Map<Realization_Subtopic, Realization> realizations;

    public SubtopicBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public SubtopicBuilder setLearningTime(PGInterval learningTime) {
        this.learningTime = learningTime;
        return this;
    }

    public SubtopicBuilder setTopic(Topic topic) {
        this.topic = topic;
        return this;
    }

    public SubtopicBuilder setSubtopicForm(SubtopicForm subtopicForm) {
        this.subtopicForm = subtopicForm;
        return this;
    }

    public SubtopicBuilder setRealizations(Map<Realization_Subtopic, Realization> realizations) {
        this.realizations = realizations;
        return this;
    }

    public Subtopic createSubtopic() {
        return new Subtopic(id, learningTime, topic, subtopicForm, realizations);
    }
}