package com.ifeed.model;

import com.ifeed.model.base.IdentifiableEntity;

import javax.persistence.*;

/**
 * Created by ipascu on 23.04.2016.
 */
@Entity
public class Answer extends IdentifiableEntity {

    @Column(unique = true, nullable = false, length = 500)
    private String text;

    @Column(nullable = false)
    private boolean isCorrect;

    @Column(unique = true, nullable = false)
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    public Answer() {
    }

    public Answer(String text, boolean isCorrect, Integer position, Question question) {
        this.text = text;
        this.isCorrect = isCorrect;
        this.position = position;
        this.question = question;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
