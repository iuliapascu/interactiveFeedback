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

    private selectedQuestion: Question;
    private editMode: boolean;

    private assignMode: boolean;
    private allTopics: Array<Topic>;

    constructor(private questionsService:QuestionsService, private topicsService:TopicsService) {
        this.newQuestion = new Question();
        this.newQuestionDisplayed = false;
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

    public addQuestion(newQuestion: Question) {
        this.saveQuestion(newQuestion);
        this.displayNewQuestion(false);
        this.allQuestions = null;
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
        }
    }

    public setNoSelectedQuestion() {
        this.selectedQuestion = null;
        this.setEditMode(false);
        this.setAssignMode(false);
    }

    public displayQuestionDetails(question) {
        this.selectedQuestion = question;
        this.setEditMode(false);
        this.setAssignMode(false);
    }

    public isSelectedQuestion(question: Question) {
        return this.selectedQuestion != null && this.selectedQuestion.id == question.id;
    }

    public setEditMode(val: boolean){
        this.editMode = val;
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