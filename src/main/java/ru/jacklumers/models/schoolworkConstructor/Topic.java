package ru.jacklumers.models.schoolworkConstructor;

import java.util.List;

/**
 * Модель сущности Тема
 */
public class Topic {
    private long id;
    private String name;
    private Schoolwork schoolwork; // Урок, в котором находится тема

    private List<Subtopic> subtopicList; // Список подтем, которые ссылаются на этот урок

    public Topic(long id, String name, Schoolwork schoolwork, List<Subtopic> subtopicList) {
        this.id = id;
        this.name = name;
        this.schoolwork = schoolwork;
        this.subtopicList = subtopicList;
    }

    public List<Subtopic> getSubtopicList() {
        return subtopicList;
    }

    public void setSubtopicList(List<Subtopic> subtopicList) {
        this.subtopicList = subtopicList;
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

    public Schoolwork getSchoolwork() {
        return schoolwork;
    }

    public void setSchoolwork(Schoolwork schoolwork) {
        this.schoolwork = schoolwork;
    }
}
