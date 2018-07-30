package com.jonnymatts.jzonbie.pippo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.google.common.base.Stopwatch;
import com.jonnymatts.jzonbie.JzonbieOptions;
import com.jonnymatts.jzonbie.model.TemplatedAppResponse;
import com.jonnymatts.jzonbie.model.content.BodyContent;
import com.jonnymatts.jzonbie.model.content.LiteralBodyContent;
import com.jonnymatts.jzonbie.requests.AppRequestHandler;
import com.jonnymatts.jzonbie.requests.PrimingNotFoundException;
import com.jonnymatts.jzonbie.requests.RequestHandler;
import com.jonnymatts.jzonbie.requests.ZombieRequestHandler;
import com.jonnymatts.jzonbie.response.CurrentPrimingFileResponseFactory.FileResponse;
import com.jonnymatts.jzonbie.response.ErrorResponse;
import com.jonnymatts.jzonbie.response.PrimingNotFoundErrorResponse;
import com.jonnymatts.jzonbie.response.Response;
import com.jonnymatts.jzonbie.templating.JsonPathHelper;
import com.jonnymatts.jzonbie.templating.TransformationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.pippo.core.Application;
import ro.pippo.core.route.RouteContext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static ro.pippo.core.HttpConstants.ContentType.APPLICATION_JSON;

public class PippoApplication extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(PippoApplication.class);

    private final AppRequestHandler appRequestHandler;
    private final ZombieRequestHandler zombieRequestHandler;
    private final ObjectMapper objectMapper;
    private final String zombieHeaderName;
    private final List<JzonbieRoute> additionalRoutes;

    public PippoApplication(JzonbieOptions options,
                            AppRequestHandler appRequestHandler,
                            ZombieRequestHandler zombieRequestHandler,
                            ObjectMapper objectMapper,
                            List<JzonbieRoute> additionalRoutes) {

        this.appRequestHandler = appRequestHandler;
        this.zombieRequestHandler = zombieRequestHandler;
        this.objectMapper = objectMapper;
        this.zombieHeaderName = options.getZombieHeaderName();
        this.additionalRoutes = additionalRoutes;
    }

    @Override
    protected void onInit() {
        additionalRoutes.forEach(route -> route.accept(this));
        ANY(".*", this::handleRequest);
    }

    private void handleRequest(RouteContext routeContext) {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        final PippoRequest pippoRequest = new PippoRequest(routeContext.getRequest());
        final ro.pippo.core.Response pippoResponse = routeContext.getResponse();

        final String zombieHeader = pippoRequest.getHeaders().get(zombieHeaderName);

        final RequestHandler requestHandler = zombieHeader != null ?
                zombieRequestHandler : appRequestHandler;

        try {
            final Response response = requestHandler.handle(pippoRequest);
            if(response instanceof FileResponse) {
                final FileResponse fileResponse = (FileResponse) response;
                pippoResponse.contentType(APPLICATION_JSON);
                pippoResponse.file(fileResponse.getFileName(), new ByteArrayInputStream(fileResponse.getContents().getBytes()));
            } else {
                primeResponse(pippoResponse, response);

                response.getDelay().ifPresent(d -> {
                    try {
                        Thread.sleep(d.toMillis());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

                final Object body = response.getBody();

                if(body == null) {
                    routeContext.getResponse().commit();
                } else if(body instanceof LiteralBodyContent) {
                    final String transformedBody = transformResponseBody(pippoRequest, ((LiteralBodyContent) body).getContent());
                    routeContext.send(transformedBody);
                } else {
                    final Object o = (body instanceof BodyContent) ? ((BodyContent) body).getContent() : body;
                    final String bodyString = objectMapper.writeValueAsString(o);
                    if(response instanceof TemplatedAppResponse) {
                        final String transformedBodyString = transformResponseBody(pippoRequest, bodyString);
                        routeContext.send(transformedBodyString);
                    } else {
                        routeContext.send(bodyString);
                    }
                }
            }
        } catch (PrimingNotFoundException e) {
            LOGGER.error("Priming not found for request {}", e.getRequest());
            sendErrorResponse(routeContext, SC_NOT_FOUND, new PrimingNotFoundErrorResponse(e.getRequest()));
        } catch (Exception e) {
            LOGGER.error("Exception occurred: " + e.getClass().getSimpleName(), e);
            sendErrorResponse(routeContext, SC_INTERNAL_SERVER_ERROR, new ErrorResponse(format("Error occurred: %s - %s", e.getClass().getName(), e.getMessage())));
        } finally {
            stopwatch.stop();
            LOGGER.debug("Handled request {} in {} ms", pippoRequest, stopwatch.elapsed(MILLISECONDS));
        }
    }

    private String transformResponseBody(PippoRequest pippoRequest, String bodyString) throws IOException {
        final TransformationContext transformationContext = new TransformationContext(pippoRequest);
        final Handlebars handlebars = new Handlebars();
        handlebars.registerHelper("jsonPath", new JsonPathHelper());
        final Template template = handlebars.compileInline(bodyString);
        return template.apply(transformationContext);
    }

    private void primeResponse(ro.pippo.core.Response response, Response r) {
        response.status(r.getStatusCode());

        final Map<String, String> headers = r.getHeaders();

        if(headers != null) {
            headers.forEach(response::header);
        }
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