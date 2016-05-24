package com.ifeed.model;

import com.ifeed.model.base.IdentifiableEntity;

import javax.persistence.*;

/**
 * Created by ipascu on 24.05.2016.
 */
@Entity
public class UserAnswer extends IdentifiableEntity {

    @Column(unique = true, nullable = false, length = 1000)
    private String text;

    @Column(unique = true)
    private Integer percentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_event_id")
    private CourseEvent courseEvent;

    public UserAnswer() { }

    public UserAnswer(String text, Integer percentage, Question question, CourseEvent courseEvent) {
        this.text = text;
        this.percentage = percentage;
        this.question = question;
        this.courseEvent = courseEvent;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public CourseEvent getCourseEvent() {
        return courseEvent;
    }

    public void setCourseEvent(CourseEvent courseEvent) {
        this.courseEvent = courseEvent;
    }
}
