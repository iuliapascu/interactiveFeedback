package com.ifeed.service;

import com.ifeed.model.dto.AnswerDTO;

import java.util.List;

/**
 * Created by ipascu on 23.04.2016.
 */
public interface AnswerService {

    AnswerDTO find(long id);

    List<AnswerDTO> getAllQuestionAnswers(Long questionId);

    AnswerDTO save(AnswerDTO answer);

    void remove(Long answerId);

}
