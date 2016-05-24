package com.ifeed.repository;

import com.ifeed.model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ipascu on 24.05.2016.
 */
@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {

    @Query("SELECT ua FROM UserAnswer as ua WHERE ua.courseEvent.id = :courseEventId AND ua.question.id = :questionId ORDER BY ua.percentage DESC")
    List<UserAnswer> findUserAnswersOrderedByPercentage(@Param("courseEventId") long courseEventId, @Param("questionId") long questionId);
}
