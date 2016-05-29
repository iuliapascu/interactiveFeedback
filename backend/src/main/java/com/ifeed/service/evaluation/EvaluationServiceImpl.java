package com.ifeed.service.evaluation;

import com.ifeed.model.dto.AnswerDTO;
import com.ifeed.model.dto.QuestionDTO;
import com.ifeed.model.dto.UserAnswerDTO;
import com.ifeed.service.AnswerService;
import com.ifeed.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ipascu on 24.05.2016.
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class EvaluationServiceImpl implements EvaluationService {

    private final AnswerService answerService;
    private final QuestionService questionService;

    @Autowired
    public EvaluationServiceImpl(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @Override
    public int computeCorrectnessPercentage(UserAnswerDTO userAnswer) {
        QuestionDTO crtQuestion = questionService.find(userAnswer.getQuestionId());


        List<AnswerDTO> correctAnswers = answerService.getAllCorrectQuestionAnswers(crtQuestion.getId());
        switch (crtQuestion.getQuestionType()) {
            case SINGLE_ANSWER: return evalSingleAnswer(userAnswer, correctAnswers);
            case MULTIPLE_ANSWER: return evalMultipleAnswer(userAnswer, correctAnswers);
            case OPEN_TEXT: return evalOpenTextAnswerScore(userAnswer, crtQuestion.getGoodKeywords(), crtQuestion.getBadKeywords());
            case JAVA_CODE: return evalJavaCodeAnswer(userAnswer);
        }

        return 0;
    }

    private int evalSingleAnswer(UserAnswerDTO userAnswer, List<AnswerDTO> correctAnswers) {
        if (correctAnswers.isEmpty()) {
            return 0;
        }

        long selectedAnswerId = Long.parseLong(userAnswer.getText());
        long correctAnswerId = correctAnswers.get(0).getId();

        return (selectedAnswerId == correctAnswerId)? 100 : 0;
    }

    private int evalMultipleAnswer(UserAnswerDTO userAnswer, List<AnswerDTO> correctAnswers) {

        List<String> selectedIdsStrings = Arrays.asList(userAnswer.getText().split(","));
        if (correctAnswers.isEmpty()) {
            return selectedIdsStrings.isEmpty()? 100 : 0;
        }

        List<Long> selectedAnswersIds = selectedIdsStrings.stream().map(Long::parseLong).collect(Collectors.toList());
        List<Long> correctAnswersIds = correctAnswers.stream().map(AnswerDTO::getId).collect(Collectors.toList());

        Set<Long> selected = new HashSet<>();
        Set<Long> correct = new HashSet<>();
        selected.addAll(selectedAnswersIds);
        correct.addAll(correctAnswersIds);

        if (selected.equals(correct)) {
            return 100;
        }

        int nrCorrectAnswers = correct.size();
        int answerScore = 0;

        for(Long elem : selected) {
            if (correct.contains(elem)) {
                answerScore ++;
            } else {
                answerScore --;
            }
        }

        answerScore = Math.max(answerScore, 0);

        return (answerScore * 100) / nrCorrectAnswers;
    }

    private int evalOpenTextAnswerScore(UserAnswerDTO userAnswer, String goodKeywordsString, String badKeywordsString) {
        // TODO : create configuration file for negations
        List<String> negations = Arrays.asList("no", "not");

        List<String> sentences = Arrays.asList(userAnswer.getText().split("[\\.\\?!;]"));
        List<String> goodKeywords = Arrays.asList(goodKeywordsString.split("\\s*,\\s*"));
        List<String> badKeywords = Arrays.asList(badKeywordsString.split("\\s*,\\s*"));

        int answerScore = 0;

        for (String sentence : sentences) {
            List<String> subSentences = Arrays.asList(sentence.split("\\s*,\\s*"));
            for (String subSentence : subSentences) {
                List<String> words = Arrays.asList(subSentence.split("[ ,]"));
                boolean negationPresent = false;
                for(String word : words) {
                    if (!negationPresent && negations.contains(word)) {
                        negationPresent = true;
                    }
                    if (goodKeywords.contains(word)) {
                        answerScore = (!negationPresent)? answerScore + 1 : answerScore - 1;
                    } else if (badKeywords.contains(word)) {
                        answerScore = (negationPresent)? answerScore + 1 : answerScore - 1;;
                    }
                }
            }
        }

        //TODO: compute percentage
        return answerScore;
    }

    private int evalJavaCodeAnswer(UserAnswerDTO userAnswer) {
        return 0;
    }
}
