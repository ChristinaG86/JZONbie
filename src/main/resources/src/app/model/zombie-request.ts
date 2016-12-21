export class ZombieRequest {

    path: string;
    method: string;
    headers: Object;
    body: Object;
    basicAuth: Object;

    constructor(
        path: string,
        method: string,
        headers: Object,
        body: Object,
        basicAuth: Object
    ){
        this.path = path;
        this.method = method;
        this.headers = headers;
        this.body = body;
        this.basicAuth = basicAuth;
    }
}