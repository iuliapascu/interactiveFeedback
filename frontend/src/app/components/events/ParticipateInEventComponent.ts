import {Component, Input} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import Course from "../../data/Course";
import Topic from "../../data/Topic";
import TopicsResponse from "../../data/TopicsResponse";
import Question from "../../data/Question";
import CourseEvent from "../../data/CourseEvent";
import Answer from "../../data/Answer";
import AnswersService from "../../services/AnswersService";
import SolutionStepComponent from "./SolutionStepComponent";
import {Observable} from "rxjs/Observable";
import QuestionsResponse from "../../data/QuestionsResponse";
import AnswersResponse from "../../data/AnswersResponse";

@Component({
    selector: 'participate-in-event',
    templateUrl: 'app/components/events/participate-in-event-component.html',
    directives: [SolutionStepComponent],
    pipes: [TranslatePipe]
})
export default class ParticipateInEventComponent {
    @Input() private selectedEvent:CourseEvent;
    private selectedQuestion: Question;
    private selectedQuestionAnswers: Array<Answer>;
    private selectedStep: number;

    constructor(private answersService: AnswersService) {
    }

    public selectQuestion(question: Question) {
        this.queryAnswers(question);
        this.selectedQuestion = question;
        this.selectedStep = this.selectedEvent.questionStates.get(question.id);
    }

    public selectStep(step: number) {
        if (this.selectedStep != null) {
            this.selectedStep = step;
            let crtStep = this.selectedEvent.questionStates.get(this.selectedQuestion.id);
            this.selectedEvent.questionStates.set(this.selectedQuestion.id, Math.max(crtStep, step));
        }
    }

    public isSelectedQuestion(question: Question) {
        return this.selectedQuestion == question;
    }

    private queryAnswers(question: Question) {
        let searchResult: Observable<AnswersResponse> = this.answersService.getAnswers(question.id);
        searchResult.subscribe(
            searchResult => {
                this.selectedQuestionAnswers = searchResult.results;
            }
        );
    }

}