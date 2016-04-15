package com.ifeed.repository;

import com.ifeed.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Iulia-Anamaria Pascu on 3/29/2016.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question as q ORDER BY q.title ASC")
    List<Question> findAllOrderedByName();

}