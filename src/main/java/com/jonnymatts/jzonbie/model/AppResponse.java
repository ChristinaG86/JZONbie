package com.jonnymatts.jzonbie.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jonnymatts.jzonbie.response.Response;

import java.util.Map;

public class AppResponse implements Response {

    private int statusCode;
    private Map<String, String> headers;
    private Map<String, Object> body;

    public Map<String, String> getHeaders() {
        return headers;
    }

    void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    @Override
    @JsonIgnore
    public boolean isFileResponse() {
        return false;
    }

    void setBody(Map<String, Object> body) {
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppResponse that = (AppResponse) o;

        if (statusCode != that.statusCode) return false;
        if (headers != null ? !headers.equals(that.headers) : that.headers != null) return false;
        return body != null ? body.equals(that.body) : that.body == null;
    }

    @Override
    public int hashCode() {
        int result = statusCode;
        result = 31 * result + (headers != null ? headers.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    public static AppResponseBuilder builder(int statusCode) {
        return new AppResponseBuilder(statusCode);
    }
}
