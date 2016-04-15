package com.ifeed.model.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Iulia-Anamaria Pascu on 3/29/2016.
 */
public class QuestionDTO extends AbstractDatabaseEntityDTO{

    @NotBlank
    private String title;

    @NotBlank
    private String requirement;

    public QuestionDTO() {

    }

    public QuestionDTO(Long id, Integer version, String title, String requirement) {
        this.setId(id);
        this.setVersion(version);
        this.setTitle(title);
        this.setRequirement(requirement);
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
