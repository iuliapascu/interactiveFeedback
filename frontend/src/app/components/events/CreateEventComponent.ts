import {Component, Input, Output, EventEmitter} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import Course from "../../data/Course";
import TopicsService from "../../services/TopicsService";
import QuestionsService from "../../services/QuestionsService";
import Topic from "../../data/Topic";
import Question from "../../data/Question";
import CourseEvent from "../../data/CourseEvent";
import {Observable} from "rxjs/Observable";
import TopicsResponse from "../../data/TopicsResponse";
import CourseEventsService from "../../services/CourseEventsService";


@Component({
    selector: 'create-event',
    templateUrl: 'app/components/events/create-event-component.html',
    directives: [],
    pipes: [TranslatePipe]
})
export default class CreateEventComponent {
    @Input() private selectedCourse:Course;
    @Output() public onEventCreatedEvent: EventEmitter<any> = new EventEmitter();
    @Output() public onEventCanceledEvent: EventEmitter<any> = new EventEmitter();

    private allTopics: Array<Topic>;
    private selectedQuestions: Array<Question>;
    private eventDate: string = "17/05/16";
    private eventName: string;
    private createdEvent: CourseEvent;

    constructor(private courseEventsService:CourseEventsService, private topicsService:TopicsService) {
        this.allTopics = null;
        this.selectedQuestions = [];
        this.createdEvent = new CourseEvent();
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
        let searchResult: Observable<TopicsResponse> = this.topicsService.getTopics(this.selectedCourse.id);
        searchResult.subscribe(
            searchResult => {
                this.allTopics = searchResult.results;
            }
        );
    }

    public selectQuestion(question: Question) {
        if (!this.isSelectedQuestion(question)) {
            this.selectedQuestions.push(question);
        }
    }

    public removeQuestion(question: Question) {
        var index = this.selectedQuestions.indexOf(question, 0);
        if (index > -1) {
            this.selectedQuestions.splice(index, 1);
        }
    }

    public isSelectedQuestion(question: Question) {
        let result = false;
        this.selectedQuestions.forEach( crt => {
            if (crt.id == question.id) {
                result = true;
            }
        });
        return result;
    }

    public createEvent() {
        if (this.selectedQuestions != null && this.selectedQuestions.length > 0) {
            this.createdEvent.date = this.eventDate;
            this.createdEvent.name = this.eventName;
            this.createdEvent.courseId = this.selectedCourse.id;

            this.courseEventsService.saveCourseEvent(this.createdEvent, this.selectedQuestions).subscribe(
                (evt:CourseEvent) => this.createdEvent.id = evt.id
            );

            this.fireEventCreatedEvent(this.createdEvent)
        } else {
            alert("Please select at least one question!");
        }

    }

    public cancelEvent() {
        this.fireEventCanceled();
        this.createdEvent = null;
        this.selectedQuestions = null;
        this.allTopics = null;
    }

    public fireEventCreatedEvent(evt: CourseEvent) {
        this.onEventCreatedEvent.emit(evt);
    }

    public fireEventCanceled() {
        this.onEventCanceledEvent.emit(null);
    }

}