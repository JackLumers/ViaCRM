package ru.jacklumers.models.modelsBuilders;

import ru.jacklumers.models.Subtopic;
import ru.jacklumers.models.SubtopicForm;

import java.util.List;

/**
 * Builder для экземпляра класса SubtopicForm
 * @see SubtopicForm
 */
public class SubtopicFormBuilder {
    private long id;
    private String name;
    private List<Subtopic> subtopics;

    public SubtopicFormBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public SubtopicFormBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SubtopicFormBuilder setSubtopics(List<Subtopic> subtopics) {
        this.subtopics = subtopics;
        return this;
    }

    public SubtopicForm createSubtopicForm() {
        return new SubtopicForm(id, name, subtopics);
    }
}