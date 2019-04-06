package ru.jacklumers.models.modelsBuilders;

import ru.jacklumers.models.Schoolwork;
import ru.jacklumers.models.Template;
import ru.jacklumers.models.Template_Lesson;

import java.util.Map;

/**
 * Builder для экземпляра класса Template
 * @see Template
 */
public class TemplateBuilder {
    private long id;
    private String name;
    private String description;
    private Map<Template_Lesson, Schoolwork> schoolworks;

    public TemplateBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public TemplateBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TemplateBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public TemplateBuilder setSchoolworks(Map<Template_Lesson, Schoolwork> schoolworks) {
        this.schoolworks = schoolworks;
        return this;
    }

    public Template createTemplate() {
        return new Template(id, name, description, schoolworks);
    }


}