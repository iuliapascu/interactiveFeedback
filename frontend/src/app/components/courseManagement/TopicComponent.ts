import {Component, Input} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import Course from "../../data/Course";

@Component({
    selector: 'topic-list',
    templateUrl: 'app/components/courseManagement/topic-component.html',
    directives: [],
    pipes: [TranslatePipe]
})
export default class TopicComponent {
    @Input() private selectedCourse:Course;

    constructor() {
    }
}