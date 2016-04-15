import Course from "./Course";

export default class CoursesResponse {
    constructor(public results:Array<Course>) {}

    static fromJson(json:any):CoursesResponse {
        let results:Array<Course> = [];
        json.forEach((cur) => {
            results.push(Course.fromJson(cur));
        });

        return new CoursesResponse(results);
    }
}