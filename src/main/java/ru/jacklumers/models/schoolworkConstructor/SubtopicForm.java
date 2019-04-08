package ru.jacklumers.models.schoolworkConstructor;

import java.util.List;

/**
 * Модель сущности вида подтемы
 */
public class SubtopicForm {
    private long id;
    private String name;

    private List<Subtopic> subtopics; // Подтемы, ссылающиеся на это направление

    public SubtopicForm(long id, String name, List<Subtopic> subtopics) {
        this.id = id;
        this.name = name;
        this.subtopics = subtopics;
    }

    public List<Subtopic> getSubtopics() {
        return subtopics;
    }

    public void setSubtopics(List<Subtopic> subtopics) {
        this.subtopics = subtopics;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
