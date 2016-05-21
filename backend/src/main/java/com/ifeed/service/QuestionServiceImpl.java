package com.ifeed.service;

import com.ifeed.exception.EntityInUseException;
import com.ifeed.mapper.QuestionMapper;
import com.ifeed.model.Question;
import com.ifeed.model.dto.AnswerDTO;
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

    private final QuestionRepository questionRepository;
    private final QuestionMapper mapper;

    private final AnswerService answerService;
    private final TopicQuestionService topicQuestionService;
    private final CourseEventQuestionService courseEventQuestionService;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, QuestionMapper questionMapper,
                               AnswerService answerService, TopicQuestionService topicQuestionService,
                               CourseEventQuestionService courseEventQuestionService) {
        this.questionRepository = questionRepository;
        this.mapper = questionMapper;
        this.answerService = answerService;
        this.topicQuestionService = topicQuestionService;
        this.courseEventQuestionService = courseEventQuestionService;
    }

    @Override
    public List<QuestionDTO> getAllQuestions() {
        return mapper.map(questionRepository.findAllOrderedByName());
    }

    @Override
    public List<QuestionDTO> getQuestionsByTopicId(Long id) {
        List<Long> ids = topicQuestionService.getAllTopicQuestionIds(id);
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
    public void remove(Long questionId) throws EntityInUseException{
        if (topicQuestionService.getCountQuestionTopics(questionId) > 0) {
            throw new EntityInUseException("Remove not allowed. The question is assigned to one or more topics.");
        }
        if (courseEventQuestionService.findCountQuestionAssignments(questionId) > 0) {
            throw new EntityInUseException("Remove not allowed. The question is used in one or more events.");
        }
        if (questionId != null) {
            List<AnswerDTO> questionAnswers = answerService.getAllQuestionAnswers(questionId);
            for (AnswerDTO answer: questionAnswers) {
                answerService.remove(answer.getId());
            }
            questionRepository.delete(questionId);
        }
    }

}
