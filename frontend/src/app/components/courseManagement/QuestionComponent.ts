import {Component, Input, Output, EventEmitter} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import Course from "../../data/Course";
import Question from "../../data/Question";
import QuestionsService from "../../services/QuestionsService";
import QuestionsResponse from "../../data/QuestionsResponse";
import Topic from "../../data/Topic";
import TopicsService from "../../services/TopicsService";
import TopicsResponse from "../../data/TopicsResponse";
import AnswerComponent from "./AnswerComponent";
import {QuestionType} from "../../data/enums/QuestionType";

@Component({
    selector: 'question-list',
    templateUrl: 'app/components/courseManagement/question-component.html',
    directives: [AnswerComponent],
    pipes: [TranslatePipe]
})
export default class QuestionComponent {
    @Input() private selectedCourse:Course;
    @Output() public onQuestionChangedEvent: EventEmitter<any> = new EventEmitter();

    private allQuestions: Array<Question>;

    private newQuestion:Question;
    private newQuestionDisplayed: boolean;
    private newQuestionType:QuestionType;
    private questionTypes: Array<QuestionType>;
    public isNewQuestion: boolean;

    private selectedQuestion: Question;
    private oldGoodKeywords:string;
    private oldBadKeywords:string;
    private editMode: boolean;

    private assignMode: boolean;
    private allTopics: Array<Topic>;

    constructor(private questionsService:QuestionsService, private topicsService:TopicsService) {
        this.newQuestion = new Question();
        this.newQuestionDisplayed = false;
        this.questionTypes = [QuestionType.SINGLE_ANSWER, QuestionType.MULTIPLE_ANSWER, QuestionType.OPEN_TEXT, QuestionType.JAVA_CODE];
        this.newQuestionType = this.questionTypes[0];
        this.selectedQuestion = null;
        this.editMode = false;
        this.assignMode = false;
    }

    public getQuestions(): Array<Question> {
        if (this.allQuestions == null) {
            setTimeout(() => {
                this.questionsService.getQuestions().subscribe(
                    (searchResult: QuestionsResponse) => this.allQuestions = searchResult.results
                );
            }, 100);
        }
        return this.allQuestions;
    }

    public saveQuestion(question:Question) {
        this.questionsService.saveQuestion(question).subscribe(
            (savedQuestion: Question) => {
                this.selectedQuestion = savedQuestion;
                this.fireQuestionChangedEvent();
            }
        );
    }

    public editQuestion(question:Question, title:HTMLInputElement, requirement: HTMLTextAreaElement) {
        question.title = title.value;
        question.requirement = requirement.value;
        this.saveQuestion(question);

        this.setEditMode(false);
        this.setAssignMode(false);
    }

    public addQuestion() {
        this.newQuestion.questionType = this.newQuestionType;
        this.saveQuestion(this.newQuestion);
        this.isNewQuestion = true;
        this.displayNewQuestion(false);
        this.allQuestions = null;
    }

    public newQuestionTypeSelected(type:QuestionType) {
        this.newQuestionType = type;
    }

    public isOpenText(question:Question): boolean {
        return question.questionType == QuestionType.OPEN_TEXT;
    }

    public removeQuestion(question:Question) {
        this.questionsService.removeQuestion(question).subscribe(
            () => {
                this.fireQuestionChangedEvent();
                this.allQuestions = null;
            },
            error => alert(error._body)
        );
    }

    public displayNewQuestion(val: boolean) {
        this.newQuestionDisplayed = val;
        if (!val) {
            this.newQuestion = new Question();
            this.newQuestionType = this.questionTypes[0];
        }
    }

    public setNoSelectedQuestion() {
        this.selectedQuestion = null;
        this.setEditMode(false);
        this.setAssignMode(false);
    }

    public displayQuestionDetails(question) {
        this.selectedQuestion = question;
        this.isNewQuestion = false;
        this.setEditMode(false);
        this.setAssignMode(false);
    }

    public isSelectedQuestion(question: Question) {
        return this.selectedQuestion != null && this.selectedQuestion.id == question.id;
    }

    public setEditMode(val: boolean){
        this.editMode = val;

        this.oldGoodKeywords = val? this.selectedQuestion.goodKeywords : null;
        this.oldBadKeywords = val? this.selectedQuestion.badKeywords : null;
    }

    public cancelEdit(){
        this.selectedQuestion.goodKeywords = this.oldGoodKeywords;
        this.selectedQuestion.badKeywords = this.oldBadKeywords;
        this.setEditMode(false);
    }

    public setAssignMode(val: boolean) {
        this.assignMode = val;
    }

    public getAllTopics(): Array<Topic> {
        if (this.allTopics == null) {
            setTimeout(() => {
                this.topicsService.getTopics(this.selectedCourse.id).subscribe(
                    (result:TopicsResponse) => this.allTopics = result.results
                );
            }, 100);
        }
        return this.allTopics
    }

    public isTopicQuestionAssigned(topic: Topic): boolean {
        return topic.isQuestionAssigned(this.selectedQuestion);
    }

    public assignTopicQuestion(question: Question, topic: Topic, assign: boolean) {
        if (assign) {
            this.topicsService.assignTopicQuestion(question.id, topic.id).subscribe( () => this.fireQuestionChangedEvent());
        } else {
            this.topicsService.unassignTopicQuestion(question.id, topic.id).subscribe( () => this.fireQuestionChangedEvent());
        }

    }

    public fireQuestionChangedEvent() {
        this.onQuestionChangedEvent.emit(null);
    }

    handleOnTopicChangedEvent(arg: any) {
        this.allTopics = null;
    }
}