package com.ifeed.service;

import com.ifeed.model.dto.CourseEventQuestionDTO;

import java.util.List;

/**
 * Created by ipascu on 19.05.2016.
 */
public interface CourseEventQuestionService {

    CourseEventQuestionDTO find(long id);

    List<CourseEventQuestionDTO> getAllCourseEventQuestionsByCourseEventId(Long courseEventId);

    Integer findCountQuestionAssignments(long questionId);

    CourseEventQuestionDTO save(CourseEventQuestionDTO courseEventQuestion);

    void addEntitiesWithIds(Long questionEventId, List<Long> questionIds);

    void duplicateEventQuestions(Long oldEventId, Long courseEventId);

    void remove(Long courseEventQuestionId);

    void removeAllForCourseEvent(Long courseEventId);

}