package com.ifeed.model.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Iulia-Anamaria Pascu on 3/28/2016.
 */
public class CourseDTO extends AbstractDatabaseEntityDTO{

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    public CourseDTO() {

    }

    public CourseDTO(Long id, Integer version, String name, String code) {
        this.setId(id);
        this.setVersion(version);
        this.setName(name);
        this.setCode(code);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
