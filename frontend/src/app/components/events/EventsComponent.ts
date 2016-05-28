import {Component} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import CourseEventsService from "../../services/CourseEventsService";
import Course from "../../data/Course";
import CourseEvent from "../../data/CourseEvent";
import ParticipateInEventComponent from "./ParticipateInEventComponent";
import CourseListComponent from "../courseManagement/CourseListComponent";
import {Observable} from "rxjs/Observable";
import CourseEventsResponse from "../../data/CourseEventsResponse";

@Component({
    selector: 'events',
    templateUrl: 'app/components/events/events-component.html',
    directives: [CourseListComponent, ParticipateInEventComponent],
    pipes: [TranslatePipe]
})
export default class EventsComponent {
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
        this.selectedCourseEvent = courseEvent;
        this.eventSelected = (courseEvent != null);
    }

    public handleOnSelectedCourseEvent(course: Course) {
        this.selectedCourse = course;
        if (course != null) {
            this.queryCourseEvents();
        }
        this.courseSelected = (course != null);
    }

    private queryCourseEvents(){
        setTimeout(() => {
            let searchResult: Observable<CourseEventsResponse> = this.courseEventsService.getCourseEvents(this.selectedCourse.id);
            searchResult.subscribe(
                searchResult => {
                    this.allCourseEvents = searchResult.results;
                }
            );
        }, 100);
    }

}