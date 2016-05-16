export default class TopicQuestion {

    constructor(public topicId?:number,
                public questionId?:number) {
    }

    static fromJson(json:any):TopicQuestion {
        return new TopicQuestion(
            json['topicId'],
            json['questionId']);
    }

    public getProperties(): any {
        let results: Array<string> = [];

        if (this.topicId != null && this.questionId != null) {
            results.push('topicId=' + this.topicId);
            results.push('questionId=' + this.questionId);
        }

        return results;
    }
}