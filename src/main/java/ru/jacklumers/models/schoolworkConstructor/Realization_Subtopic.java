package ru.jacklumers.models.schoolworkConstructor;

public class Realization_Subtopic {
    private Subtopic subtopic;
    private Realization realization;

    public Realization_Subtopic(Subtopic subtopic, Realization realization) {
        this.subtopic = subtopic;
        this.realization = realization;
    }

    public Subtopic getSubtopic() {
        return subtopic;
    }

    public void setSubtopic(Subtopic subtopic) {
        this.subtopic = subtopic;
    }

    public Realization getRealization() {
        return realization;
    }

    public void setRealization(Realization realization) {
        this.realization = realization;
    }
}
