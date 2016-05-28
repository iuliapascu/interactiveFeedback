import {Component, Input, Output, EventEmitter} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import CoursesService from "../../services/CoursesService";
import {Observable} from "rxjs/Observable";
import CoursesResponse from "../../data/CoursesResponse";
import Course from "../../data/Course";

@Component({
    selector: 'course-list',
    templateUrl: 'app/components/courseManagement/course-list-component.html',
    directives: [],
    pipes: [TranslatePipe]
})
export default class CourseListComponent {
    @Input() private selectedCourse:Course;
    @Output() public onCourseSelectedEvent: EventEmitter<any> = new EventEmitter();

    private searchResult:Observable<CoursesResponse>;
    private allCourses: Array<Course>;
    private searchTerm: string = '';

    constructor(private coursesService:CoursesService) {
    }

    public getCourses(): Array<Course> {
        if (this.allCourses == null) {
            this.queryCourses();
        }
        return this.filterResults(this.allCourses, this.searchTerm);
    }

    public filterResults(itemList: Array<any>, term: string) {
        return (itemList != null)? itemList.filter((item) => item.matchesFilterString(term)) : itemList;
    }

    public selectCourse(course: Course) {
        this.selectedCourse = course;
        this.fireCourseSelectedEvent(course);
    }

    private queryCourses() {
        this.searchResult = this.coursesService.getCourses();
        this.searchResult.subscribe(
            searchResult => {
                this.allCourses = searchResult.results;
            }
        );
    }

    public fireCourseSelectedEvent(evt) {
        this.onCourseSelectedEvent.emit(evt);
    }
}