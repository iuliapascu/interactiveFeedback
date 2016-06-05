package com.ifeed.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by ipascu on 24.05.2016.
 */
public class UserAnswerDTO extends AbstractDatabaseEntityDTO {
    @NotBlank
    private String text;

    private Integer percentage;

    @NotNull
    private Long questionId;

    @NotNull
    private Long courseEventId;

    public UserAnswerDTO() {

    }

    public UserAnswerDTO(String text, Long courseEventId, Long questionId) {
        this.setText(text);
        this.setQuestionId(questionId);
        this.setCourseEventId(courseEventId);
    }

    public UserAnswerDTO(Long id, Integer version, String text, Long questionId, Long courseEventId) {
        this.setId(id);
        this.setVersion(version);
        this.setText(text);
        this.setQuestionId(questionId);
        this.setCourseEventId(courseEventId);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getCourseEventId() {
        return courseEventId;
    }

    public void setCourseEventId(Long courseEventId) {
        this.courseEventId = courseEventId;
    }
}
