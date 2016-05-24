package com.ifeed.mapper;

import com.ifeed.model.CourseEvent;
import com.ifeed.model.Question;
import com.ifeed.model.UserAnswer;
import com.ifeed.model.dto.UserAnswerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

/**
 * Created by ipascu on 24.05.2016.
 */
@Component
public class UserAnswerMapper extends CollectionMapper<UserAnswer, UserAnswerDTO> {
    private final EntityManager entityManager;

    @Autowired
    public UserAnswerMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UserAnswerDTO map(UserAnswer entity) {
        if (entity == null) {
            return null;
        }
        UserAnswerDTO dto = new UserAnswerDTO();

        dto.setId(entity.getId());
        dto.setVersion(entity.getVersion());
        dto.setText(entity.getText());
        dto.setPercentage(entity.getPercentage());
        dto.setQuestionId(entity.getQuestion().getId());
        dto.setCourseEventId(entity.getCourseEvent().getId());

        return dto;
    }

    @Override
    public void map(UserAnswerDTO dto, UserAnswer entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setText(dto.getText());
        entity.setPercentage(dto.getPercentage());
        if (dto.getQuestionId() != null) {
            entity.setQuestion(entityManager.getReference(Question.class, dto.getQuestionId()));
        }
        if (dto.getCourseEventId() != null) {
            entity.setCourseEvent(entityManager.getReference(CourseEvent.class, dto.getCourseEventId()));
        }
    }
}
