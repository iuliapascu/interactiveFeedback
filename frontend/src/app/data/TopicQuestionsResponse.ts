import TopicQuestion from "./TopicQuestion";

export default class TopicQuestionsResponse {
    constructor(public results:Array<TopicQuestion>) {}

    static fromJson(json:any):TopicQuestionsResponse {
        let results:Array<TopicQuestion> = [];
        json.forEach((cur) => {
            results.push(TopicQuestion.fromJson(cur));
        });

        return new TopicQuestionsResponse(results);
    }
}