package com.ifeed.service.evaluation;

import com.ifeed.model.dto.UserAnswerDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ipascu on 24.05.2016.
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class EvaluationServiceImpl implements EvaluationService {

    @Override
    public int computeCorrectnessPercentage(UserAnswerDTO userAnswer) {
        return 0;
    }
}
