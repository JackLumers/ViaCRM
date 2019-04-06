package ru.jacklumers.models;

import java.util.List;
import java.util.Map;

/**
 * Модель сущности урока
 */
public class Schoolwork {

    private long id;
    private String name; // Название урока
    private SchoolworkGroup schoolworkGroup; // Группа, в которой находится урок
    private Schoolwork schoolworkOnWhichDepends; // Урок, который должен идти до этого (прямая зависимость)
    private Map<SchoolworkDependency, SchoolworkGroup> schoolworkGroupsOnWhichDepends; // Группы уроков, от которых данный урок зависит (Групповая зависимость)
    private boolean isCollective; // Групповой ли урок
    private LearningStage learningStage; // Этап, которому принадлежит урок

    private List<Topic> topicList; // Список тем, которые ссылкаются на этот урок

    public Schoolwork(long id, String name, SchoolworkGroup schoolworkGroup, Schoolwork schoolworkOnWhichDepends, Map<SchoolworkDependency, SchoolworkGroup> schoolworkGroupsOnWhichDepends, boolean isCollective, LearningStage learningStage, List<Topic> topicList) {
        this.id = id;
        this.name = name;
        this.schoolworkGroup = schoolworkGroup;
        this.schoolworkOnWhichDepends = schoolworkOnWhichDepends;
        this.schoolworkGroupsOnWhichDepends = schoolworkGroupsOnWhichDepends;
        this.isCollective = isCollective;
        this.learningStage = learningStage;
        this.topicList = topicList;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
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

    public SchoolworkGroup getSchoolworkGroup() {
        return schoolworkGroup;
    }

    public void setSchoolworkGroup(SchoolworkGroup schoolworkGroup) {
        this.schoolworkGroup = schoolworkGroup;
    }

    public Schoolwork getSchoolworkOnWhichDepends() {
        return schoolworkOnWhichDepends;
    }

    public void setSchoolworkOnWhichDepends(Schoolwork schoolworkOnWhichDepends) {
        this.schoolworkOnWhichDepends = schoolworkOnWhichDepends;
    }

    public Map<SchoolworkDependency, SchoolworkGroup> getSchoolworkGroupsOnWhichDepends() {
        return schoolworkGroupsOnWhichDepends;
    }

    public void setSchoolworkGroupsOnWhichDepends(Map<SchoolworkDependency, SchoolworkGroup> schoolworkGroupsOnWhichDepends) {
        this.schoolworkGroupsOnWhichDepends = schoolworkGroupsOnWhichDepends;
    }

    public boolean isCollective() {
        return isCollective;
    }

    public void setCollective(boolean collective) {
        isCollective = collective;
    }

    public LearningStage getLearningStage() {
        return learningStage;
    }

    public void setLearningStage(LearningStage learningStage) {
        this.learningStage = learningStage;
    }
}
