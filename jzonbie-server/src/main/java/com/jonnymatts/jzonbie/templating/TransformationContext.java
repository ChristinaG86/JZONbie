package com.jonnymatts.jzonbie.templating;

import com.jonnymatts.jzonbie.pippo.PippoRequest;

import java.util.List;
import java.util.Map;

public class TransformationContext {

    public TransformationContext(PippoRequest request) {
        this.request = new RequestContext(request.getMethod(), request.getPath(), request.getBody(), request.getQueryParams(), request.getHeaders());
    }

    public RequestContext request;

    public RequestContext getRequest() {
        return request;
    }

    public static class RequestContext {
        public final String method;
        public final String path;
        public final String body;
        public final Map<String, List<String>> queryParams;
        public final Map<String, String> headers;

        public RequestContext(String method, String path, String body, Map<String, List<String>> queryParams, Map<String, String> headers) {
            this.method = method;
            this.path = path;
            this.body = body;
            this.queryParams = queryParams;
            this.headers = headers;
        }

        public String getMethod() {
            return method;
        }

        public String getPath() {
            return path;
        }

        public String getBody() {
            return body;
        }

        public Map<String, List<String>> getQueryParams() {
            return queryParams;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }
    }
}