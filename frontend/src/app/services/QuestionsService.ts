import {Injectable} from 'angular2/core';
import {Observable} from 'rxjs/Observable'
import HttpApiHelper from "../utils/HttpApiHelper";
import {Http} from "angular2/http";
import QuestionsResponse from "../data/QuestionsResponse";
import Question from "../data/Question";

@Injectable()
export default class QuestionsService {
    private endpoint:string = '/questions';
    private api:HttpApiHelper;

    constructor(private http:Http) {
        this.api = new HttpApiHelper(http, this.endpoint);
    }

    public getQuestions():Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();

        jsonData = this.api.getJson('/list');
        return jsonData.map((data) => QuestionsResponse.fromJson(data));
    }

    public getQuestion(id: number):Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();

        this.api.setDefaultSearchParam('id=' + id);

        jsonData = this.api.getJson('/find');
        return jsonData.map((data) => Question.fromJson(data));
    }

    public saveQuestion(question:Question):Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();

        question.getProperties().forEach((keyValuePair:string) => {
            this.api.setDefaultSearchParam(keyValuePair);
        });

        jsonData = this.api.getJson('/save');
        return jsonData.map((data) => Question.fromJson(data));
    }

    public removeQuestion(question:Question):Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();

        this.api.setDefaultSearchParam('id=' + question.id);

        jsonData = this.api.getJson('/remove');
        return jsonData.map((data) => Question.fromJson(data));
    }
}