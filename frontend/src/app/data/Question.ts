import {QuestionType} from "./enums/QuestionType";
export default class Question {

    constructor(public id?:number,
                public version?:number,
                public title?:string,
                public requirement?:string,
                public questionType?:QuestionType,
                public goodKeywords?:string,
                public badKeywords?:string) {
    }

    static fromJson(json:any):Question {
        return new Question(
            json['id'],
            json['version'],
            json['title'],
            json['requirement'],
            json['questionType'],
            json['goodKeywords'],
            json['badKeywords']);
    }

    public getProperties(): any {
        let results: Array<string> = [];

        if (this.id != null && this.version != null) {
            results.push('id=' + this.id);
            results.push('version=' + this.version);
        }
        results.push('title=' + this.title);
        results.push('requirement=' + this.requirement);
        results.push('questionType=' + this.questionType);

            if (this.goodKeywords != null) {
            results.push('goodKeywords=' + this.goodKeywords);
        }
        if (this.badKeywords != null) {
            results.push('badKeywords=' + this.badKeywords);
        }

        return results;
    }

    public titleMatchesFilterString(filter:string) {
        if (filter != null) {
            return this.title.toLowerCase().indexOf(filter.toLowerCase()) > -1;
        } else {
            return false;
        }
    }

    public contentMatchesFilterString(filter:string) {
        let content: string = this.title + " " + this.requirement;
        if (filter != null) {
            return content.toLowerCase().indexOf(filter.toLowerCase()) > -1;
        } else {
            return false;
        }
    }
}