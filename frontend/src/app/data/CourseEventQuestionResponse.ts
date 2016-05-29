import CourseEventQuestion from "./CourseEventQuestion";

export default class CourseEventQuestionResponse {
    constructor(public results:Array<CourseEventQuestion>) {}

    static fromJson(json:any):CourseEventQuestionResponse {
        let results:Array<CourseEventQuestion> = [];
        json.forEach((cur) => {
            results.push(CourseEventQuestion.fromJson(cur));
        });

        return new CourseEventQuestionResponse(results);
    }
}