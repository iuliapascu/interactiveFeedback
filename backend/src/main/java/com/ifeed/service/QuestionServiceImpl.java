package com.ifeed.service;

import com.ifeed.mapper.QuestionMapper;
import com.ifeed.model.Question;
import com.ifeed.model.dto.QuestionDTO;
import com.ifeed.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Iulia-Anamaria Pascu on 3/29/2016.
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class QuestionServiceImpl implements QuestionService{

    private QuestionRepository questionRepository;
    private QuestionMapper mapper;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.mapper = questionMapper;
    }

    @Override
    public List<QuestionDTO> getAllQuestions() {
        return mapper.map(questionRepository.findAllOrderedByName());
    }

    @Override
    public List<QuestionDTO> getQuestionsForIds(List<Long> ids) {
        return mapper.map(questionRepository.findAll(ids));
    }

    @Override
    public QuestionDTO find(long id) {
        Question question = questionRepository.findOne(id);
        if (question == null) {
            return null;
        }
        return mapper.map(question);
    }

    @Override
    public QuestionDTO save(QuestionDTO question) {
        Question entity = null;
        if (question.getId() != null) {
            entity = questionRepository.findOne(question.getId());
        }

        if (entity == null) {
            entity = new Question();
        }
        mapper.map(question, entity);

        Question savedQuestion = questionRepository.save(entity);

        return mapper.map(savedQuestion);
    }

    @Override
    public void remove(Long questionId) {
        if (questionId != null) {
            questionRepository.delete(questionId);
        }
    }

}
