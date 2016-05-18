import Question from "./Question";
export default class CourseEvent {

    constructor(public id?:number,
                public version?:number,
                public name?:string,
                public date?:string,
                public questions?:Array<Question>,
                public questionStates?: Map<number, number>) {
    }

    static fromJson(json:any):CourseEvent {
        let questions:Array<Question> = [];
        if (json['questions'] != null) {
            json['questions'].forEach((cur) => {
                questions.push(Question.fromJson(cur));
            });
        }
        let questionStates:Map<number, number> = new Map<number, number>();
        if (json['questionStates'] != null) {
            json['questions'].forEach((cur) => {
                questionStates.set(cur['questionId'], cur['state']);
            });
        }
        return new CourseEvent(
            json['id'],
            json['version'],
            json['name'],
            json['date'],
            questions,
            questionStates);
    }

    public getProperties(): any {
        let results: Array<string> = [];

        if (this.id != null && this.version != null) {
            results.push('id=' + this.id);
            results.push('version=' + this.version);
        }
        results.push('name=' + this.name);
        results.push('date=' + this.date);

        let questionList = "[";
        this.questions.forEach(crt => {
            questionList += crt.id + ",";
        });

        questionList = questionList.slice(0, -1) + "]";

        return results;
    }
}