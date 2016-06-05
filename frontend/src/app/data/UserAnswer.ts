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
        var text:string = json['text'];
        var title:string = text.substring(0, Math.min(20, text.length));
        return new UserAnswer(
            json['id'],
            json['version'],
            text,
            json['isCorrect'],
            json['percentage'],
            title,
            json['questionId'],
            json['eventId']);
    }

    public getProperties(): any {
        let results: Array<string> = [];

        results.push('text=' + this.text);
        results.push('questionId=' + this.questionId);
        results.push('eventId=' + this.eventId);

        return results;
    }
}