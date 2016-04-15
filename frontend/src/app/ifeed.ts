/**
 * @author Iulia Pascu, Catalysts GmbH
 */
import {Component, Injectable} from 'angular2/core';
import IFeedComponent from "./components/IFeedComponent"

@Component({
    selector: 'ifeed-app',
    templateUrl: 'app/ifeed.html',
    directives: [IFeedComponent],
    pipes: []
})
export class IFeedApplication {

    constructor() {
    }
}