package com.ifeed.repository;

import com.ifeed.model.CourseEventQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ipascu on 19.05.2016.
 */

@Repository
public interface CourseEventQuestionRepository extends JpaRepository<CourseEventQuestion, Long> {

    @Query("SELECT ceq FROM CourseEventQuestion as ceq WHERE ceq.courseEvent.id = :courseEventId")
    List<CourseEventQuestion> findCourseEventQuestionsByCourseEventId(@Param("courseEventId") long courseEventId);

    @Query("SELECT COUNT(ceq) FROM CourseEventQuestion as ceq WHERE ceq.question.id = :questionId")
    Integer findCountQuestionAssignments(@Param("questionId") long questionId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CourseEventQuestion as ceq WHERE ceq.courseEvent.id = :courseEventId")
    void removeAllForCourseEventId(@Param("courseEventId") long courseEventId);
}