package com.ifeed.mapper;

import com.ifeed.model.Course;
import com.ifeed.model.Topic;
import com.ifeed.model.dto.TopicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

/**
 * Created by Iulia-Anamaria Pascu on 3/29/2016.
 */
@Component
public class TopicMapper extends CollectionMapper<Topic, TopicDTO> {
    private EntityManager entityManager;

    @Autowired
    public TopicMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public TopicDTO map(Topic entity) {
        if (entity == null) {
            return null;
        }
        TopicDTO dto = new TopicDTO();

        dto.setId(entity.getId());
        dto.setVersion(entity.getVersion());
        dto.setTitle(entity.getTitle());
        dto.setPosition(entity.getPosition());
        dto.setCourseId(entity.getCourse().getId());

        return dto;
    }

    @Override
    public void map(TopicDTO dto, Topic entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setTitle(dto.getTitle());
        entity.setPosition(dto.getPosition());
        if (dto.getCourseId() != null) {
            entity.setCourse(entityManager.getReference(Course.class, dto.getCourseId()));
        }
    }
}

