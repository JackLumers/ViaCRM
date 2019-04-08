package ru.jacklumers.models.schoolworkConstructor;

import java.util.Map;

/**
 * Модель сущности группы уроков
 */
public class SchoolworkGroup {
    private long id;
    private String name;
    private Map<SchoolworkDependency, Schoolwork> schoolworks; // Зависимые от этой группы уроки

    public SchoolworkGroup(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Map<SchoolworkDependency, Schoolwork> getSchoolworks() {
        return schoolworks;
    }

    public void setSchoolworks(Map<SchoolworkDependency, Schoolwork> schoolworks) {
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
}
