package com.ifeed.mapper;

import com.ifeed.model.Course;
import com.ifeed.model.CourseEvent;
import com.ifeed.model.dto.CourseEventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

/**
 * Created by ipascu on 19.05.2016.
 */
@Component
public class CourseEventMapper extends CollectionMapper<CourseEvent, CourseEventDTO> {
    private final EntityManager entityManager;

    @Autowired
    public CourseEventMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public CourseEventDTO map(CourseEvent entity) {
        if (entity == null) {
            return null;
        }
        CourseEventDTO dto = new CourseEventDTO();

        dto.setId(entity.getId());
        dto.setVersion(entity.getVersion());
        dto.setName(entity.getName());
        dto.setDate(entity.getDate());
        dto.setCourseId(entity.getCourse().getId());

        return dto;
    }

    @Override
    public void map(CourseEventDTO dto, CourseEvent entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        if (dto.getCourseId() != null) {
            entity.setCourse(entityManager.getReference(Course.class, dto.getCourseId()));
        }
    }
}
