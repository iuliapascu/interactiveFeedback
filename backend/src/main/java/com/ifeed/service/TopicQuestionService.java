package com.ifeed.service;

import com.ifeed.model.dto.QuestionDTO;
import com.ifeed.model.dto.TopicDTO;
import com.ifeed.model.dto.TopicQuestionDTO;

import java.util.List;

/**
 * Created by ipascu on 14.05.2016.
 */
public interface TopicQuestionService {

    TopicQuestionDTO find(Long topicId, Long questionId);

    List<QuestionDTO> getAllTopicQuestions(Long topicId);

    List<TopicDTO> getAllQuestionTopics(Long questionId);

    TopicQuestionDTO save(TopicQuestionDTO topicQuestion);

    void remove(Long topicId, Long questionId);
}
