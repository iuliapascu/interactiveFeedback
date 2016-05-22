import Question from "./Question";
export default class Topic {

    constructor(public id?:number,
                public version?:number,
                public title?:string,
                public position?:number,
                public courseId?:number,
                public questions?: Array<Question>) {
        this.position = 0;
    }

    static fromJson(json:any):Topic {
        let questions:Array<Question> = [];
        if (json['questions'] != null) {
            json['questions'].forEach((cur) => {
                questions.push(Question.fromJson(cur));
            });
        }
        return new Topic(
            json['id'],
            json['version'],
            json['title'],
            json['position'],
            json['courseId'],
            questions);
    }

    public getProperties(): any {
        let results: Array<string> = [];

        if (this.id != null && this.version != null) {
            results.push('id=' + this.id);
            results.push('version=' + this.version);
        }
        results.push('title=' + this.title);
        results.push('position=' + this.position);
        results.push('courseId=' + this.courseId);

        return results;
    }

    public isQuestionAssigned(question: Question): boolean {
        let result: boolean = false;
        for (var q of this.questions) {
            if (q.id == question.id) {
                return true;
            }
        }
        return false;
    }
}