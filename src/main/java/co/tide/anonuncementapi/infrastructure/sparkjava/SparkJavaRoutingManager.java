package co.tide.announcementapi.infrastructure.sparkjava;

import co.tide.announcementapi.controller.ApplicationController;
import co.tide.announcementapi.library.RoutingManager;
import spark.QueryParamsMap;
import spark.Request;
import spark.ResponseTransformer;

import java.util.HashMap;
import java.util.Map;

import static co.tide.announcementapi.library.JsonOutputWrapper.wrap;
import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.notFound;
import static spark.Spark.port;
import static spark.Spark.post;

public class SparkJavaRoutingManager implements RoutingManager {

    int httpServerPort;
    private ApplicationController applicationController;
    private ResponseTransformer responseTransformer;

    public SparkJavaRoutingManager(
        ApplicationController applicationController,
        ResponseTransformer responseTransformer,
        int httpServerPort
    ) {
        this.applicationController = applicationController;
        this.responseTransformer = responseTransformer;
        this.httpServerPort = httpServerPort;
    }

    private static Map<String, String> prepareRequestParameters(
        Request request
    ) {

        QueryParamsMap parameterMap = request.queryMap();

        Map<String, String> parameters = new HashMap<>();
        parameters.put("announcementId", request.params("announcementId"));


        if (parameterMap
            .hasKey("rating")) {
            parameters.put("rating", parameterMap.get("rating").value());
        }

        return parameters;
    }

    public void route() {

        port(httpServerPort);

        exception(IllegalStateException.class, (exception, request, response) -> {
            response.status(500);
            try {
                response.body(responseTransformer.render(wrap("error", exception.getMessage())));
            } catch (Exception e) {
                //noop
            }
        });

        exception(IllegalArgumentException.class, (exception, request, response) -> {
            response.status(400);
            try {
                response.body(responseTransformer.render(wrap("error", exception.getMessage())));
            } catch (Exception e) {
                //noop
            }
        });

        notFound((req, res) -> responseTransformer.render(wrap("error", "No resource found - 404")));

        get(
            "/announcements/v1/:announcementId/rate",
            (request, response) -> applicationController.fetchRateAction(prepareRequestParameters(request)),
            responseTransformer
        );

        post(
            "/announcements/v1/:announcementId/rate",
            (request, response) -> applicationController.performRateAction(prepareRequestParameters(request)),
            responseTransformer
        );

        after((request, response) -> {
            response.type("application/json");
        });
    }
}
