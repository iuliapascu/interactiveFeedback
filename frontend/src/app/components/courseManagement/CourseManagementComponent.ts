import {Component, Input, Output, EventEmitter} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import Course from "../../data/Course";
import QuestionComponent from "./QuestionComponent";
import CourseListComponent from "./CourseListComponent";
import TopicComponent from "./TopicComponent";
import CreateEventComponent from "../events/CreateEventComponent";
import CourseEvent from "../../data/CourseEvent";

@Component({
    selector: 'course-management',
    templateUrl: 'app/components/courseManagement/course-management-component.html',
    directives: [CourseListComponent, CreateEventComponent, TopicComponent, QuestionComponent],
    pipes: [TranslatePipe]
})
export default class CourseManagementComponent {
    @Output() public onEventCreatedEvent: EventEmitter<any> = new EventEmitter();

    private selectedCourse:Course;
    private courseSelected: boolean;
    private createEventMode: boolean;

    constructor() {
        this.courseSelected = false;
    }

    public setCreateEventMode(val: boolean) {
        this.createEventMode = val;
    }

    handleOnSelectedCourseEvent(course: Course) {
        this.courseSelected = true;
        this.selectedCourse = course;
    }

    handleOnEventCreatedEvent(event: CourseEvent) {
        this.createEventMode = false;
        this.onEventCreatedEvent.emit(event);
    }
}