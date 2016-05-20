package com.ifeed.mapper;

import com.ifeed.model.Question;
import com.ifeed.model.Topic;
import com.ifeed.model.TopicQuestion;
import com.ifeed.model.dto.TopicQuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

/**
 * Created by ipascu on 14.05.2016.
 */
@Component
public class TopicQuestionMapper extends CollectionMapper<TopicQuestion, TopicQuestionDTO> {
    private final EntityManager entityManager;

    @Autowired
    public TopicQuestionMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public TopicQuestionDTO map(TopicQuestion entity) {
        if (entity == null) {
            return null;
        }
        TopicQuestionDTO dto = new TopicQuestionDTO();

        dto.setTopicId(entity.getTopic().getId());
        dto.setQuestionId(entity.getQuestion().getId());
        dto.setVersion(entity.getVersion());

        return dto;
    }

    @Override
    public void map(TopicQuestionDTO dto, TopicQuestion entity) {
        if (dto == null || entity == null) {
            return;
        }

        if (dto.getTopicId() != null) {
            entity.setTopic(entityManager.getReference(Topic.class, dto.getTopicId()));
        }

        if (dto.getQuestionId() != null) {
            entity.setQuestion(entityManager.getReference(Question.class, dto.getQuestionId()));
        }
    }
}
