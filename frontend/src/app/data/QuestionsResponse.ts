import Question from "./Question";

export default class QuestionsResponse {
    constructor(public results:Array<Question>) {}

    static fromJson(json:any):QuestionsResponse {
        let results:Array<Question> = [];
        json.forEach((cur) => {
            results.push(Question.fromJson(cur));
        });

        return new QuestionsResponse(results);
    }
}