import {PrimingRequest} from "../../model/priming-request";
import {ZombieRequest} from "../../model/zombie-request";
import {ZombieResponse} from "../../model/zombie-response";
import {HistoryComponent} from "./history.component";
import {ComponentFixture, TestBed, async} from "@angular/core/testing";
import {DebugElement} from "@angular/core";
import {JzonbieService} from "../../services/jzonbie/jzonbie.service";
import {By} from "@angular/platform-browser";

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

describe("HistoryComponent Unit Tests", () => {

});

describe("HistoryComponent Angular Integration Tests", () => {

    let stubJzonbieService = {};
    let comp:    HistoryComponent;
    let fixture: ComponentFixture<HistoryComponent>;
    let de:      DebugElement;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            providers: [
                {
                    provide: JzonbieService,
                    useValue: stubJzonbieService
                }
            ],
            declarations: [ HistoryComponent ]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(HistoryComponent);

        comp = fixture.componentInstance;
        de = fixture.debugElement;
    });

    it("HistoryComponent exists", () => {
        expect(comp).toBeTruthy()
    });

    it("The correct number of history entries is rendered after init", () => {
        fixture.detectChanges();

        let historyContainer: HTMLElement = de.query(By.css(".history-container")).nativeElement;

        expect(historyContainer.children.length).toBe(2);
    });
});
