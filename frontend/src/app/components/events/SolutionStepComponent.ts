import {Component, Input, Output, AfterViewInit} from "angular2/core";
import {TranslatePipe} from "ng2-translate";
import Question from "../../data/Question";
import UserAnswer from "../../data/UserAnswer";

@Component({
    selector: 'solution-step',
    templateUrl: 'app/components/events/solution-step-component.html',
    directives: [],
    pipes: [TranslatePipe]
})
export default class SolutionStepComponent implements AfterViewInit {
    @Input() private selectedQuestion:Question;

    private allUserAnswers: Array<UserAnswer>;

    constructor() {
        this.allUserAnswers = [
            new UserAnswer(1, 0, "First answer", true, 100, "answer1", 1, 1),
            new UserAnswer(2, 0, "Second answer", true, 100, "answer2", 1, 1),
            new UserAnswer(3, 0, "Third answer", false, 0, "answer3", 1, 1),
            new UserAnswer(4, 0, "Fourth answer", true, 100, "answer4", 1, 1),
            new UserAnswer(5, 0, "Fifth answer", false, 0, "answer5", 1, 1),
        ];
    }

    ngAfterViewInit() {
        this.showChart();
    }

    private pieData = [
        {
            value: 25,
            label: 'Correct',
            color: '#008000'
        },
        {
            value: 75,
            label: 'Incorrect',
            color: '#FF0000'
        }
    ];

    public showChart() {
        let canvas = <HTMLCanvasElement> document.getElementById("canvas");
        let ctx: CanvasRenderingContext2D = canvas.getContext("2d");

        if(window['chart'] != null){
            window['chart'].destroy();
        }
        window['chart'] = new Chart(ctx).Pie(this.pieData);
        document.getElementById('js-legend').innerHTML = window['chart'].generateLegend();
    }

}