import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by ipascu on 29.05.2016.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EvaluationServiceSingleAnswerTests.class,
        EvaluationServiceMultipleAnswerTests.class,
        EvaluationServiceOpenTextTests.class})
public class InteractiveFeedbackSuiteClass {
}

