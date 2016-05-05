import {Injectable} from 'angular2/core';
import {Observable} from 'rxjs/Observable'
import HttpApiHelper from "../utils/HttpApiHelper";
import {Http} from "angular2/http";
import AnswersResponse from "../data/AnswersResponse";
import Answer from "../data/Answer";

@Injectable()
export default class AnswersService {
    private endpoint:string = '/answers';
    private api:HttpApiHelper;

    constructor(private http:Http) {
        this.api = new HttpApiHelper(http, this.endpoint);
    }

    public getAnswers(id: number):Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();
        this.api.setDefaultSearchParam('id=' + id);

        jsonData = this.api.getJson('/list');
        return jsonData.map((data) => AnswersResponse.fromJson(data));
    }

    public saveAnswer(answer:Answer):Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();

        answer.getProperties().forEach((keyValuePair:string) => {
            this.api.setDefaultSearchParam(keyValuePair);
        });

        jsonData = this.api.getJson('/save');
        return jsonData.map((data) => Answer.fromJson(data));
    }

    public removeAnswer(answer:Answer):Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();

        this.api.setDefaultSearchParam('id=' + answer.id);

        jsonData = this.api.getJson('/remove');
        return jsonData.map((data) => Answer.fromJson(data));
    }
}