package ru.jacklumers.models.modelsBuilders;

import ru.jacklumers.models.Schoolwork;
import ru.jacklumers.models.Subtopic;
import ru.jacklumers.models.Topic;

import java.util.List;

/**
 * Builder для экземпляра класса Topic
 * @see Topic
 */
public class TopicBuilder {
    private long id;
    private String name;
    private Schoolwork schoolwork;
    private List<Subtopic> subtopicList;

    public TopicBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public TopicBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TopicBuilder setSchoolwork(Schoolwork schoolwork) {
        this.schoolwork = schoolwork;
        return this;
    }

    public TopicBuilder setSubtopicList(List<Subtopic> subtopicList) {
        this.subtopicList = subtopicList;
        return this;
    }

    public Topic createTopic() {
        return new Topic(id, name, schoolwork, subtopicList);
    }


}