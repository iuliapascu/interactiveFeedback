import {Injectable} from 'angular2/core';
import {Http} from 'angular2/http';
import HttpApiHelper from "../utils/HttpApiHelper";

@Injectable()
export default class ComputationResultsService {
    private endpoint:string = '/';
    private api:HttpApiHelper;

    constructor(private http:Http) {
        this.api = new HttpApiHelper(http, this.endpoint);
    }

    public logout(): void {
        this.api.resetDefaultSearchParams();
        this.api.postJson('/logout', '');
    }
}