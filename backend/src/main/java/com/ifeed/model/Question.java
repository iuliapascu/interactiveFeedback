package com.ifeed.model;

import com.ifeed.model.base.IdentifiableEntity;
import com.ifeed.model.enums.QuestionType;

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

    @Column(nullable = false, length = 20)
    private QuestionType questionType;

    @Column
    private String goodKeywords;

    @Column
    private String badKeywords;

    public Question() {
    }

    public Question(String title, String requirement, QuestionType questionType, String goodKeywords, String badKeywords) {
        this.title = title;
        this.requirement = requirement;
        this.questionType = questionType;
        this.goodKeywords = goodKeywords;
        this.badKeywords = badKeywords;
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

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public String getGoodKeywords() {
        return goodKeywords;
    }

    public void setGoodKeywords(String goodKeywords) {
        this.goodKeywords = goodKeywords;
    }

    public String getBadKeywords() {
        return badKeywords;
    }

    public void setBadKeywords(String badKeywords) {
        this.badKeywords = badKeywords;
    }
}
