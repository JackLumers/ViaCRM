package ru.jacklumers.models;

import java.util.Map;

public class Student {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String city;
    private String street;
    private String houseNum;
    private String corps;
    private String apartmentNum;
    private Float learningRate;
    private Map<DatedLesson, Teacher> datedLessons;

    /**
     * Конструктор с указанием атрубутов сущности
     */
    public Student(Long id, String firstName, String lastName, String phone, String city, String street, String houseNum, String corps, String apartmentNum, Float learningRate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
        this.corps = corps;
        this.apartmentNum = apartmentNum;
        this.learningRate = learningRate;
    }

    /**
     * Конструктор с указанием всех полей, включая назначенные занятия.
     */
    public Student(Long id, String firstName, String lastName, String phone, String city, String street, String houseNum, String corps, String apartmentNum, Float learningRate, Map<DatedLesson, Teacher> datedLessons) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
        this.corps = corps;
        this.apartmentNum = apartmentNum;
        this.learningRate = learningRate;
        this.datedLessons = datedLessons;
    }

    /**
     * Конструктор без указания id ученика
     */
    public Student(String firstName, String lastName, String phone, String city, String street, String houseNum, String corps, String apartmentNum, Float learningRate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
        this.corps = corps;
        this.apartmentNum = apartmentNum;
        this.learningRate = learningRate;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getCorps() {
        return corps;
    }

    public void setCorps(String corps) {
        this.corps = corps;
    }

    public String getApartmentNum() {
        return apartmentNum;
    }

    public void setApartmentNum(String apartmentNum) {
        this.apartmentNum = apartmentNum;
    }

    public Float getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(Float learningRate) {
        this.learningRate = learningRate;
    }

    public Map<DatedLesson, Teacher> getDatedLessons() {
        return datedLessons;
    }

    public void setDatedLessons(Map<DatedLesson, Teacher> datedLessons) {
        this.datedLessons = datedLessons;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNum='" + houseNum + '\'' +
                ", corps='" + corps + '\'' +
                ", apartmentNum='" + apartmentNum + '\'' +
                ", learningRate=" + learningRate +
                ", datedLessons=" + datedLessons +
                '}';
    }
}
