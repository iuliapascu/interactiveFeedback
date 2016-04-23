import {Injectable} from 'angular2/core';
import {Observable} from 'rxjs/Observable'
import HttpApiHelper from "../utils/HttpApiHelper";
import {Http} from "angular2/http";
import TopicsResponse from "../data/TopicsResponse";
import Topic from "../data/Topic";

@Injectable()
export default class TopicsService {
    private endpoint:string = '/topics';
    private api:HttpApiHelper;

    constructor(private http:Http) {
        this.api = new HttpApiHelper(http, this.endpoint);
    }

    public getTopics(id: number):Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();
        this.api.setDefaultSearchParam('id=' + id);

        jsonData = this.api.getJson('/list');
        return jsonData.map((data) => TopicsResponse.fromJson(data));
    }

    public saveTopic(topic:Topic):Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();

        topic.getProperties().forEach((keyValuePair:string) => {
            this.api.setDefaultSearchParam(keyValuePair);
        });

        jsonData = this.api.getJson('/save');
        return jsonData.map((data) => Topic.fromJson(data));
    }

    public removeTopic(topic:Topic):Observable<Object> {
        let jsonData:Observable<Object>;
        this.api.resetDefaultSearchParams();

        this.api.setDefaultSearchParam('id=' + topic.id);

        jsonData = this.api.getJson('/remove');
        return jsonData.map((data) => Topic.fromJson(data));
    }
}