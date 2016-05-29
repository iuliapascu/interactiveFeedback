import {Component, Input} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import CourseEventsService from "../../services/CourseEventsService";
import Course from "../../data/Course";
import CourseEvent from "../../data/CourseEvent";
import ParticipateInEventComponent from "./ParticipateInEventComponent";
import CourseListComponent from "../courseManagement/CourseListComponent";
import CourseEventsResponse from "../../data/CourseEventsResponse";
import CourseEventQuestion from "../../data/CourseEventQuestion";
import CourseEventQuestionResponse from "../../data/CourseEventQuestionResponse";

@Component({
    selector: 'events',
    templateUrl: 'app/components/events/events-component.html',
    directives: [CourseListComponent, ParticipateInEventComponent],
    pipes: [TranslatePipe]
})
export default class EventsComponent {
    @Input() private createdEvent:CourseEvent;
    private selectedCourse:Course;
    private courseSelected: boolean;

    private selectedCourseEvent: CourseEvent;
    private eventSelected: boolean;

    private allCourseEvents: Array<CourseEvent>;
    private searchTerm: string = '';

    constructor(private courseEventsService: CourseEventsService) {
        this.courseSelected = false;
        this.eventSelected = false;
    }

    public getCourseEvents(): Array<CourseEvent> {
        if (this.allCourseEvents == null) {
            this.queryCourseEvents();
        }
        return this.filterResults(this.allCourseEvents, this.searchTerm);
    }

    public filterResults(itemList: Array<any>, term: string) {
        return (itemList != null)? itemList.filter((item) => item.matchesFilterString(term)) : itemList;
    }

    public selectCourseEvent(courseEvent: CourseEvent) {
        if (courseEvent != null) {
            this.courseEventsService.getCourseEventQuestions(courseEvent.id).subscribe(
                (searchResponse:CourseEventQuestionResponse) => {
                    courseEvent.questions = searchResponse.results;
                    this.selectedCourseEvent = courseEvent;
                    this.eventSelected = true;
                }
            );
        } else {
            this.eventSelected = false;
        }
    }

    public handleOnSelectedCourse(course: Course) {
        this.selectedCourse = course;
        if (course != null) {
            this.queryCourseEvents();
        }
        this.courseSelected = (course != null);
    }

    public duplicateEvent(event: CourseEvent) {
        this.courseEventsService.duplicateCourseEvent(event).subscribe(() => this.queryCourseEvents());
    }

    public removeEvent(event: CourseEvent) {
        this.courseEventsService.removeCourseEvent(event).subscribe(() => this.queryCourseEvents());
    }

    private queryCourseEvents(){
        setTimeout(() => {
            this.courseEventsService.getCourseEvents(this.selectedCourse.id).subscribe(
                (searchResult: CourseEventsResponse) => this.allCourseEvents = searchResult.results
            );
        }, 100);
    }

}