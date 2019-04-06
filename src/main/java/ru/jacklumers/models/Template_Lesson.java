package ru.jacklumers.models;

/**
 * Модель сущности Шаблоны_Уроки
 */
public class Template_Lesson {
    private Template template;
    private Schoolwork schoolwork;
    private short orderNum; // Порядковый номер для сортировки уроков в шаблоне

    public Template_Lesson(Template template, Schoolwork schoolwork, short orderNum) {
        this.template = template;
        this.schoolwork = schoolwork;
        this.orderNum = orderNum;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public Schoolwork getSchoolwork() {
        return schoolwork;
    }

    public void setSchoolwork(Schoolwork schoolwork) {
        this.schoolwork = schoolwork;
    }

    public short getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(short orderNum) {
        this.orderNum = orderNum;
    }
}
