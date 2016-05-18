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


@Component({
    selector: 'create-event',
    templateUrl: 'app/components/events/create-event-component.html',
    directives: [],
    pipes: [TranslatePipe]
})
export default class CreateEventComponent {
    @Input() private selectedCourse:Course;
    @Output() public onEventCreatedEvent: EventEmitter<any> = new EventEmitter();

    private allTopics: Array<Topic>;
    private selectedQuestions: Array<Question>;
    //TODO: use a datetimepicker
    private SELECTED_DATETIME: string = "17/05/16 12:00:00 +00:00";
    //TODO: use a given name
    private NAME: string = "Demo Event";
    private createdEvent: CourseEvent;

    constructor(private questionsService:QuestionsService, private topicsService:TopicsService) {
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
            this.createdEvent.questions = this.selectedQuestions;
            this.createdEvent.date = this.SELECTED_DATETIME;
            this.createdEvent.name = this.NAME;

            let questionStates: Map<number, number> = new Map<number, number>();
            this.selectedQuestions.forEach( crt => {
                questionStates.set(crt.id, 0);
            });

            this.createdEvent.questionStates = questionStates;

            this.fireEventCreatedEvent(this.createdEvent)
        }
        //TODO: else - alert error msg + check other fields

    }

    public fireEventCreatedEvent(evt: CourseEvent) {
        this.onEventCreatedEvent.emit(evt);
    }

}