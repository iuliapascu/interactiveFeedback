import {Component, Input} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import TopicsResponse from "../../data/TopicsResponse";
import Topic from "../../data/Topic";
import TopicsService from "../../services/TopicsService";
import {Observable} from "rxjs/Observable";
import Course from "../../data/Course";

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

    constructor(private topicsService:TopicsService) {
        this.newTopic = new Topic();
        this.newTopicDisplayed = false;
        this.editMode = false;
        this.selectedTopic = null;
    }

    public getTopics(): Array<Topic> {
        if (this.allTopics == null) {
            setTimeout(() => {
                this.queryTopics();
            }, 100);
        }
        return this.allTopics;
    }

    private queryTopics() {
        if (this.selectedCourse != null) {
            this.searchResult = this.topicsService.getTopics(this.selectedCourse.id);
            this.searchResult.subscribe(
                searchResult => {
                    this.allTopics = searchResult.results;
                }
            );
        }
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
        this.topicsService.removeTopic(topic).subscribe(() => {});
        this.allTopics = null;
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
}