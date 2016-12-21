export class ZombieRequest {

    path: string;
    method: string;
    body: Object;
    basicAuth: Object;

    constructor(
        path: string,
        method: string,
        body: Object,
        basicAuth: Object
    ){
        this.path = path;
        this.method = method;
        this.body = body;
        this.basicAuth = basicAuth;
    }
}