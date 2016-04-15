import Topic from "./Topic";

export default class TopicsResponse {
    constructor(public results:Array<Topic>) {}

    static fromJson(json:any):TopicsResponse {
        let results:Array<Topic> = [];
        json.forEach((cur) => {
            results.push(Topic.fromJson(cur));
        });

        return new TopicsResponse(results);
    }
}