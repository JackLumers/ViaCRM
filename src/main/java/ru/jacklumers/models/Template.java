package ru.jacklumers.models;

import java.util.Map;

/**
 * Модель сущности направления
 */
public class Template {

    public Template(long id, String name, String description, Map<Template_Lesson, Schoolwork> schoolworks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.schoolworks = schoolworks;
    }

    private long id;
    private String name;
    private String description;
    private Map<Template_Lesson, Schoolwork> schoolworks;

    public Map<Template_Lesson, Schoolwork> getSchoolworks() {
        return schoolworks;
    }

    public void setSchoolworks(Map<Template_Lesson, Schoolwork> schoolworks) {
        this.schoolworks = schoolworks;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
