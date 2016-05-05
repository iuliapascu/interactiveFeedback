import {Component, Input} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import AnswersResponse from "../../data/AnswersResponse";
import {Observable} from "rxjs/Observable";
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

    private searchResult:Observable<AnswersResponse>;
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
            setTimeout(() => {
                this.queryAnswers();
            }, 100);
        }
        return this.questionAnswers;
    }

    private queryAnswers() {
        this.searchResult = this.answersService.getAnswers(this.selectedQuestion.id);
        this.searchResult.subscribe(
            searchResult => {
                this.questionAnswers = searchResult.results;
            }
        );
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

        this.questionAnswers.push(this.saveAnswer(this.newAnswer));
        this.resetNewAnswer();
    }

    public removeAnswer(answer:Answer) {
        this.answersService.removeAnswer(answer).subscribe(() => {});
        this.questionAnswers = null;
    }

    public displayNewAnswer() {
        this.newAnswerDisplayed = true;
    }

    public resetNewAnswer() {
        this.questionAnswers = null;
        this.newAnswerDisplayed = false;
        this.newAnswer = new Answer();
    }

    public setEditMode(editMode: boolean, answer: Answer){
        if (!editMode) {
            this.selectedAnswer = null;
        } else {
            this.selectedAnswer = new Answer();
            this.selectedAnswer.id = answer.id;
            this.selectedAnswer.text = answer.text;
            this.selectedAnswer.isCorrect = answer.isCorrect;
        }
    }

    public isSelectedAnswer(answer: Answer) {
        return this.selectedAnswer != null && this.selectedAnswer.id == answer.id;
    }
}