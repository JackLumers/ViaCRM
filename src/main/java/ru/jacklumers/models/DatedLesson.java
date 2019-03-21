package ru.jacklumers.models;

import java.sql.Timestamp;

public class DatedLesson {
    private Long id;
    private Timestamp date;
    private Student student;
    private Teacher teacher;

    /**
     * Конструктор с указанием всех полей
     */
    public DatedLesson(Long id, Timestamp date, Student student, Teacher teacher) {
        this.id = id;
        this.date = date;
        this.student = student;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
