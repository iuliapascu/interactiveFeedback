import {Injectable} from 'angular2/core';
import {Observable} from 'rxjs/Observable'
import HttpApiHelper from "../utils/HttpApiHelper";
import {Http} from "angular2/http";
import CourseEventsResponse from "../data/CourseEventsResponse";
import CourseEvent from "../data/CourseEvent";
import Question from "../data/Question";
import CourseEventQuestion from "../data/CourseEventQuestion";
import CourseEventQuestionResponse from "../data/CourseEventQuestionResponse";

@Injectable()
export default class CourseEventsService {
    private endpoint:string = '/events';
    private api:HttpApiHelper;

    constructor(private http:Http) {
        this.api = new HttpApiHelper(http, this.endpoint);
    }

    public getCourseEvents(courseId: number):Observable<Object> {
        let jsonData:Observable<Object>;

        this.api.resetDefaultSearchParams();
        this.api.setDefaultSearchParam('id=' + courseId);

        jsonData = this.api.getJson('/list');
        return jsonData.map((data) => CourseEventsResponse.fromJson(data));
    }

    public getCourseEventQuestions(courseEventId: number):Observable<Object> {
        let jsonData:Observable<Object>;

        this.api.resetDefaultSearchParams();
        this.api.setDefaultSearchParam('courseEventId=' + courseEventId);

        jsonData = this.api.getJson('/eventQuestions');
        return jsonData.map((data) => CourseEventQuestionResponse.fromJson(data));
    }

    public saveCourseEvent(courseEvent:CourseEvent, questions:Array<Question>):Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();

        let properties: Array<string> = courseEvent.getProperties();
        let questionIds: Array<string> = CourseEvent.getQuestionIds(questions);
        properties = properties.concat(questionIds);

        properties.forEach((keyValuePair:string) => {
            this.api.setDefaultSearchParam(keyValuePair);
        });

        jsonData = this.api.getJson('/save');
        return jsonData.map((data) => CourseEvent.fromJson(data));
    }

    public removeCourseEvent(courseEvent:CourseEvent):Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();

        this.api.setDefaultSearchParam('id=' + courseEvent.id);

        jsonData = this.api.getJson('/remove');
        return jsonData.map((data) => CourseEvent.fromJson(data));
    }

    public duplicateCourseEvent(courseEvent: CourseEvent):Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();

        this.api.setDefaultSearchParam('oldEventId=' + courseEvent.id);
        this.api.setDefaultSearchParam('name=' + courseEvent.name + " - duplicate");
        this.api.setDefaultSearchParam('courseId=' + courseEvent.courseId);

        jsonData = this.api.getJson('/duplicate');
        return jsonData.map((data) => CourseEvent.fromJson(data));
    }
}