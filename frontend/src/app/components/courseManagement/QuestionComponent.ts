import {Component, Input, Output, EventEmitter} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import QuestionsResponse from "../../data/QuestionsResponse";
import Question from "../../data/Question";
import QuestionsService from "../../services/QuestionsService";
import {Observable} from "rxjs/Observable";
import Course from "../../data/Course";
import AnswerComponent from "./AnswerComponent";
import TopicsService from "../../services/TopicsService";
import TopicsResponse from "../../data/TopicsResponse";
import Topic from "../../data/Topic";
import TopicQuestion from "../../data/TopicQuestion";
import TopicQuestionsResponse from "../../data/TopicQuestionsResponse";

@Component({
    selector: 'question-list',
    templateUrl: 'app/components/courseManagement/question-component.html',
    directives: [AnswerComponent],
    pipes: [TranslatePipe]
})
export default class QuestionComponent {
    @Input() private selectedCourse:Course;
    @Output() public onQuestionChangedEvent: EventEmitter<any> = new EventEmitter();

    private searchResult:Observable<QuestionsResponse>;
    private allQuestions: Array<Question>;

    private newQuestion:Question;
    private newQuestionDisplayed: boolean;

    private selectedQuestion: Question;
    private selectedQuestionTopics: Array<Topic>;
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
        this.setEditMode(false);
        this.setAssignMode(false);
        this.fireQuestionChangedEvent(null);
    }

    public addQuestion(newQuestion: Question) {
        this.saveQuestion(newQuestion);
        this.resetNewQuestion();
        this.allQuestions = null;
        this.fireQuestionChangedEvent(null);
    }

    public removeQuestion(question:Question) {
        this.questionsService.removeQuestion(question).subscribe(() => {});
        this.allQuestions = null;
        this.fireQuestionChangedEvent(null);
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
        this.setAssignMode(false);
    }

    public displayQuestionDetails(question) {
        this.selectedQuestion = question;
        this.setEditMode(false);
        this.setAssignMode(false);
    }

    public setEditMode(val: boolean){
        this.editMode = val;
    }

    public isSelectedQuestion(question: Question) {
        return this.selectedQuestion != null && this.selectedQuestion.id == question.id;
    }

    public setAssignMode(val: boolean) {
        this.assignMode = val;
        if (val) {
            this.queryAllTopics();
           this.querySelectedQuestionTopics();
        } else {
            this.allTopics = null;
            this.selectedQuestionTopics = null;
        }
    }

    private queryAllTopics() {
        let result:Observable<TopicsResponse> = this.topicsService.getTopics(this.selectedCourse.id);
        result.subscribe(
            result => {
                this.allTopics = result.results;
            }
        );
    }

    private querySelectedQuestionTopics() {
        let result:Observable<TopicsResponse> = this.topicsService.getQuestionTopics(this.selectedQuestion.id);
        result.subscribe(
            result => {
                this.selectedQuestionTopics = result.results;
            }
        );
    }

    public getAllTopics(): Array<Topic> {
        if (this.allTopics == null) {
            setTimeout(() => {
                this.queryAllTopics();
            }, 100);
        }
        return this.allTopics;
    }

    public isTopicQuestionAssigned(topic: Topic) {
        if (this.selectedQuestionTopics != null) {
            for (var crtTopic of this.selectedQuestionTopics) {
                if (crtTopic.id == topic.id) {
                    return true;
                }
            }
        }
        return false;
    }

    public assignTopicQuestion(question: Question, topic: Topic, assign: boolean) {
        if (assign) {
            this.topicsService.assignTopicQuestion(question.id, topic.id);
        } else {
            this.topicsService.unassignTopicQuestion(question.id, topic.id);
        }

    }

    public fireQuestionChangedEvent(evt) {
        this.onQuestionChangedEvent.emit(null);
    }
}