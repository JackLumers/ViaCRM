package ru.jacklumers.models.schoolworkConstructor;

public class SchoolworkDependency {

    private Schoolwork schoolwork;
    private SchoolworkGroup schoolworkGroup;

    public SchoolworkDependency(Schoolwork schoolwork, SchoolworkGroup schoolworkGroup) {
        this.schoolwork = schoolwork;
        this.schoolworkGroup = schoolworkGroup;
    }

    public Schoolwork getSchoolwork() {
        return schoolwork;
    }

    public void setSchoolwork(Schoolwork schoolwork) {
        this.schoolwork = schoolwork;
    }

    public SchoolworkGroup getSchoolworkGroup() {
        return schoolworkGroup;
    }

    public void setSchoolworkGroup(SchoolworkGroup schoolworkGroup) {
        this.schoolworkGroup = schoolworkGroup;
    }
}
