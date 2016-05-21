import {Component, Input} from "angular2/core";
import {Observable} from "rxjs/Observable";
import {TranslatePipe} from "ng2-translate";

import CourseEvent from "../../data/CourseEvent";
import CourseEventQuestion from "../../data/CourseEventQuestion";
import {QuestionState} from "../../data/enums/QuestionState";

import Answer from "../../data/Answer";
import AnswersService from "../../services/AnswersService";
import AnswersResponse from "../../data/AnswersResponse";

import SolutionStepComponent from "./SolutionStepComponent";

@Component({
    selector: 'participate-in-event',
    templateUrl: 'app/components/events/participate-in-event-component.html',
    directives: [SolutionStepComponent],
    pipes: [TranslatePipe]
})
export default class ParticipateInEventComponent {
    @Input() private selectedEvent:CourseEvent;
    private selectedQuestion: CourseEventQuestion;
    private selectedQuestionAnswers: Array<Answer>;
    private selectedStep: number;

    constructor(private answersService: AnswersService) {
    }

    public selectQuestion(courseEventQuestion: CourseEventQuestion) {
        this.queryAnswers(courseEventQuestion);
        this.selectedQuestion = courseEventQuestion;
        this.selectedStep = this.getQuestionStateIndex(courseEventQuestion.questionState);
    }

    public selectStep(step: number) {
        this.selectedStep = step;
        let crtStep = this.selectedQuestion.questionState;

        if (this.getQuestionStateIndex(crtStep) < step){
            this.selectedQuestion.questionState = this.getQuestionState(step);
        }
    }

    public isSelectedQuestion(courseEventQuestion: CourseEventQuestion) {
        return (this.selectedQuestion != null) && (this.selectedQuestion.question.id == courseEventQuestion.question.id);
    }

    private queryAnswers(courseEventQuestion: CourseEventQuestion) {
        let searchResult: Observable<AnswersResponse> = this.answersService.getAnswers(courseEventQuestion.question.id);
        searchResult.subscribe(
            searchResult => {
                this.selectedQuestionAnswers = searchResult.results;
            }
        );
    }

    private getQuestionStateIndex(state: QuestionState): number {
        switch(state) {
            case QuestionState.PREPARATION: return 0;
            case QuestionState.PRESENTATION: return 1;
            case QuestionState.SOLUTION: return 2;
            case QuestionState.DISCUSSION: return 3;
        }
        return -1;
    }

    private getQuestionState(index: number): QuestionState {
        switch(index) {
            case 0: return QuestionState.PREPARATION;
            case 1: return QuestionState.PRESENTATION;
            case 2: return QuestionState.SOLUTION;
            case 3: return QuestionState.DISCUSSION;
        }
        return null;
    }

}