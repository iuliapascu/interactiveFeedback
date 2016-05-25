package com.ifeed.mapper;

import com.ifeed.model.Question;
import com.ifeed.model.dto.QuestionDTO;
import org.springframework.stereotype.Component;

/**
 * Created by Iulia-Anamaria Pascu on 3/29/2016.
 */
@Component
public class QuestionMapper extends CollectionMapper<Question, QuestionDTO> {

    @Override
    public QuestionDTO map(Question entity) {
        if (entity == null) {
            return null;
        }
        QuestionDTO dto = new QuestionDTO();

        dto.setId(entity.getId());
        dto.setVersion(entity.getVersion());
        dto.setTitle(entity.getTitle());
        dto.setRequirement(entity.getRequirement());
        dto.setQuestionType(entity.getQuestionType());
        dto.setGoodKeywords(entity.getGoodKeywords());
        dto.setBadKeywords(entity.getBadKeywords());

        return dto;
    }

    @Override
    public void map(QuestionDTO dto, Question entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setTitle(dto.getTitle());
        entity.setRequirement(dto.getRequirement());
        entity.setQuestionType(dto.getQuestionType());
        entity.setGoodKeywords(dto.getGoodKeywords());
        entity.setBadKeywords(dto.getBadKeywords());
    }
}

