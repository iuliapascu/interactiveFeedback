import CourseEventQuestion from "./CourseEventQuestion";

export default class CourseEvent {

    constructor(public id?: number,
                public version?: number,
                public name?: string,
                public date?: string,
                public courseId?: number,
                public questions?: Array<CourseEventQuestion>) {
    }

    static fromJson(json:any):CourseEvent {
        let questions:Array<CourseEventQuestion> = [];
        if (json['questions'] != null) {
            json['questions'].forEach((cur) => {
                questions.push(CourseEventQuestion.fromJson(cur));
            });
        }
        return new CourseEvent(
            json['id'],
            json['version'],
            json['name'],
            json['date'],
            json['courseId'],
            questions);
    }

    public getProperties(): Array<string> {
        let results: Array<string> = [];

        if (this.id != null && this.version != null) {
            results.push('id=' + this.id);
            results.push('version=' + this.version);
        }
        results.push('name=' + this.name);
        results.push('date=' + this.date);
        results.push('courseId=' + this.courseId);

        return results;
    }

    public static getQuestionIds(questions: Array<any>): Array<string> {
        let results: Array<string> = [];

        let questionList = "";
        questions.forEach(crt => {
            questionList += crt.id + ",";
        });
        questionList = questionList.slice(0, -1);

        results.push('questionList=' + questionList);

        return results;
    }

    public matchesFilterString(filter:string) {
        if (filter != null) {
            return this.name.toLowerCase().indexOf(filter.toLowerCase()) > -1;
        } else {
            return false;
        }
    }
}