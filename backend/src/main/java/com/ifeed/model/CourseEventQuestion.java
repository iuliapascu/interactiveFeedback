package com.ifeed.model;

import com.ifeed.model.base.IdentifiableEntity;
import com.ifeed.model.enums.QuestionState;

import javax.persistence.*;

/**
 * Created by ipascu on 19.05.2016.
 */
@Entity
@Table(name="course_event_question")
public class CourseEventQuestion extends IdentifiableEntity {

    @Column(name = "question_state", nullable = false, length = 20)
    private QuestionState questionState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_event_id")
    private CourseEvent courseEvent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    public CourseEventQuestion() {}

    public CourseEventQuestion(QuestionState questionState, CourseEvent courseEvent, Question question) {
        this.questionState = questionState;
        this.courseEvent = courseEvent;
        this.question = question;
    }

    public QuestionState getQuestionState() {
        return questionState;
    }

    public void setQuestionState(QuestionState questionState) {
        this.questionState = questionState;
    }

    public CourseEvent getCourseEvent() {
        return courseEvent;
    }

    public void setCourseEvent(CourseEvent courseEvent) {
        this.courseEvent = courseEvent;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
