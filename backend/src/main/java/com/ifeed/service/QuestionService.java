package com.ifeed.service;

import com.ifeed.exception.EntityInUseException;
import com.ifeed.model.dto.QuestionDTO;

import java.util.List;

/**
 * Created by Iulia-Anamaria Pascu on 3/29/2016.
 */
public interface QuestionService {

    QuestionDTO find(long id);

    QuestionDTO save(QuestionDTO question);

    void remove(Long questionId) throws EntityInUseException;

    List<QuestionDTO> getAllQuestions();

    List<QuestionDTO> getQuestionsByTopicId(Long id);

}
