import {Observable} from 'rxjs/Observable';
import {Http, Response, Headers, RequestOptionsArgs} from 'angular2/http';
import HttpUtils from "./HttpUtils";

export default class HttpApiHelper {
    private defaultHeaderKeys: Array<string> = [];
    private defaultHeaderValues: Array<string> = [];
    private defaultSearchParams: Array<string> = [];

    constructor(private http:Http, private endpoint:string='') {
    }

    public setDefaultBasicAuthHeaderValue(token:string) {
        this.setDefaultHeaderValue('Authorization', 'Basic ' + btoa(token))
    }

    public setDefaultHeaderValue(key:string, value:string) {
        this.defaultHeaderKeys.push(key);
        this.defaultHeaderValues.push(value);
    }

    public setDefaultSearchParam(keyValuePair:string) {
        this.defaultSearchParams.push(keyValuePair);
    }

    public resetDefaultSearchParams() {
        this.defaultSearchParams = [];
    }

    private appendDefaultHeaderValues(options:RequestOptionsArgs) {
        if (this.defaultHeaderValues.length == 0) {
            return;
        }

        if (!options.headers) {
            options.headers = new Headers();
        }

        for (let i = 0 ; i < this.defaultHeaderKeys.length; i++ ) {
            let key = this.defaultHeaderKeys[i];
            let value = this.defaultHeaderValues[i];
            if (!options.headers.has(key)) {
                options.headers.append(key, value);
            }
        }
    }

    private appendDefaultSearchParamString(options:RequestOptionsArgs) {
        let defaultSearchParamString:string = '';
        this.defaultSearchParams.forEach((keyValue:string) => {
            defaultSearchParamString += `&${keyValue}`;
        });

        if (!options.search) {
            options.search = defaultSearchParamString.substr(1);
        } else {
            options.search += defaultSearchParamString;
        }
    }

    public getJson(apiUrl:string, options?:RequestOptionsArgs):Observable<any> {
        let url:string = `${this.endpoint}${apiUrl}`;

        if (!options) {
            options = {};
        }
        this.appendDefaultHeaderValues(options);
        this.appendDefaultSearchParamString(options);

        return this.getJsonInternal(url, options);
    }

    public postJson(apiUrl:string, body:any, options?:RequestOptionsArgs):Observable<any> {
        let url:string = `${this.endpoint}${apiUrl}`;

        if(!options) {
            options = {}
        }

        this.appendDefaultHeaderValues(options);
        this.appendDefaultSearchParamString(options);

        return this.postJsonInternal(url, JSON.stringify(body), options);
    }

    public getTestJson(fileUrl:string):Observable<any> {
        return this.getJsonInternal(fileUrl);
    }

    private getJsonInternal(url:string, options?:RequestOptionsArgs):Observable<any> {
        return HttpUtils.mapErrorStatusToError(
            this.http.get(url, options))
            .map((response:Response) => {
                // console.log(response.json());
                return response.json();
            });
    }

    private postJsonInternal(url:string, body:string, options:RequestOptionsArgs):Observable<any> {
        return HttpUtils.mapErrorStatusToError(
            this.http.post(url, body, options))
            .map((response:Response) => {
                return response.json();
            });
    }
}

