import Answer from "./Answer";

export default class AnswersResponse {
    constructor(public results:Array<Answer>) {}

    static fromJson(json:any):AnswersResponse {
        let results:Array<Answer> = [];
        json.forEach((cur) => {
            results.push(Answer.fromJson(cur));
        });

        return new AnswersResponse(results);
    }
}