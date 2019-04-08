package ru.jacklumers.models.schoolworkConstructor;

import java.util.Map;

public class Realization {
    private long id;
    private String name;
    private String URL; // Ссылка на материал
    private Map<Realization_Subtopic, Subtopic> subtopics; // Подтемы с данной реализацией

    public Realization(long id, String name, String URL, Map<Realization_Subtopic, Subtopic> subtopics) {
        this.id = id;
        this.name = name;
        this.URL = URL;
        this.subtopics = subtopics;
    }

    public Realization(String name, String URL, Map<Realization_Subtopic, Subtopic> subtopics) {
        this.name = name;
        this.URL = URL;
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

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Map<Realization_Subtopic, Subtopic> getSubtopics() {
        return subtopics;
    }

    public void setSubtopics(Map<Realization_Subtopic, Subtopic> subtopics) {
        this.subtopics = subtopics;
    }
}
