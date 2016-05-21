package com.ifeed.service;

import com.ifeed.mapper.TopicQuestionMapper;
import com.ifeed.model.TopicQuestion;
import com.ifeed.model.dto.TopicQuestionDTO;
import com.ifeed.repository.TopicQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ipascu on 14.05.2016.
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class TopicQuestionServiceImpl implements TopicQuestionService {

    private TopicQuestionRepository topicQuestionRepository;
    private TopicQuestionMapper mapper;

    @Autowired
    public TopicQuestionServiceImpl(TopicQuestionRepository topicQuestionRepository, TopicQuestionMapper mapper) {
        this.topicQuestionRepository = topicQuestionRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Long> getAllTopicQuestionIds(Long topicId) {
        return topicQuestionRepository.findTopicQuestionIds(topicId);
    }

    @Override
    public Integer getCountTopicQuestions(Long topicId) {
        return topicQuestionRepository.getCountTopicQuestions(topicId);
    }

    @Override
    public List<Long> getAllQuestionTopicIds(Long questionId) {
        return topicQuestionRepository.findQuestionTopicIds(questionId);
    }

    @Override
    public Integer getCountQuestionTopics(Long questionId) {
        return topicQuestionRepository.getCountQuestionTopics(questionId);
    }

    @Override
    public TopicQuestionDTO find(Long topicId, Long questionId) {
        TopicQuestion topicQuestion = topicQuestionRepository.findOne(topicId, questionId);
        return (topicQuestion != null)? mapper.map(topicQuestion) : null;
    }

    @Override
    public TopicQuestionDTO save(TopicQuestionDTO topicQuestion) {
        TopicQuestion entity = null;
        if (topicQuestion.getTopicId() != null && topicQuestion.getQuestionId() != null) {
            entity = topicQuestionRepository.findOne(topicQuestion.getTopicId(), topicQuestion.getQuestionId());
        }

        if (entity == null) {
            entity = new TopicQuestion();
        }
        mapper.map(topicQuestion, entity);

        TopicQuestion savedTopicQuestion = topicQuestionRepository.save(entity);

        return mapper.map(savedTopicQuestion);
    }

    @Override
    public void remove(Long topicId, Long questionId) {
        if (topicId != null && questionId != null) {
            topicQuestionRepository.delete(topicId, questionId);
        }
    }

}
