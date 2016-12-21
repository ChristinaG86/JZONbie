import {Component} from "@angular/core";
import {JZONbieService} from "../../services/jzonbie/jzonbie.service";

@Component({
    selector: 'history',
    templateUrl: './history.component.html'
})
export class HistoryComponent {

    constructor(
      private jzonbieService: JZONbieService
    ){}
}