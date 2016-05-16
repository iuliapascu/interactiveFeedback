package com.ifeed.model.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by ipascu on 14.05.2016.
 */
public class TopicQuestionDTO extends AbstractDatabaseEntityDTO{

    @NotNull
    private Long topicId;

    @NotNull
    private Long questionId;

    public TopicQuestionDTO() {

    }

    public TopicQuestionDTO(Long topicId, Long questionId) {
        this.setTopicId(topicId);
        this.setQuestionId(questionId);
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
