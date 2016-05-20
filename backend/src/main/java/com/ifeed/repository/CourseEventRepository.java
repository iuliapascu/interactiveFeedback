package com.ifeed.repository;

import com.ifeed.model.CourseEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ipascu on 19.05.2016.
 */
@Repository
public interface CourseEventRepository extends JpaRepository<CourseEvent, Long> {

    @Query("SELECT ce FROM CourseEvent as ce WHERE ce.course.id = :courseId ORDER BY ce.date DESC")
    List<CourseEvent> findCourseEventsByCourseId(@Param("courseId") long courseId);
}
