import {Component, Input} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import AnswersResponse from "../../data/AnswersResponse";
import Question from "../../data/Question";
import Answer from "../../data/Answer";
import AnswersService from "../../services/AnswersService";

@Component({
    selector: 'answer-list',
    templateUrl: 'app/components/courseManagement/answer-component.html',
    directives: [],
    pipes: [TranslatePipe]
})
export default class AnswerComponent {
    @Input() private selectedQuestion:Question;
    @Input() private isNewQuestion:boolean;

    private questionAnswers: Array<Answer>;
    private selectedAnswer: Answer;
    private newAnswer:Answer;
    private newAnswerDisplayed: boolean;

    constructor(private answersService:AnswersService) {
        this.newAnswer = new Answer();
        this.newAnswerDisplayed = false;
        this.selectedAnswer = null;
    }

    public getAnswers(): Array<Answer> {
        if (this.questionAnswers == null) {
            if (this.isNewQuestion) {
                this.questionAnswers = [];
                this.isNewQuestion = false;
            } else {
                setTimeout(() => {
                    this.answersService.getAnswers(this.selectedQuestion.id).subscribe(
                        (searchResult: AnswersResponse) => this.questionAnswers = searchResult.results
                    );
                }, 100);
            }
        }
        return this.questionAnswers;
    }

    public saveAnswer(answer:Answer): any {
        return this.answersService.saveAnswer(answer).subscribe(() => {});
    }

    public editAnswer(answer:Answer) {
        answer.text = this.selectedAnswer.text;
        answer.isCorrect = this.selectedAnswer.isCorrect;
        this.saveAnswer(answer);

        this.setEditMode(false, answer);
    }

    public addAnswer() {
        this.newAnswer.questionId = this.selectedQuestion.id;
        this.saveAnswer(this.newAnswer);
        this.displayNewAnswer(false);
        this.questionAnswers = null;
    }

    public removeAnswer(answer:Answer) {
        this.answersService.removeAnswer(answer).subscribe(() => {});
        this.questionAnswers = null;
    }

    public displayNewAnswer(val: boolean) {
        this.newAnswerDisplayed = val;
        if (!val) {
            this.newAnswer = new Answer();
        }
    }

    public setEditMode(editMode: boolean, answer: Answer){
        this.selectedAnswer = editMode? answer : null;
    }

    public isSelectedAnswer(answer: Answer) {
        return this.selectedAnswer != null && this.selectedAnswer.id == answer.id;
    }
}