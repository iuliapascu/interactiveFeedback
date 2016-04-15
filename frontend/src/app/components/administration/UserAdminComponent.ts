import {Component} from "angular2/core";
import {TranslatePipe} from "ng2-translate";

@Component({
    selector: 'user-admin',
    templateUrl: 'app/components/administration/user-admin-component.html',
    directives: [],
    pipes: [TranslatePipe]
})
export default class UserAdminComponent {

    constructor() {
    }
}