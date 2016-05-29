package com.ifeed.repository;

import com.ifeed.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ipascu on 23.04.2016.
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("SELECT a FROM Answer as a WHERE a.question.id = :questionId ORDER BY a.position ASC")
    List<Answer> findQuestionAnswersOrderedByPosition(@Param("questionId") long questionId);

    @Query("SELECT a FROM Answer as a WHERE a.isCorrect = true AND a.question.id = :questionId")
    List<Answer> findQuestionCorrectAnswers(@Param("questionId") long questionId);
}
