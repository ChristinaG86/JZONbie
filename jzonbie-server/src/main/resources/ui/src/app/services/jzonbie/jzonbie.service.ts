import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import {PrimingRequest} from "../../model/priming-request";
import {PrimedMapping} from "../../model/primed-mapping";
import {RequestFactory} from "../request-factory/request-factory.service";

@Injectable()
export class JzonbieService {

    constructor(private requestFactory: RequestFactory,
                private http: Http) {
    }

    primeZombie(primingRequest: PrimingRequest): PrimingRequest {
        return null;
    }

    getCurrentMappings(): PrimedMapping[] {
        return null;
    }

    getHistory(): PrimingRequest[] {
        // let headers: Headers = new Headers({
        //     "Accept": "application/json",
        //     "zombie": "reset"
        // });
        // return this.http.get("/", {
        //     headers: headers
        // }).toPromise()
        //     .then(response => response.json() as PrimingRequest[]);
        return null;
    }

    reset() {
        let headers: Headers = new Headers({
            "Accept": "application/json",
            "zombie": "reset"
        });
        this.http.delete("/", {
            headers: headers
        });
    }
}
