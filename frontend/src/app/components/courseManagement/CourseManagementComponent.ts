import {Component, Input} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import Course from "../../data/Course";
import QuestionComponent from "./QuestionComponent";
import CourseListComponent from "./CourseListComponent";
import TopicComponent from "./TopicComponent";

@Component({
    selector: 'course-management',
    templateUrl: 'app/components/courseManagement/course-management-component.html',
    directives: [CourseListComponent, TopicComponent, QuestionComponent],
    pipes: [TranslatePipe]
})
export default class CourseManagementComponent {
    private selectedCourse:Course;
    private courseSelected: boolean;

    constructor() {
        this.courseSelected = false;
    }

    handleOnSelectedCourseEvent(course: Course) {
        this.courseSelected = true;
        this.selectedCourse = course;
    }
}