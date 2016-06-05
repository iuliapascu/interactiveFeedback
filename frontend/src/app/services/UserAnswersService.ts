import {Injectable} from 'angular2/core';
import {Observable} from 'rxjs/Observable'
import HttpApiHelper from "../utils/HttpApiHelper";
import {Http} from "angular2/http";
import UserAnswersResponse from "../data/UserAnswersResponse";
import UserAnswer from "../data/UserAnswer";

@Injectable()
export default class UserAnswersService {
    private endpoint:string = '/userAnswers';
    private api:HttpApiHelper;

    constructor(private http:Http) {
        this.api = new HttpApiHelper(http, this.endpoint);
    }

    public getUserAnswers(courseEventId: number, questionId:number):Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();

        this.api.setDefaultSearchParam('courseEventId=' + courseEventId);
        this.api.setDefaultSearchParam('questionId=' + questionId);

        jsonData = this.api.getJson('/list');
        return jsonData.map((data) => UserAnswersResponse.fromJson(data));
    }

    public saveUserAnswer(answer:UserAnswer):Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();

        answer.getProperties().forEach((keyValuePair:string) => {
            this.api.setDefaultSearchParam(keyValuePair);
        });

        jsonData = this.api.getJson('/save');
        return jsonData.map((data) => UserAnswer.fromJson(data));
    }

    public removeUserAnswer(answer:UserAnswer):Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();

        this.api.setDefaultSearchParam('id=' + answer.id);

        jsonData = this.api.getJson('/remove');
        return jsonData.map((data) => UserAnswer.fromJson(data));
    }
}