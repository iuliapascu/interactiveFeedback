package com.ifeed.model.dto;

import com.ifeed.model.enums.QuestionType;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Iulia-Anamaria Pascu on 3/29/2016.
 */
public class QuestionDTO extends AbstractDatabaseEntityDTO{

    @NotBlank
    private String title;

    @NotBlank
    private String requirement;

    @NotBlank
    private QuestionType questionType;

    private String goodKeywords;

    private String badKeywords;

    public QuestionDTO() {

    }

    public QuestionDTO(Long id, Integer version, String title, String requirement, QuestionType questionType, String goodKeywords, String badKeywords) {
        this.setId(id);
        this.setVersion(version);
        this.setTitle(title);
        this.setRequirement(requirement);
        this.setQuestionType(questionType);
        this.setGoodKeywords(goodKeywords);
        this.setBadKeywords(badKeywords);
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
