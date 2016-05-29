package com.ifeed.service.evaluation;

import com.ifeed.model.dto.UserAnswerDTO;

/**
 * Created by ipascu on 24.05.2016.
 */
public interface EvaluationService {

    int computeCorrectnessPercentage(UserAnswerDTO userAnswer);
}
