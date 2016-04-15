package com.ifeed.service;

import com.ifeed.mapper.CourseMapper;
import com.ifeed.model.Course;
import com.ifeed.model.dto.CourseDTO;
import com.ifeed.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Iulia-Anamaria Pascu on 3/28/2016.
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class CourseServiceImpl implements CourseService{

    private CourseRepository courseRepository;
    private CourseMapper mapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.mapper = courseMapper;
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return mapper.map(courseRepository.findAllOrderedByName());
    }

    @Override
    public CourseDTO find(long id) {
        Course course = courseRepository.findOne(id);
        if (course == null) {
            return null;
        }
        return mapper.map(course);
    }

    @Override
    public CourseDTO save(CourseDTO course) {
        Course entity = null;
        if (course.getId() != null) {
            entity = courseRepository.findOne(course.getId());
        }

        if (entity == null) {
            entity = new Course();
        }
        mapper.map(course, entity);

        Course savedCourse = courseRepository.save(entity);

        return mapper.map(savedCourse);
    }

    @Override
    public void remove(Long courseId) {
        if (courseId != null) {
            courseRepository.delete(courseId);
        }
    }

}
