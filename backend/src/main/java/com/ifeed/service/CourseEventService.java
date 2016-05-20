package com.ifeed.service;

import com.ifeed.model.dto.CourseEventDTO;

import java.util.List;

/**
 * Created by ipascu on 19.05.2016.
 */
public interface CourseEventService {

    CourseEventDTO find(long id);

    List<CourseEventDTO> getAllCourseEventsByCourseId(Long courseId);

    CourseEventDTO save(CourseEventDTO courseEvent, List<String> questionIds);

    void remove(Long courseEventId);

}
