package com.ifeed.mapper;

import com.ifeed.model.Answer;
import com.ifeed.model.Question;
import com.ifeed.model.dto.AnswerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

/**
 * Created by ipascu on 23.04.2016.
 */
@Component
public class AnswerMapper extends CollectionMapper<Answer, AnswerDTO> {
    private final EntityManager entityManager;

    @Autowired
    public AnswerMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public AnswerDTO map(Answer entity) {
        if (entity == null) {
            return null;
        }
        AnswerDTO dto = new AnswerDTO();

        dto.setId(entity.getId());
        dto.setVersion(entity.getVersion());
        dto.setText(entity.getText());
        dto.setCorrect(entity.isCorrect());
        dto.setPosition(entity.getPosition());
        dto.setQuestionId(entity.getQuestion().getId());

        return dto;
    }

    @Override
    public void map(AnswerDTO dto, Answer entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setText(dto.getText());
        entity.setCorrect(dto.getCorrect());
        entity.setPosition(dto.getPosition());
        if (dto.getQuestionId() != null) {
            entity.setQuestion(entityManager.getReference(Question.class, dto.getQuestionId()));
        }
    }
}
