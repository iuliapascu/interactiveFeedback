import CourseEvent from "./CourseEvent";

export default class CourseEventsResponse {
    constructor(public results:Array<CourseEvent>) {}

    static fromJson(json:any):CourseEventsResponse {
        let results:Array<CourseEvent> = [];
        json.forEach((cur) => {
            results.push(CourseEvent.fromJson(cur));
        });

        return new CourseEventsResponse(results);
    }
}