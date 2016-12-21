import {Component, OnInit} from "@angular/core";
import {JZONbieService} from "../../services/jzonbie/jzonbie.service";
import {PrimingRequest} from "../../model/priming-request";
import {ZombieRequest} from "../../model/zombie-request";
import {ZombieResponse} from "../../model/zombie-response";

const HISTORY: PrimingRequest[] = [
    new PrimingRequest(
        new ZombieRequest("/blah", "GET", {"Accept": "application/json"}, null, null),
        new ZombieResponse(200, {"Content-Type": "application/json"}, {"message": "Hello World!"})
    ),
    new PrimingRequest(
        new ZombieRequest("/create", "POST", {"Accept": "application/json"}, {"var": "val"}, null),
        new ZombieResponse(201, {"Content-Type": "application/json"}, {"message": "Blah created!"})
    )
];

@Component({
    selector: 'history',
    templateUrl: './history.component.html'
})
export class HistoryComponent implements OnInit {
    history: PrimingRequest[];

    constructor(
      private jzonbieService: JZONbieService
    ){}

    ngOnInit(): void {
          this.history = HISTORY;
    }
}