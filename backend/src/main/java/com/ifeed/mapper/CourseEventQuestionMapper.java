package com.ifeed.mapper;

import com.ifeed.model.CourseEvent;
import com.ifeed.model.CourseEventQuestion;
import com.ifeed.model.dto.CourseEventQuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

/**
 * Created by ipascu on 19.05.2016.
 */
@Component
public class CourseEventQuestionMapper extends CollectionMapper<CourseEventQuestion, CourseEventQuestionDTO> {

    private final EntityManager entityManager;
    private final QuestionMapper questionMapper;

    @Autowired
    public CourseEventQuestionMapper(EntityManager entityManager, QuestionMapper questionMapper) {
        this.entityManager = entityManager;
        this.questionMapper = questionMapper;
    }

    @Override
    public CourseEventQuestionDTO map(CourseEventQuestion entity) {
        if (entity == null) {
            return null;
        }
        CourseEventQuestionDTO dto = new CourseEventQuestionDTO();

        dto.setCourseEventId(entity.getCourseEvent().getId());
        dto.setQuestion(questionMapper.map(entity.getQuestion()));
        dto.setVersion(entity.getVersion());
        dto.setId(entity.getId());
        dto.setQuestionState(entity.getQuestionState());
        return dto;
    }

    @Override
    public void map(CourseEventQuestionDTO dto, CourseEventQuestion entity) {
        if (dto == null || entity == null) {
            return;
        }

        if (dto.getCourseEventId() != null) {
            entity.setCourseEvent(entityManager.getReference(CourseEvent.class, dto.getCourseEventId()));
        }

        if (dto.getQuestion() != null) {
            questionMapper.map(dto.getQuestion(), entity.getQuestion());
        }

        if (dto.getId() != null && dto.getVersion() != null) {
            entity.setId(dto.getId());
            entity.setVersion(dto.getVersion());
        }

        entity.setQuestionState(dto.getQuestionState());
    }
}
