package com.ifeed.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by Iulia-Anamaria Pascu on 3/29/2016.
 */
public class TopicDTO extends AbstractDatabaseEntityDTO{

    @NotBlank
    private String title;

    @NotBlank
    private Integer position;

    @NotNull
    private Long courseId;

    public TopicDTO() {

    }

    public TopicDTO(Long id, Integer version, String title, Integer position, Long courseId) {
        this.setId(id);
        this.setVersion(version);
        this.setTitle(title);
        this.setPosition(position);
        this.setCourseId(courseId);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
