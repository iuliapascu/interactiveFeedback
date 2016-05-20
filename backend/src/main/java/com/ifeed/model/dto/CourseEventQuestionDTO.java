package com.ifeed.model.dto;

import com.ifeed.model.enums.QuestionState;

import javax.validation.constraints.NotNull;

/**
 * Created by ipascu on 19.05.2016.
 */
public class CourseEventQuestionDTO extends AbstractDatabaseEntityDTO {

    @NotNull
    private Long courseEventId;

    @NotNull
    private QuestionDTO question;

    @NotNull
    private QuestionState questionState;

    public CourseEventQuestionDTO() {}

    public CourseEventQuestionDTO(Long id, Integer version, QuestionState questionState, Long courseEventId, QuestionDTO question) {
        this.setId(id);
        this.setVersion(version);
        this.setQuestionState(questionState);
        this.setCourseEventId(courseEventId);
        this.setQuestion(question);
    }

    public Long getCourseEventId() {
        return courseEventId;
    }

    public void setCourseEventId(Long courseEventId) {
        this.courseEventId = courseEventId;
    }

    public QuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDTO question) {
        this.question = question;
    }

    public QuestionState getQuestionState() {
        return questionState;
    }

    public void setQuestionState(QuestionState questionState) {
        this.questionState = questionState;
    }
}
