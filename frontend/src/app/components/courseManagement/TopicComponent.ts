import {Component, Input} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import TopicsResponse from "../../data/TopicsResponse";
import Topic from "../../data/Topic";
import TopicsService from "../../services/TopicsService";
import {Observable} from "rxjs/Observable";
import Course from "../../data/Course";
import Question from "../../data/Question";
import QuestionsResponse from "../../data/QuestionsResponse";
import QuestionsService from "../../services/QuestionsService";

@Component({
    selector: 'topic-list',
    templateUrl: 'app/components/courseManagement/topic-component.html',
    directives: [],
    pipes: [TranslatePipe]
})
export default class TopicComponent {
    @Input() private selectedCourse:Course;

    private searchResult:Observable<TopicsResponse>;
    private allTopics: Array<Topic>;
    private newTopic:Topic;
    private newTopicDisplayed: boolean;
    private editMode: boolean;
    private selectedTopic: Topic;
    private assignMode: boolean;
    private allQuestions: Array<Question>;
    private unassignedQuestions: Array<Question>;

    constructor(private topicsService:TopicsService, private questionsService: QuestionsService) {
        this.newTopic = new Topic();
        this.newTopicDisplayed = false;
        this.editMode = false;
        this.selectedTopic = null;
    }

    public getTopics(): Array<Topic> {
        if (this.allTopics == null) {
            setTimeout(() => {
                this.queryTopics();
                this.queryQuestions();
            }, 100);
        }
        return this.allTopics;
    }

    private queryTopics() {
        this.searchResult = this.topicsService.getTopics(this.selectedCourse.id);
        this.searchResult.subscribe(
            searchResult => {
                this.allTopics = searchResult.results;
            }
        );
    }

    private queryQuestions() {
        let searchResult: Observable<QuestionsResponse> = this.questionsService.getQuestions();
        searchResult.subscribe(
            searchResult => {
                this.allQuestions = searchResult.results;
            }
        );
    }

    public saveTopic(topic:Topic) {
        this.topicsService.saveTopic(topic).subscribe(() => {});
    }

    public editTopic(topic:Topic, title:HTMLInputElement) {
        topic.title = title.value;
        this.topicsService.saveTopic(topic).subscribe(() => {});
        this.setEditMode(false, topic)
    }

    public addTopic(newTopic: Topic) {
        newTopic.courseId = this.selectedCourse.id;
        this.saveTopic(newTopic);
        this.resetNewTopic();
        this.allTopics = null;
    }

    public removeTopic(topic:Topic) {
        this.topicsService.removeTopic(topic).subscribe(
            () => this.allTopics = null,
            error => alert(error._body)
        );
    }

    public displayNewTopic() {
        this.newTopicDisplayed = true;
    }

    public resetNewTopic() {
        this.newTopicDisplayed = false;
        this.newTopic = new Topic();
    }

    public setEditMode(val: boolean, topic: Topic){
        this.editMode = val;
        this.selectedTopic = val? topic : null;
    }

    public setAssignMode(val: boolean, topic: Topic){
        this.selectedTopic = val? topic : null;
        this.unassignedQuestions = null;
        this.assignMode = val;
    }

    public getSelectedTopicUnassignedQuestions() {
        if (this.unassignedQuestions == null && this.selectedTopic != null) {
            this.unassignedQuestions = this.getUnassignedQuestions(this.selectedTopic);
        }
        return this.unassignedQuestions;
    }

    public getUnassignedQuestions(topic: Topic) {
        let unassignedTopicQuestions: Array<Question> = [];
        this.allQuestions.forEach( question => {
            if (!topic.isQuestionAssigned(question)) {
                unassignedTopicQuestions.push(question);
            }
        });
        return unassignedTopicQuestions;
    }

    public unassignQuestion(question: Question, topic: Topic) {
        this.topicsService.unassignTopicQuestion(question.id, topic.id);
    }

    public assignQuestion(question: Question, topic: Topic) {
        this.topicsService.assignTopicQuestion(question.id, topic.id);
    }

    handleOnQuestionChangedEvent(arg) {
        this.allTopics = null;
    }
}