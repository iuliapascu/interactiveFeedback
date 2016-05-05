export default class Answer {

    constructor(public id?:number,
                public version?:number,
                public text?:string,
                public isCorrect?:boolean,
                public position?:number,
                public questionId?:number) {
    }

    static fromJson(json:any):Answer {
        return new Answer(
            json['id'],
            json['version'],
            json['text'],
            json['correct'],
            json['position'],
            json['questionId']);
    }

    public getProperties(): any {
        let results: Array<string> = [];

        if (this.id != null && this.version != null) {
            results.push('id=' + this.id);
            results.push('version=' + this.version);
        }
        if (this.isCorrect != null) {
            results.push('correct=' + this.isCorrect);
        }
        if (this.position != null) {
            results.push('position=' + this.position);
        }

        results.push('text=' + this.text);
        results.push('questionId=' + this.questionId);

        return results;
    }
}