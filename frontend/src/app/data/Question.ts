export default class Question {

    constructor(public id?:number,
                public version?:number,
                public title?:string,
                public requirement?:string) {
    }

    static fromJson(json:any):Question {
        return new Question(
            json['id'],
            json['version'],
            json['title'],
            json['requirement']);
    }

    public getProperties(): any {
        let results: Array<string> = [];

        if (this.id != null && this.version != null) {
            results.push('id=' + this.id);
            results.push('version=' + this.version);
        }
        results.push('title=' + this.title);
        results.push('requirement=' + this.requirement);

        return results;
    }
}