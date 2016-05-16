package com.ifeed.model;

import java.io.Serializable;

/**
 * Created by ipascu on 14.05.2016.
 */
public class TopicQuestionId implements Serializable {

    private long topicId;

    private long questionId;

    public int hashCode() {
        return (int)(topicId + questionId);
    }

    public boolean equals(Object object) {
        if (object instanceof TopicQuestionId) {
            TopicQuestionId otherId = (TopicQuestionId) object;
            return (otherId.topicId == this.topicId) && (otherId.topicId == this.topicId);
        }
        return false;
    }

}