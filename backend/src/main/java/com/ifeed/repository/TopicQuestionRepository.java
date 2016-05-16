package com.ifeed.repository;

import com.ifeed.model.TopicQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ipascu on 14.05.2016.
 */
@Repository
public interface TopicQuestionRepository extends JpaRepository<TopicQuestion, Long> {

    @Query("SELECT tq FROM TopicQuestion as tq WHERE tq.topic.id = :topicId AND tq.question.id = :questionId")
    TopicQuestion findOne(@Param("topicId") long topicId, @Param("questionId") long questionId);

    @Query("DELETE FROM TopicQuestion as tq WHERE tq.topic.id = :topicId AND tq.question.id = :questionId")
    void delete(@Param("topicId") long topicId, @Param("questionId") long questionId);

    @Query("SELECT tq.question.id FROM TopicQuestion as tq WHERE tq.topic.id = :topicId")
    List<Long> findTopicQuestionIds(@Param("topicId") long topicId);

    @Query("SELECT tq.topic.id FROM TopicQuestion as tq WHERE tq.question.id = :questionId")
    List<Long> findQuestionTopicIds(@Param("questionId") long questionId);

}
