import {Component} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import CoursesService from "../../services/CoursesService";
import {Observable} from "rxjs/Observable";
import CoursesResponse from "../../data/CoursesResponse";
import Course from "../../data/Course";

@Component({
    selector: 'course-admin',
    templateUrl: 'app/components/administration/course-admin-component.html',
    directives: [],
    pipes: [TranslatePipe]
})
export default class CourseAdminComponent {
    private searchResult:Observable<CoursesResponse>;
    private allCourses: Array<Course>;
    private newCourse:Course;
    private newCourseDisplayed: boolean;

    constructor(private coursesService:CoursesService) {
        this.newCourse = new Course();
        this.newCourseDisplayed = false;
    }

    public getCourses(): Array<Course> {
        if (this.allCourses == null) {
            this.queryCourses();
        }
        return this.allCourses;
    }

    private queryCourses() {
        this.searchResult = this.coursesService.getCourses();
        this.searchResult.subscribe(
            searchResult => {
                this.allCourses = searchResult.results;
            }
        );
    }

    public saveCourse(course:Course) {
        this.coursesService.saveCourse(course).subscribe(() => {});
    }

    public addCourse(newCourse: Course) {
        this.saveCourse(newCourse);
        this.resetNewCourse();
        this.allCourses = null;
    }

    public removeCourse(course:Course) {
        this.coursesService.removeCourse(course).subscribe(() => {});
        this.allCourses = null;
    }

    public displayNewCourse() {
        this.newCourseDisplayed = true;
    }

    public resetNewCourse() {
        this.newCourseDisplayed = false;
        this.newCourse = new Course();
    }
}