package com.ifeed.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by Iulia-Anamaria Pascu on 3/28/2016.
 */
@Entity
public class Question extends IdentifiableEntity {

    @Column(unique = true, nullable = false, length = 100)
    private String title;

    @Column(unique = true, nullable = false, length = 500)
    private String requirement;

    public Question() {
    }

    public Question(String title, String requirement) {
        this.title = title;
        this.requirement = requirement;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }
}
