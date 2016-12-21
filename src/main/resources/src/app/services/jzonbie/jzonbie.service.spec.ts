import {RequestFactory} from "./request-factory.service";
import {Http} from "@angular/http";
import {JZONbieService} from "./jzonbie.service";

describe("JZONbieService Unit Tests", () => {
    let jzonbieService: JZONbieService;
    let requestFactory: RequestFactory = {} as RequestFactory;
    let http: Http = <any>{
        get: () => null,
        delete: () => null
    } as Http;

    beforeEach(() => {
        jzonbieService = new JZONbieService(requestFactory, http);
    });

    // it("getHistory sends get history request", () => { TODO: Fix this too
    //     let primingRequests: PrimingRequest[] = [];
    //
    //     let getSpy = spyOn(http, "get");
    //
    //     let history: PrimingRequest[] = jzonbieService.getHistory();
    //
    //     expect(getSpy.calls.count()).toBe(1);
    //
    //     let deleteArgs = getSpy.calls.mostRecent().args;
    //
    //     expect(deleteArgs[0]).toBe("/");
    //
    //     let headers = deleteArgs[1].headers;
    //
    //     expect(headers.keys().length).toBe(2);
    //     expect(headers.get("Accept")).toBe("application/json");
    //     expect(headers.get("zombie")).toBe("history");
    // });

    it("reset sends reset request", () => {
        let deleteSpy = spyOn(http, "delete");

        jzonbieService.reset();

        expect(deleteSpy.calls.count()).toBe(1);

        let deleteArgs = deleteSpy.calls.mostRecent().args;

        expect(deleteArgs[0]).toBe("/");

        let headers = deleteArgs[1].headers;

        expect(headers.keys().length).toBe(2);
        expect(headers.get("Accept")).toBe("application/json");
        expect(headers.get("zombie")).toBe("reset");
    });
});