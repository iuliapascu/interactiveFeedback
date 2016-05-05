package com.ifeed.service;

import com.ifeed.mapper.AnswerMapper;
import com.ifeed.model.Answer;
import com.ifeed.model.dto.AnswerDTO;
import com.ifeed.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ipascu on 23.04.2016.
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class AnswerServiceImpl implements AnswerService{

    private AnswerRepository answerRepository;
    private AnswerMapper mapper;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository, AnswerMapper topicMapper) {
        this.answerRepository = answerRepository;
        this.mapper = topicMapper;
    }

    @Override
    public List<AnswerDTO> getAllQuestionAnswers(Long questionId) {
        return mapper.map(answerRepository.findQuestionAnswersOrderedByPosition(questionId));
    }

    @Override
    public AnswerDTO find(long id) {
        Answer answer = answerRepository.findOne(id);
        if (answer == null) {
            return null;
        }
        return mapper.map(answer);
    }

    @Override
    public AnswerDTO save(AnswerDTO answer) {
        Answer entity = null;
        if (answer.getId() != null) {
            entity = answerRepository.findOne(answer.getId());
        }

        if (entity == null) {
            entity = new Answer();
        }
        mapper.map(answer, entity);

        Answer savedAnswer = answerRepository.save(entity);

        return mapper.map(savedAnswer);
    }

    @Override
    public void remove(Long answerId) {
        if (answerId != null) {
            answerRepository.delete(answerId);
        }
    }

}