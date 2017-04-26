import {RequestFactory} from "./request-factory.service";
import {Request, Headers, RequestMethod} from "@angular/http";
import {PrimingRequest} from "../../model/priming-request";

describe("Request Factory Unit Tests", () => {
    let requestFactory: RequestFactory;

    beforeEach(() => {
        requestFactory = new RequestFactory();
    });

    it("createPrimeZombieRequest creates prime zombie request successfully", () => {
        let primingRequest: PrimingRequest = {} as PrimingRequest;
        let primeZombieRequest: Request = requestFactory.createPrimeZombieRequest(primingRequest);

        expect(primeZombieRequest.url).toBe('/');
        expect(primeZombieRequest.method).toBe(RequestMethod.Post);
        // expect(primeZombieRequest.getBody()).toBe(primingRequest); TODO: Fix this assertion

        let headers: Headers = primeZombieRequest.headers;

        expect(headers.keys().length).toBe(3);
        expect(headers.get("Accept")).toBe("application/json");
        expect(headers.get("Content-Type")).toBe("application/json");
        expect(headers.get("zombie")).toBe("priming");
    });

    it("createGetCurrentPrimingRequest creates get current priming request successfully", () => {
        let getCurringPrimingRequest: Request = requestFactory.createGetCurrentPrimingRequest();

        expect(getCurringPrimingRequest.url).toBe('/');
        expect(getCurringPrimingRequest.method).toBe(RequestMethod.Get);
        expect(getCurringPrimingRequest.getBody()).toBeFalsy();

        let headers: Headers = getCurringPrimingRequest.headers;

        expect(headers.keys().length).toBe(2);
        expect(headers.get("Accept")).toBe("application/json");
        expect(headers.get("zombie")).toBe("list");
    });

    it("createGetHistoryRequest creates get history request successfully", () => {
        let getHistoryRequest: Request = requestFactory.createGetHistoryRequest();

        expect(getHistoryRequest.url).toBe('/');
        expect(getHistoryRequest.method).toBe(RequestMethod.Get);
        expect(getHistoryRequest.getBody()).toBeFalsy();

        let headers: Headers = getHistoryRequest.headers;

        expect(headers.keys().length).toBe(2);
        expect(headers.get("Accept")).toBe("application/json");
        expect(headers.get("zombie")).toBe("history");
    });

    it("createResetRequest creates reset request successfully", () => {
        let resetRequest: Request = requestFactory.createResetRequest();

        expect(resetRequest.url).toBe('/');
        expect(resetRequest.method).toBe(RequestMethod.Delete);
        expect(resetRequest.getBody()).toBeFalsy();

        let headers: Headers = resetRequest.headers;

        expect(headers.keys().length).toBe(2);
        expect(headers.get("Accept")).toBe("application/json");
        expect(headers.get("zombie")).toBe("reset");
    });
});