package ru.jacklumers.models;

import java.util.Map;

public class Teacher {
    private Long id;
    private String firstName;
    private String lastName;

    private Map<DatedLesson, Student> datedLessons;

    /**
     * Конструктор с указанием только атрибутов сущности
     */
    public Teacher(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Конструктор с указанием всех полей, включая назначенные занятия
     */
    public Teacher(Long id, String firstName, String lastName, Map<DatedLesson, Student> datedLessons) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.datedLessons = datedLessons;
    }

    /**
     * Конструктор без указания id ученика
     */
    public Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Map<DatedLesson, Student> getDatedLessons() {
        return datedLessons;
    }

    public void setDatedLessons(Map<DatedLesson, Student> datedLessons) {
        this.datedLessons = datedLessons;
    }
}
