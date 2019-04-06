package ru.jacklumers.models;

/**
 * Модель сущности этапа обучения
 */
public class LearningStage {
    private long id;
    private String name;
    private Specialization specialization;

    public LearningStage(long id, String name, Specialization specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
    }

    public LearningStage(String name, Specialization specialization) {
        this.name = name;
        this.specialization = specialization;
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

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
}
