package com.ifeed.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by ipascu on 23.04.2016.
 */
public class AnswerDTO extends AbstractDatabaseEntityDTO{

    @NotBlank
    private String text;

    @NotNull
    private Boolean isCorrect;

    @NotBlank
    private Integer position;

    @NotNull
    private Long questionId;

    public AnswerDTO() {

    }

    public AnswerDTO(Long id, Integer version, String text, Boolean isCorrect, Integer position, Long questionId) {
        this.setId(id);
        this.setVersion(version);
        this.setText(text);
        this.setCorrect(isCorrect);
        this.setPosition(position);
        this.setQuestionId(questionId);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}

