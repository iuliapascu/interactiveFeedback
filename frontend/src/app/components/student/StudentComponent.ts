import {Component} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import Question from "../../data/Question";
import UserAnswer from "../../data/UserAnswer";
import {QuestionType} from "../../data/enums/QuestionType";
import {Input} from "angular2/core";
import Answer from "../../data/Answer";
import AnswersService from "../../services/AnswersService";
import AnswersResponse from "../../data/AnswersResponse";
import QuestionsService from "../../services/QuestionsService";
import UserAnswersService from "../../services/UserAnswersService";

@Component({
    selector: 'student',
    templateUrl: 'app/components/student/student-component.html',
    directives: [],
    pipes: [TranslatePipe]
})
export default class StudentComponent{
    @Input() private currentQuestionId: number;
    @Input() private currentEventId: number;
    private currentQuestion: Question;
    private questionAnswers: Array<Answer>;
    private myAnswer: UserAnswer;

    private textAnswer: string;
    private selectedAnswers: Array<number> = [];

    //TODO: temporary - just for testing
    private answerSubmitted: boolean = false;
    private submittedAnswer: UserAnswer;
    //----

    constructor(private questionsService: QuestionsService, private answersService: AnswersService, private userAnswersService: UserAnswersService) {
        this.myAnswer = new UserAnswer();
        this.textAnswer = '';
        this.queryCurrentQuestion();
        this.queryAnswers();
    }

    queryCurrentQuestion() {
        setTimeout(() => {
            this.questionsService.getQuestion(this.currentQuestionId).subscribe(
                (question: Question) => this.currentQuestion = question
            );
        }, 100);
    }

    queryAnswers() {
        setTimeout(() => {
            this.answersService.getAnswers(this.currentQuestionId).subscribe(
                (searchResult: AnswersResponse) => this.questionAnswers = searchResult.results
            );
        }, 100);
    }

    getQuestionTitle(): string {
        return (this.currentQuestion != null)? this.currentQuestion.title : '';
    }

    getQuestionRequirement(): string {
        return (this.currentQuestion != null)? this.currentQuestion.requirement : '';
    }

    getQuestionType(): number {
        if (this.currentQuestion!= null) {
            switch (this.currentQuestion.questionType) {
                case QuestionType.SINGLE_ANSWER: return 0;
                case QuestionType.MULTIPLE_ANSWER: return 1;
                case QuestionType.OPEN_TEXT: return 2;
                case QuestionType.JAVA_CODE: return 3;
            }
        }
        return -1;
    }

    getQuestionAnswers(): Array<Answer> {
        return (this.questionAnswers != null)? this.questionAnswers : [];
    }

    selectSingleAnswer(answer: Answer) {
        this.textAnswer = answer.id.toString();
    }

    selectMultipleAnswer(answer: Answer, selected: boolean) {
        if (selected) {
            this.selectedAnswers.push(answer.id);
        } else {
            var index = this.selectedAnswers.indexOf(answer.id, 0);
            if (index > -1) {
                this.selectedAnswers.splice(index, 1);
            }
        }
    }

    submitAnswer() {
        if (this.getQuestionType() == 1) {
            this.textAnswer = this.getAnswersString();
        }
        this.myAnswer.text = this.textAnswer;
        this.myAnswer.eventId = this.currentEventId;
        this.myAnswer.questionId = this.currentQuestionId;

        this.userAnswersService.saveUserAnswer(this.myAnswer).subscribe(
            (userAnswer: UserAnswer) => {
                this.submittedAnswer = userAnswer;
                this.answerSubmitted = true;
            });
    }

    private getAnswersString() {
        var answersText = '';
        if (this.selectedAnswers != null) {
            for (var id of this.selectedAnswers) {
                answersText += id + ",";
            }
        }
        return answersText;
    }

}