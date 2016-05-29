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
public class EvaluationServiceMultipleAnswerTests {
    @Mock
    AnswerService answerService;

    @Mock
    QuestionService questionService;

    @InjectMocks
    EvaluationServiceImpl evaluationService;

    private static QuestionDTO question;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        question = TestUtils.createQuestionMock(QuestionType.MULTIPLE_ANSWER);
        Mockito.when(questionService.find(TestUtils.QUESTION_ID)).thenReturn(question);
    }

    @Test
    public void testNoCorrectAnswers() {

        Mockito.when(answerService.getAllCorrectQuestionAnswers(TestUtils.QUESTION_ID)).thenReturn(new ArrayList<>());

        UserAnswerDTO userAnswer = TestUtils.createUserAnswerMock("1");
        Assert.assertEquals(evaluationService.computeCorrectnessPercentage(userAnswer), 0);
    }

    @Test
    public void testOneCorrectAnswer() {
        AnswerDTO answer = TestUtils.createAnswerMock(1L);

        Mockito.when(answerService.getAllCorrectQuestionAnswers(TestUtils.QUESTION_ID)).thenReturn(Arrays.asList(answer));

        UserAnswerDTO userAnswer = TestUtils.createUserAnswerMock("1");
        Assert.assertEquals(evaluationService.computeCorrectnessPercentage(userAnswer), 100);
    }

    @Test
    public void testMultipleCorrectAnswer() {
        AnswerDTO answer1 = TestUtils.createAnswerMock(1L);
        AnswerDTO answer2 = TestUtils.createAnswerMock(2L);
        AnswerDTO answer3 = TestUtils.createAnswerMock(3L);

        Mockito.when(answerService.getAllCorrectQuestionAnswers(TestUtils.QUESTION_ID)).thenReturn(Arrays.asList(answer1, answer2, answer3));

        UserAnswerDTO userAnswer = TestUtils.createUserAnswerMock("1,3,2");
        Assert.assertEquals(evaluationService.computeCorrectnessPercentage(userAnswer), 100);
    }

    @Test
    public void testNotAllCorrectAnswer() {
        AnswerDTO answer1 = TestUtils.createAnswerMock(1L);
        AnswerDTO answer2 = TestUtils.createAnswerMock(2L);
        AnswerDTO answer3 = TestUtils.createAnswerMock(3L);

        Mockito.when(answerService.getAllCorrectQuestionAnswers(TestUtils.QUESTION_ID)).thenReturn(Arrays.asList(answer1, answer2, answer3));

        UserAnswerDTO userAnswer = TestUtils.createUserAnswerMock("1,2");
        Assert.assertEquals(evaluationService.computeCorrectnessPercentage(userAnswer), 66);
    }

    @Test
    public void testToManyCorrectAnswer() {
        AnswerDTO answer1 = TestUtils.createAnswerMock(1L);
        AnswerDTO answer2 = TestUtils.createAnswerMock(2L);

        Mockito.when(answerService.getAllCorrectQuestionAnswers(TestUtils.QUESTION_ID)).thenReturn(Arrays.asList(answer1, answer2));

        UserAnswerDTO userAnswer = TestUtils.createUserAnswerMock("1,2,3");
        Assert.assertEquals(evaluationService.computeCorrectnessPercentage(userAnswer), 50);
    }


}
