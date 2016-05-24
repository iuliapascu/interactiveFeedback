export default class UserAnswer {

    constructor(public id?:number,
                public version?:number,
                public text?:string,
                public isCorrect?:boolean,
                public percentage?: number,
                public title?:string,
                public questionId?: number,
                public eventId?: number) {
    }

    static fromJson(json:any):UserAnswer {
        return new UserAnswer(
            json['id'],
            json['version'],
            json['text'],
            json['percentage'],
            json['questionId'],
            json['eventId']);
    }

    public getProperties(): any {
        let results: Array<string> = [];

        if (this.id != null && this.version != null) {
            results.push('id=' + this.id);
            results.push('version=' + this.version);
        }

        if (this.percentage != null) {
            results.push('percentage=' + this.percentage);
        }

        results.push('text=' + this.text);
        results.push('questionId=' + this.questionId);
        results.push('eventId=' + this.eventId);

        return results;
    }
}