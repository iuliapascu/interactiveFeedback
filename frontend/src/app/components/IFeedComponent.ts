import {Component} from "angular2/core";
import {TranslatePipe, TranslateService} from "ng2-translate";
import LogoutService from "../services/LogoutService"
import EventsComponent from "./events/EventsComponent";
import CourseManagementComponent from "./courseManagement/CourseManagementComponent";
import CourseAdminComponent from "./administration/CourseAdminComponent";
import UserAdminComponent from "./administration/UserAdminComponent";

@Component({
    selector: 'ifeed',
    templateUrl: 'app/components/ifeed-component.html',
    directives: [EventsComponent, CourseManagementComponent, CourseAdminComponent, UserAdminComponent],
    pipes: [TranslatePipe]
})
export default class IFeedComponent {

    private selectedPage:number;

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

    handleOnEventCreatedEvent(event: any) {
        this.selectPage(0);
    }
}