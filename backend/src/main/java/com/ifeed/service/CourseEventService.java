package com.ifeed.service;

import com.ifeed.model.dto.CourseEventDTO;
import com.ifeed.model.dto.CourseEventQuestionDTO;

import java.util.List;

/**
 * Created by ipascu on 19.05.2016.
 */
public interface CourseEventService {

    CourseEventDTO find(long id);

    List<CourseEventDTO> getAllCourseEventsByCourseId(Long courseId);

    List<CourseEventQuestionDTO> getAllCourseEventQuestions(Long courseEventId);

    CourseEventDTO save(CourseEventDTO courseEvent, List<String> questionIds);

    CourseEventDTO duplicate(Long oldEventId, String name, Long courseId);

    void remove(Long courseEventId);

}
