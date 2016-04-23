package com.ifeed.repository;

import com.ifeed.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Iulia-Anamaria Pascu on 3/29/2016.
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query("SELECT t FROM Topic as t WHERE t.course.id = :courseId ORDER BY t.position ASC")
    List<Topic> findCourseTopicsOrderedByPosition(@Param("courseId") long courseId);

}
