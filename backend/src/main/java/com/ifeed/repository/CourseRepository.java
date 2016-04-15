package com.ifeed.repository;

import com.ifeed.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Iulia-Anamaria Pascu on 3/28/2016.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course as c ORDER BY c.name ASC")
    List<Course> findAllOrderedByName();

}