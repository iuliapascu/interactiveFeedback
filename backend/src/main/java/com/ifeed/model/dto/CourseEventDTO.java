package com.ifeed.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by ipascu on 19.05.2016.
 */
public class CourseEventDTO extends AbstractDatabaseEntityDTO {

    @NotBlank
    private String name;

    private Date date;

    @NotNull
    private Long courseId;

    private List<CourseEventQuestionDTO> questions;

    public CourseEventDTO() {}

    public CourseEventDTO(Long id, Integer version, String name, Date date, Long courseId) {
        this.setId(id);
        this.setVersion(version);
        this.setName(name);
        this.setDate(date);
        this.setCourseId(courseId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public List<CourseEventQuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<CourseEventQuestionDTO> questions) {
        this.questions = questions;
    }
}
