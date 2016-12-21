export class ZombieResponse {

    statusCode: number;
    headers: Object;
    body: Object;

    constructor(
        statusCode: number,
        headers: Object,
        body: Object
    ) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }
}