package com.ifeed.service;

import com.ifeed.model.dto.UserAnswerDTO;

import java.util.List;

/**
 * Created by ipascu on 24.05.2016.
 */
public interface UserAnswerService {

    UserAnswerDTO find(long id);

    List<UserAnswerDTO> getAllUserAnswers(Long courseEventId, Long questionId);

    UserAnswerDTO save(UserAnswerDTO userAnswer);

    void remove(Long userAnswerId);
}
