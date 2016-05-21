import Question from "./Question";
import {QuestionState} from "./enums/QuestionState";

export default class CourseEventQuestion {

    constructor(public id?: number,
                public version?: number,
                public courseEventId?: number,
                public question?: Question,
                public questionState?: QuestionState) {
    }

    static fromJson(json:any):CourseEventQuestion {
        let question: Question = Question.fromJson(json['question']);
        return new CourseEventQuestion(
            json['id'],
            json['version'],
            json['courseEventId'],
            question,
            json['questionState']);
    }

    public getProperties(): any {
        let results: Array<string> = [];

        if (this.id != null && this.version != null) {
            results.push('id=' + this.id);
            results.push('version=' + this.version);
        }
        results.push('courseEventId=' + this.courseEventId);
        results.push('questionId=' + this.question.id);
        results.push('questionState=' + this.questionState);

        return results;
    }
}