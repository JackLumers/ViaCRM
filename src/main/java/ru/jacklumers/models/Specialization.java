package ru.jacklumers.models;

/**
 * Модель сущности направления
 */
public class Specialization {
    private long id;
    private String name;

    public Specialization(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Specialization(String name) {
        this.name = name;
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
