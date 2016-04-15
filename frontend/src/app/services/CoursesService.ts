import {Injectable} from 'angular2/core';
import {Observable} from 'rxjs/Observable'
import HttpApiHelper from "../utils/HttpApiHelper";
import {Http} from "angular2/http";
import CoursesResponse from "../data/CoursesResponse";
import Course from "../data/Course";

@Injectable()
export default class CoursesService {
    private endpoint:string = '/courses';
    private api:HttpApiHelper;

    constructor(private http:Http) {
        this.api = new HttpApiHelper(http, this.endpoint);
    }

    public getCourses():Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();

        jsonData = this.api.getJson('/list');
        return jsonData.map((data) => CoursesResponse.fromJson(data));
    }

    public saveCourse(course:Course):Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();

        course.getProperties().forEach((keyValuePair:string) => {
            this.api.setDefaultSearchParam(keyValuePair);
        });

        jsonData = this.api.getJson('/save');
        return jsonData.map((data) => Course.fromJson(data));
    }

    public removeCourse(course:Course):Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();

        this.api.setDefaultSearchParam('id=' + course.id);

        jsonData = this.api.getJson('/remove');
        return jsonData.map((data) => Course.fromJson(data));
    }
}