package com.ifeed.service;

import com.ifeed.mapper.CourseEventQuestionMapper;
import com.ifeed.model.CourseEvent;
import com.ifeed.model.CourseEventQuestion;
import com.ifeed.model.Question;
import com.ifeed.model.dto.CourseEventQuestionDTO;
import com.ifeed.model.enums.QuestionState;
import com.ifeed.repository.CourseEventQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ipascu on 19.05.2016.
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class CourseEventQuestionServiceImpl implements CourseEventQuestionService {

    private final CourseEventQuestionRepository courseEventQuestionRepository;
    private final CourseEventQuestionMapper mapper;
    private final EntityManager entityManager;

    @Autowired
    public CourseEventQuestionServiceImpl(CourseEventQuestionRepository courseEventQuestionRepository, CourseEventQuestionMapper mapper,
                                          EntityManager entityManager) {
        this.courseEventQuestionRepository = courseEventQuestionRepository;
        this.mapper = mapper;
        this.entityManager = entityManager;
    }

    @Override
    public List<CourseEventQuestionDTO> getAllCourseEventQuestionsByCourseEventId(Long courseEventId) {
        return mapper.map(courseEventQuestionRepository.findCourseEventQuestionsByCourseEventId(courseEventId));
    }

    @Override
    public Integer findCountQuestionAssignments(long questionId) {
        return courseEventQuestionRepository.findCountQuestionAssignments(questionId);
    }

    @Override
    public CourseEventQuestionDTO find(long id) {
        CourseEventQuestion courseEventQuestion = courseEventQuestionRepository.findOne(id);
        if (courseEventQuestion == null) {
            return null;
        }
        return mapper.map(courseEventQuestion);
    }

    @Override
    public CourseEventQuestionDTO save(CourseEventQuestionDTO courseEventQuestion) {
        CourseEventQuestion entity = null;
        if (courseEventQuestion.getId() != null) {
            entity = courseEventQuestionRepository.findOne(courseEventQuestion.getId());
        }

        if (entity == null) {
            entity = new CourseEventQuestion();
        }
        mapper.map(courseEventQuestion, entity);

        CourseEventQuestion savedCourseEventQuestion = courseEventQuestionRepository.save(entity);

        return mapper.map(savedCourseEventQuestion);
    }

    @Override
    public void addEntitiesWithIds(Long questionEventId, List<Long> questionIds) {
        List<CourseEventQuestion> courseEventQuestions = new ArrayList<>();

        for (long id: questionIds) {
            CourseEventQuestion ceQuestion = new CourseEventQuestion();
            ceQuestion.setCourseEvent(entityManager.getReference(CourseEvent.class, questionEventId));
            ceQuestion.setQuestionState(QuestionState.PREPARATION);
            ceQuestion.setQuestion(entityManager.getReference(Question.class, id));

            courseEventQuestions.add(ceQuestion);
        }

        courseEventQuestionRepository.save(courseEventQuestions);
    }


    @Override
    public void duplicateEventQuestions(Long oldEventId, Long courseEventId) {
        List<Long> questionIds = courseEventQuestionRepository.findCourseEventQuestionIdsByCourseEventId(oldEventId);
        addEntitiesWithIds(courseEventId, questionIds);
    }

    @Override
    public void remove(Long courseEventQuestionId) {
        if (courseEventQuestionId != null) {
            courseEventQuestionRepository.delete(courseEventQuestionId);
        }
    }

    @Override
    public void removeAllForCourseEvent(Long courseEventId) {
        if (courseEventId != null) {
            courseEventQuestionRepository.removeAllForCourseEventId(courseEventId);
        }
    }

}