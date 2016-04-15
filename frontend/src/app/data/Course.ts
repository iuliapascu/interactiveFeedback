export default class Course {

    constructor(public id?:number,
                public version?:number,
                public name?:string,
                public code?:string) {
    }

    static fromJson(json:any):Course {
        return new Course(
            json['id'],
            json['version'],
            json['name'],
            json['code']);
    }

    public getProperties(): any {
        let results: Array<string> = [];

        if (this.id != null && this.version != null) {
            results.push('id=' + this.id);
            results.push('version=' + this.version);
        }
        results.push('name=' + this.name);
        results.push('code=' + this.code);

        return results;
    }
}