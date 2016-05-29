package util;

import com.ifeed.model.dto.AnswerDTO;
import com.ifeed.model.dto.QuestionDTO;
import com.ifeed.model.dto.UserAnswerDTO;
import com.ifeed.model.enums.QuestionType;

/**
 * Created by ipascu on 29.05.2016.
 */
public class TestUtils {

    public static final Long QUESTION_ID = 1L;

    public static QuestionDTO createQuestionMock(QuestionType type) {
        QuestionDTO question = new QuestionDTO();
        question.setId(QUESTION_ID);
        question.setQuestionType(type);

        return question;
    }

    public static UserAnswerDTO createUserAnswerMock(String text) {
        UserAnswerDTO userAnswer = new UserAnswerDTO();
        userAnswer.setQuestionId(QUESTION_ID);
        userAnswer.setText(text);

        return userAnswer;
    }

    public static AnswerDTO createAnswerMock(Long id) {
        AnswerDTO answer = new AnswerDTO();
        answer.setId(id);
        answer.setCorrect(true);
        answer.setQuestionId(QUESTION_ID);

        return answer;
    }

}
