package com.ifeed.service;

import com.ifeed.mapper.TopicQuestionMapper;
import com.ifeed.model.TopicQuestion;
import com.ifeed.model.dto.QuestionDTO;
import com.ifeed.model.dto.TopicDTO;
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
    private QuestionService questionService;
    private TopicService topicService;
    private TopicQuestionMapper mapper;

    @Autowired
    public TopicQuestionServiceImpl(TopicQuestionRepository topicQuestionRepository, TopicQuestionMapper mapper,
                                    QuestionService questionService, TopicService topicService) {
        this.topicQuestionRepository = topicQuestionRepository;
        this.mapper = mapper;
        this.questionService = questionService;
        this.topicService = topicService;
    }

    @Override
    public List<QuestionDTO> getAllTopicQuestions(Long topicId) {
        return questionService.getQuestionsForIds(topicQuestionRepository.findTopicQuestionIds(topicId));
    }

    @Override
    public List<TopicDTO> getAllQuestionTopics(Long questionId) {
        return topicService.getTopicsForIds(topicQuestionRepository.findQuestionTopicIds(questionId));
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
