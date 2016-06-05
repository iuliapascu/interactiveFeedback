import UserAnswer from "./UserAnswer";

export default class UserAnswersResponse {
    constructor(public results:Array<UserAnswer>) {}

    static fromJson(json:any):UserAnswersResponse {
        let results:Array<UserAnswer> = [];
        json.forEach((cur) => {
            results.push(UserAnswer.fromJson(cur));
        });

        return new UserAnswersResponse(results);
    }
}