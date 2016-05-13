import {Component, Input} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import QuestionsResponse from "../../data/QuestionsResponse";
import Question from "../../data/Question";
import QuestionsService from "../../services/QuestionsService";
import {Observable} from "rxjs/Observable";
import Course from "../../data/Course";
import AnswerComponent from "./AnswerComponent";

@Component({
    selector: 'question-list',
    templateUrl: 'app/components/courseManagement/question-component.html',
    directives: [AnswerComponent],
    pipes: [TranslatePipe]
})
export default class QuestionComponent {
    @Input() private selectedCourse:Course;

    private searchResult:Observable<QuestionsResponse>;
    private allQuestions: Array<Question>;
    private newQuestion:Question;
    private newQuestionDisplayed: boolean;
    private selectedQuestion: Question;
    private editMode: boolean;

    constructor(private questionsService:QuestionsService) {
        this.newQuestion = new Question();
        this.newQuestionDisplayed = false;
        this.selectedQuestion = null;
        this.editMode = false;
    }

    public getQuestions(): Array<Question> {
        if (this.allQuestions == null) {
            setTimeout(() => {
                this.queryQuestions();
            }, 100);
        }
        return this.allQuestions;
    }

    private queryQuestions() {
        this.searchResult = this.questionsService.getQuestions();
        this.searchResult.subscribe(
            searchResult => {
                this.allQuestions = searchResult.results;
            }
        );
    }

    public saveQuestion(question:Question) {
        let savedQuestion: Observable<Question> =  this.questionsService.saveQuestion(question);
        savedQuestion.subscribe(
            savedQuestion => {
                this.selectedQuestion = savedQuestion;
            }
        );
    }

    public editQuestion(question:Question, title:HTMLInputElement, requirement: HTMLTextAreaElement) {
        question.title = title.value;
        question.requirement = requirement.value;
        this.questionsService.saveQuestion(question).subscribe(() => {});
        this.setEditMode(false)
    }

    public addQuestion(newQuestion: Question) {
        this.saveQuestion(newQuestion);
        this.resetNewQuestion();
        this.allQuestions = null;
    }

    public removeQuestion(question:Question) {
        this.questionsService.removeQuestion(question).subscribe(() => {});
        this.allQuestions = null;
    }

    public displayNewQuestion() {
        this.newQuestionDisplayed = true;
    }

    public resetNewQuestion() {
        this.newQuestionDisplayed = false;
        this.newQuestion = new Question();
    }

    public setNoSelectedQuestion() {
        this.selectedQuestion = null;
        this.setEditMode(false);
    }

    public displayQuestionDetails(question) {
        this.selectedQuestion = question;
        this.setEditMode(false);
    }

    public setEditMode(val: boolean){
        this.editMode = val;
    }

    public isSelectedQuestion(question: Question) {
        return this.selectedQuestion != null && this.selectedQuestion.id == question.id;
    }
}