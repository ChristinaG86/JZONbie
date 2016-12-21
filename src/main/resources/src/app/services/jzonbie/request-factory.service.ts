import {Injectable} from "@angular/core";
import {Request, RequestMethod, Headers} from "@angular/http";
import {PrimingRequest} from "../../model/priming-request";

@Injectable()
export class RequestFactory {

    createPrimeZombieRequest(primingRequest: PrimingRequest) {
        let headers: Headers = RequestFactory.defaultHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("zombie", "priming");

        return new Request({
            url: '/',
            method: RequestMethod.Post,
            headers: headers,
            body: primingRequest
        });
    }

    createGetHistoryRequest() {
        let headers: Headers = RequestFactory.defaultHeaders();
        headers.set("zombie", "history");

        return new Request({
            url: '/',
            method: RequestMethod.Get,
            headers: headers
        });
    }

    createGetCurrentPrimingRequest() {
        let headers: Headers = RequestFactory.defaultHeaders();
        headers.set("zombie", "list");

        return new Request({
            url: '/',
            method: RequestMethod.Get,
            headers: headers
        });
    }

    createResetRequest(): Request {
        let headers: Headers = RequestFactory.defaultHeaders();
        headers.set("zombie", "reset");

        return new Request({
            url: '/',
            method: RequestMethod.Delete,
            headers: headers
        });
    }

    private static defaultHeaders(): Headers {
        return new Headers({
            "Accept": "application/json"
        });
    }
}