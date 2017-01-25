package com.jonnymatts.jzonbie.pippo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonnymatts.jzonbie.requests.AppRequestHandler;
import com.jonnymatts.jzonbie.requests.PrimingNotFoundException;
import com.jonnymatts.jzonbie.requests.RequestHandler;
import com.jonnymatts.jzonbie.requests.ZombieRequestHandler;
import com.jonnymatts.jzonbie.response.ErrorResponse;
import com.jonnymatts.jzonbie.response.PrimingNotFoundErrorResponse;
import com.jonnymatts.jzonbie.response.Response;
import ro.pippo.core.Application;
import ro.pippo.core.route.RouteContext;

import java.util.Map;

import static java.lang.String.format;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class PippoApplication extends Application {

    private final AppRequestHandler appRequestHandler;
    private final ZombieRequestHandler zombieRequestHandler;
    private final ObjectMapper objectMapper;

    public PippoApplication(AppRequestHandler appRequestHandler,
                            ZombieRequestHandler zombieRequestHandler,
                            ObjectMapper objectMapper) {

        this.appRequestHandler = appRequestHandler;
        this.zombieRequestHandler = zombieRequestHandler;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void onInit() {
        ALL(".*", this::handleRequest);
    }

    private void handleRequest(RouteContext routeContext) {
        final PippoRequest pippoRequest = new PippoRequest(routeContext.getRequest());
        final ro.pippo.core.Response pippoResponse = routeContext.getResponse();

        final String zombieHeader = pippoRequest.getHeaders().get("zombie");

        final RequestHandler requestHandler = zombieHeader != null ?
                zombieRequestHandler : appRequestHandler;

        try {
            final Response response = requestHandler.handle(pippoRequest);
            primeResponse(pippoResponse, response);
            routeContext.send(objectMapper.writeValueAsString(response.getBody()));
        } catch (PrimingNotFoundException e) {
            sendErrorResponse(routeContext, SC_NOT_FOUND, new PrimingNotFoundErrorResponse(e.getRequest()));
        } catch (Exception e) {
            sendErrorResponse(routeContext, SC_INTERNAL_SERVER_ERROR, new ErrorResponse(format("Error occurred: %s - %s", e.getClass().getName(), e.getMessage())));
        }
    }

    private void primeResponse(ro.pippo.core.Response response, Response r) throws JsonProcessingException {
        response.status(r.getStatusCode());

        final Map<String, String> headers = r.getHeaders();

        response.contentType("application/json");

        if (headers != null) headers.entrySet().forEach(entry -> response.header(entry.getKey(), entry.getValue()));
    }

    private void sendErrorResponse(RouteContext routeContext, int statusCode, ErrorResponse errorResponse) {
        final ro.pippo.core.Response pippoResponse = routeContext.getResponse();
        pippoResponse.status(statusCode);

        try {
            routeContext.json().send(objectMapper.writeValueAsString(errorResponse));
        } catch (Exception e) {
            routeContext.send(errorResponse.getMessage());
        }
    }
}