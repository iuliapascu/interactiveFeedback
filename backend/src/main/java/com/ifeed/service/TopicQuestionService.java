package com.ifeed.service;

import com.ifeed.model.dto.TopicQuestionDTO;

import java.util.List;

/**
 * Created by ipascu on 14.05.2016.
 */
public interface TopicQuestionService {

    TopicQuestionDTO find(Long topicId, Long questionId);

    List<Long> getAllTopicQuestionIds(Long topicId);

    Integer getCountTopicQuestions(Long topicId);

    List<Long> getAllQuestionTopicIds(Long questionId);

    Integer getCountQuestionTopics(Long questionId);

    TopicQuestionDTO save(TopicQuestionDTO topicQuestion);

    void remove(Long topicId, Long questionId);
}
