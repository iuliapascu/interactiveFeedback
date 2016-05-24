package com.ifeed.service;

import com.ifeed.mapper.UserAnswerMapper;
import com.ifeed.model.UserAnswer;
import com.ifeed.model.dto.UserAnswerDTO;
import com.ifeed.repository.UserAnswerRepository;
import com.ifeed.service.evaluation.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ipascu on 24.05.2016.
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class UserAnswerServiceImpl implements UserAnswerService{

    private final UserAnswerRepository userAnswerRepository;
    private final UserAnswerMapper mapper;
    private final EvaluationService evaluationService;

    @Autowired
    public UserAnswerServiceImpl(UserAnswerRepository userAnswerRepository, UserAnswerMapper topicMapper, EvaluationService evaluationService) {
        this.userAnswerRepository = userAnswerRepository;
        this.mapper = topicMapper;
        this.evaluationService = evaluationService;
    }

    @Override
    public UserAnswerDTO find(long id) {
        UserAnswer answer = userAnswerRepository.findOne(id);
        if (answer == null) {
            return null;
        }
        return mapper.map(answer);
    }

    @Override
    public List<UserAnswerDTO> getAllUserAnswers(Long courseEventId, Long questionId) {
        return mapper.map(userAnswerRepository.findUserAnswersOrderedByPercentage(courseEventId, questionId));
    }

    @Override
    public UserAnswerDTO save(UserAnswerDTO userAnswer) {
        UserAnswer entity = null;
        if (userAnswer.getId() != null) {
            entity = userAnswerRepository.findOne(userAnswer.getId());
        }

        if (entity == null) {
            entity = new UserAnswer();
        }

        userAnswer.setPercentage(evaluationService.computeCorrectnessPercentage(userAnswer));

        mapper.map(userAnswer, entity);
        UserAnswer savedUserAnswer = userAnswerRepository.save(entity);

        return mapper.map(savedUserAnswer);
    }

    @Override
    public void remove(Long userAnswerId) {
        if (userAnswerId != null) {
            userAnswerRepository.delete(userAnswerId);
        }
    }
}
