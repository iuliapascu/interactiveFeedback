package com.ifeed.service;

import com.ifeed.model.dto.CourseDTO;

import java.util.List;

/**
 * Created by Iulia-Anamaria Pascu on 3/28/2016.
 */
public interface CourseService {

    CourseDTO find(long id);

    List<CourseDTO> getAllCourses();

    CourseDTO save(CourseDTO course);

    void remove(Long courseId);

   /* void assignQuestionToCourse(Integer questionId, Integer topicId);*/

}
