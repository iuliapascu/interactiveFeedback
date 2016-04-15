export default class Topic {

    constructor(public id?:number,
                public version?:number,
                public title?:string,
                public position?:number,
                public courseId?:number) {
    }

    static fromJson(json:any):Topic {
        return new Topic(
            json['id'],
            json['version'],
            json['title'],
            json['position'],
            json['courseId']);
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
}