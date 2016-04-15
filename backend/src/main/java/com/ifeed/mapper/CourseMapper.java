package com.ifeed.mapper;

import com.ifeed.model.Course;
import com.ifeed.model.dto.CourseDTO;
import org.springframework.stereotype.Component;

/**
 * Created by Iulia-Anamaria Pascu on 3/28/2016.
 */
@Component
public class CourseMapper extends CollectionMapper<Course, CourseDTO> {

    @Override
    public CourseDTO map(Course entity) {
        if (entity == null) {
            return null;
        }
        CourseDTO dto = new CourseDTO();

        dto.setId(entity.getId());
        dto.setVersion(entity.getVersion());
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());

        return dto;
    }

    @Override
    public void map(CourseDTO dto, Course entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
    }
}

