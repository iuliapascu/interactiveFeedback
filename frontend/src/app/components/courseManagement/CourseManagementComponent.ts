import {Component, Input} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import Course from "../../data/Course";
import QuestionComponent from "./QuestionComponent";
import CourseListComponent from "./CourseListComponent";
import TopicComponent from "./TopicComponent";
import CreateEventComponent from "../events/CreateEventComponent";
import ParticipateInEventComponent from "../events/ParticipateInEventComponent";
import CourseEvent from "../../data/CourseEvent";

@Component({
    selector: 'course-management',
    templateUrl: 'app/components/courseManagement/course-management-component.html',
    directives: [CourseListComponent, CreateEventComponent, ParticipateInEventComponent, TopicComponent, QuestionComponent],
    pipes: [TranslatePipe]
})
export default class CourseManagementComponent {
    private selectedCourse:Course;
    private courseSelected: boolean;
    private createEventMode: boolean;
    private participateInEventMode: boolean;
    private selectedEvent: CourseEvent;

    constructor() {
        this.courseSelected = false;
    }

    handleOnSelectedCourseEvent(course: Course) {
        this.courseSelected = true;
        this.selectedCourse = course;
    }

    public setCreateEventMode(val: boolean) {
        this.createEventMode = val;
    }

    handleOnEventCreatedEvent(event: CourseEvent) {
        this.createEventMode = false;
        this.participateInEventMode = true;
        this.selectedEvent = event;
    }
}