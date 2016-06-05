///<reference path="../node_modules/angular2/typings/es6-collections/es6-collections.d.ts"/>
///<reference path="../node_modules/angular2/typings/es6-promise/es6-promise.d.ts"/>

import {bootstrap} from 'angular2/platform/browser';
import {HTTP_PROVIDERS} from 'angular2/http';
import {TranslateService, TranslatePipe} from 'ng2-translate'
import {IFeedApplication} from './app/ifeed';
import LogoutService from './app/services/LogoutService';
import CoursesService from "./app/services/CoursesService";
import QuestionsService from "./app/services/QuestionsService";
import TopicsService from "./app/services/TopicsService";
import AnswersService from "./app/services/AnswersService";
import CourseEventsService from "./app/services/CourseEventsService";
import UserAnswersService from "./app/services/UserAnswersService";

bootstrap(IFeedApplication, [HTTP_PROVIDERS, TranslateService, TranslatePipe, LogoutService, CoursesService,
                             QuestionsService, TopicsService, AnswersService, CourseEventsService, UserAnswersService]);