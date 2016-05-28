import {Component, Input, Output, EventEmitter} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import Course from "../../data/Course";
import Topic from "../../data/Topic";
import TopicsService from "../../services/TopicsService";
import TopicsResponse from "../../data/TopicsResponse";
import Question from "../../data/Question";
import QuestionsService from "../../services/QuestionsService";
import QuestionsResponse from "../../data/QuestionsResponse";

@Component({
    selector: 'topic-list',
    templateUrl: 'app/components/courseManagement/topic-component.html',
    directives: [],
    pipes: [TranslatePipe]
})
export default class TopicComponent {
    @Input() private selectedCourse:Course;
    @Output() public onTopicChangedEvent: EventEmitter<any> = new EventEmitter();

    private allTopics: Array<Topic>;
    private selectedTopic: Topic;
    private newTopic:Topic;
    private newTopicDisplayed: boolean;

    private editMode: boolean;
    private assignMode: boolean;

    private allQuestions: Array<Question>;
    private unassignedQuestions: Array<Question>;

    private searchTerm: string = '';
    private questionSearchTerm: string = '';

    constructor(private topicsService:TopicsService, private questionsService: QuestionsService) {
        this.newTopic = new Topic();
    }

    public getTopics(): Array<Topic> {
        if (this.allTopics == null) {
            setTimeout(() => {
                this.topicsService.getTopics(this.selectedCourse.id).subscribe(
                    (searchResult: TopicsResponse) => this.allTopics = searchResult.results
                );
            }, 100);
        }
        return this.filterResults(this.allTopics, this.searchTerm);
    }

    public filterResults(itemList: Array<any>, term: string) {
        return (itemList != null)? itemList.filter((item) => item.titleMatchesFilterString(term)) : itemList;
    }

    public saveTopic(topic:Topic) {
        this.topicsService.saveTopic(topic).subscribe(() => this.fireTopicChangedEvent());
    }

    public editTopic(topic:Topic, title:HTMLInputElement) {
        topic.title = title.value;
        this.saveTopic(topic);
        this.setEditMode(false, topic)
    }

    public addTopic(newTopic: Topic) {
        newTopic.courseId = this.selectedCourse.id;
        this.saveTopic(newTopic);
        this.displayNewTopic(false);
    }

    public removeTopic(topic:Topic) {
        this.topicsService.removeTopic(topic).subscribe(
            () => this.fireTopicChangedEvent(),
            error => alert(error._body)
        );
    }

    public displayNewTopic(val:boolean) {
        this.newTopicDisplayed = val;
        if (!val) {
            this.newTopic = new Topic();
        }
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

    public getSelectedTopicUnassignedQuestions(): Array<Question> {
        if (this.unassignedQuestions == null) {
            if (this.allQuestions == null) {
                setTimeout(() => {
                    this.questionsService.getQuestions().subscribe(
                        (searchResult: QuestionsResponse) => this.allQuestions = searchResult.results
                    );
                }, 100);
            } else {
                this.unassignedQuestions = [];
                this.allQuestions.forEach( question => {
                    if (!this.selectedTopic.isQuestionAssigned(question)) {
                        this.unassignedQuestions.push(question);
                    }
                });
            }
        }
        return this.filterResults(this.unassignedQuestions, this.questionSearchTerm);
    }

    public unassignQuestion(question: Question, topic: Topic) {
        this.topicsService.unassignTopicQuestion(question.id, topic.id).subscribe(
            () => this.fireTopicChangedEvent()
        );
    }

    public assignQuestion(question: Question, topic: Topic) {
        this.topicsService.assignTopicQuestion(question.id, topic.id).subscribe(
            () => this.fireTopicChangedEvent()
        );
    }

    public fireTopicChangedEvent() {
        this.allTopics = null;
        this.onTopicChangedEvent.emit(null);
    }

    handleOnQuestionChangedEvent(arg: any) {
        this.allTopics = null;
        this.allQuestions = null;
    }
}