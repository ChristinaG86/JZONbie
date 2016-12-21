import {ZombieRequest} from "./zombie-request";
import {ZombieResponse} from "./zombie-response";

export class PrimingRequest {

    request: ZombieRequest;
    response: ZombieResponse;

    constructor(
        request: ZombieRequest,
        response: ZombieResponse
    ) {
        this.request = request;
        this.response = response;
    }
}