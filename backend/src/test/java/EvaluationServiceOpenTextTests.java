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

/**
 * Created by ipascu on 29.05.2016.
 */
public class EvaluationServiceOpenTextTests {

    @Mock
    AnswerService answerService;

    @Mock
    QuestionService questionService;

    @InjectMocks
    EvaluationServiceImpl evaluationService;

    private static QuestionDTO question;
    private static final String GOOD_KEYWORDS = "class, abstract, static";
    private static final String BAD_KEYWORDS = "module, horse";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        question = TestUtils.createQuestionMock(QuestionType.OPEN_TEXT);
        question.setGoodKeywords(GOOD_KEYWORDS);
        question.setBadKeywords(BAD_KEYWORDS);
        Mockito.when(questionService.find(TestUtils.QUESTION_ID)).thenReturn(question);
    }

    @Test
    public void testOnlyGoodWords() {

        Mockito.when(answerService.getAllCorrectQuestionAnswers(TestUtils.QUESTION_ID)).thenReturn(new ArrayList<>());

        UserAnswerDTO userAnswer = TestUtils.createUserAnswerMock("This is a class. It can be abstract, or it can be static!");
        Assert.assertEquals(evaluationService.computeCorrectnessPercentage(userAnswer), 3);
    }

    @Test
    public void testGoodAndBadWords() {

        Mockito.when(answerService.getAllCorrectQuestionAnswers(TestUtils.QUESTION_ID)).thenReturn(new ArrayList<>());

        UserAnswerDTO userAnswer1 = TestUtils.createUserAnswerMock("This is an abstract class on a horse!");
        Assert.assertEquals(evaluationService.computeCorrectnessPercentage(userAnswer1), 1);

        UserAnswerDTO userAnswer2 = TestUtils.createUserAnswerMock("The module is riding a horse!");
        Assert.assertEquals(evaluationService.computeCorrectnessPercentage(userAnswer2), -2);
    }

    @Test
    public void testWithNegations() {

        Mockito.when(answerService.getAllCorrectQuestionAnswers(TestUtils.QUESTION_ID)).thenReturn(new ArrayList<>());

        UserAnswerDTO userAnswer1 = TestUtils.createUserAnswerMock("This is a class, not a module");
        Assert.assertEquals(evaluationService.computeCorrectnessPercentage(userAnswer1), 2);

        UserAnswerDTO userAnswer2 = TestUtils.createUserAnswerMock("This is not a class. This is an abstract horse;");
        Assert.assertEquals(evaluationService.computeCorrectnessPercentage(userAnswer2), -1);
    }

}
