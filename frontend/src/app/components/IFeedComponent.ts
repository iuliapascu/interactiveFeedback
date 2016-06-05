import {Component} from "angular2/core";
import {TranslatePipe, TranslateService} from "ng2-translate";
import LogoutService from "../services/LogoutService"
import EventsComponent from "./events/EventsComponent";
import CourseManagementComponent from "./courseManagement/CourseManagementComponent";
import CourseAdminComponent from "./administration/CourseAdminComponent";
import UserAdminComponent from "./administration/UserAdminComponent";
import CourseEvent from "../data/CourseEvent";
import StudentComponent from "./student/StudentComponent";

@Component({
    selector: 'ifeed',
    templateUrl: 'app/components/ifeed-component.html',
    directives: [EventsComponent, CourseManagementComponent, CourseAdminComponent, UserAdminComponent, StudentComponent],
    pipes: [TranslatePipe]
})
export default class IFeedComponent {

    private selectedPage:number;
    private createdEvent:CourseEvent;

    constructor(public translate:TranslateService, private logoutService:LogoutService) {
        translate.use('en');
        this.selectedPage = 1;
    }

    public selectPage(selectedPage:number) {
        return this.selectedPage = selectedPage;
    }

    public logout() {
        this.logoutService.logout();
    }

    handleOnEventCreatedEvent(event: CourseEvent) {
        this.selectPage(0);
        this.createdEvent = event;
    }
}