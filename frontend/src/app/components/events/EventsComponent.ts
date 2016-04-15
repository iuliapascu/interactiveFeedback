import {Component} from "angular2/core";
import {TranslatePipe} from "ng2-translate";

@Component({
    selector: 'events',
    templateUrl: 'app/components/events/events-component.html',
    directives: [],
    pipes: [TranslatePipe]
})
export default class EventsComponent {

    constructor() {
    }
}