import com.ifeed.model.dto.AnswerDTO;
import com.ifeed.model.dto.QuestionDTO;
import com.ifeed.model.dto.UserAnswerDTO;
import com.ifeed.model.enums.QuestionType;
import com.ifeed.service.AnswerService;
import com.ifeed.service.QuestionService;
import com.ifeed.service.evaluation.EvaluationServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import util.TestUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ipascu on 29.05.2016.
 */
public class EvaluationServiceSingleAnswerTests {
    @Mock
    AnswerService answerService;

    @Mock
    QuestionService questionService;

    @InjectMocks
    EvaluationServiceImpl evaluationService;

    private static QuestionDTO question;
    private static AnswerDTO correctAnswer;
    private static final Long CORRECT_ANSWER_ID = 11L;
    private static final Long INCORRECT_ANSWER = 12L;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        question = TestUtils.createQuestionMock(QuestionType.SINGLE_ANSWER);
        correctAnswer = TestUtils.createAnswerMock(CORRECT_ANSWER_ID);
        Mockito.when(questionService.find(TestUtils.QUESTION_ID)).thenReturn(question);
    }

    @Test
    public void testNoCorrectAnswers() {

        Mockito.when(answerService.getAllCorrectQuestionAnswers(TestUtils.QUESTION_ID)).thenReturn(new ArrayList<>());

        UserAnswerDTO userAnswer = TestUtils.createUserAnswerMock(INCORRECT_ANSWER.toString());
        Assert.assertEquals(evaluationService.computeCorrectnessPercentage(userAnswer), 0);
    }

    @Test
    public void testCorrectAnswer() {

        Mockito.when(answerService.getAllCorrectQuestionAnswers(TestUtils.QUESTION_ID)).thenReturn(Arrays.asList(correctAnswer));

        UserAnswerDTO userAnswer = TestUtils.createUserAnswerMock(CORRECT_ANSWER_ID.toString());
        Assert.assertEquals(evaluationService.computeCorrectnessPercentage(userAnswer), 100);
    }

    @Test
    public void testIncorrectAnswer() {

        Mockito.when(answerService.getAllCorrectQuestionAnswers(TestUtils.QUESTION_ID)).thenReturn(Arrays.asList(correctAnswer));

        UserAnswerDTO userAnswer = TestUtils.createUserAnswerMock(INCORRECT_ANSWER.toString());
        Assert.assertEquals(evaluationService.computeCorrectnessPercentage(userAnswer), 0);
    }

}