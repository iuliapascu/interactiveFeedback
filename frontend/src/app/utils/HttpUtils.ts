
import {Observable} from 'rxjs/Observable';
import {Response} from 'angular2/http';

export default class HttpUtils {
    static isSuccessCode(responseCode:number) {
        return responseCode >= 200 && responseCode < 300;
    }

    static mapErrorStatusToError(observable:Observable<Response>):Observable<Response> {
        return new Observable((observer) => {
            observable.subscribe((response) => {
                if (HttpUtils.isSuccessCode(response.status)) {
                    observer.next(response);
                } else {
                    console.error(response);
                    observer.error(response);
                }
            }, observer.error, () => observer.complete());
        });
    }
}