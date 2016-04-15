package com.ifeed.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by Iulia-Anamaria Pascu on 3/28/2016.
 */
@Entity
public class Course extends IdentifiableEntity {

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false, length = 10)
    private String code;

    public Course() {
    }

    public Course(String name, String code) {
        this.name = name;
        this.code = code;
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
