package com.ifeed.model;

import com.ifeed.model.base.IdentifiableEntity;

import javax.persistence.*;

/**
 * Created by ipascu on 14.05.2016.
 */
@Entity
@Table(name="topic_question")
@IdClass(TopicQuestionId.class)
public class TopicQuestion extends IdentifiableEntity {

    @ManyToOne
    @JoinColumn(name="topic_id", referencedColumnName="id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name="question_id", referencedColumnName="id")
    private Question question;

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
