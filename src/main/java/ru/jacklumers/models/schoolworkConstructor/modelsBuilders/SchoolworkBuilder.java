package ru.jacklumers.models.schoolworkConstructor.modelsBuilders;

import ru.jacklumers.models.schoolworkConstructor.*;

import java.util.List;
import java.util.Map;

/**
 * Builder для экземпляра класса Schoolwork
 * @see Schoolwork
 */
public class SchoolworkBuilder {
    private long id;
    private String name;
    private SchoolworkGroup schoolworkGroup;
    private Schoolwork schoolworkOnWhichDepends;
    private Map<SchoolworkDependency, SchoolworkGroup> schoolworkGroupsOnWhichDepends;
    private boolean isCollective = false;
    private LearningStage learningStage;
    private List<Topic> topicList;

    public SchoolworkBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public SchoolworkBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SchoolworkBuilder setSchoolworkGroup(SchoolworkGroup schoolworkGroup) {
        this.schoolworkGroup = schoolworkGroup;
        return this;
    }

    public SchoolworkBuilder setSchoolworkOnWhichDepends(Schoolwork schoolworkOnWhichDepends) {
        this.schoolworkOnWhichDepends = schoolworkOnWhichDepends;
        return this;
    }

    public SchoolworkBuilder setSchoolworkGroupsOnWhichDepends(Map<SchoolworkDependency, SchoolworkGroup> schoolworkGroupsOnWhichDepends) {
        this.schoolworkGroupsOnWhichDepends = schoolworkGroupsOnWhichDepends;
        return this;
    }

    public SchoolworkBuilder setIsCollective(boolean isCollective) {
        this.isCollective = isCollective;
        return this;
    }

    public SchoolworkBuilder setLearningStage(LearningStage learningStage) {
        this.learningStage = learningStage;
        return this;
    }

    public SchoolworkBuilder setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
        return this;
    }

    public Schoolwork createSchoolwork() {
        return new Schoolwork(id, name, schoolworkGroup, schoolworkOnWhichDepends, schoolworkGroupsOnWhichDepends, isCollective, learningStage, topicList);
    }
}