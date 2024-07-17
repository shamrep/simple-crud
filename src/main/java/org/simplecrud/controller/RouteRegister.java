package org.simplecrud.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.simplecrud.controller.handler.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class RouteRegister {

    private static final RouteRegister INSTANCE = new RouteRegister();

    private final List<Route> routes = new ArrayList<>();

    private RouteRegister() {
        get("/questions/:id", new GetQuestionHandler());
        post("/questions", new CreateQuestionHandler());
        put("/questions/:id", new UpdateQuestionHandler());
        delete("/questions/:id", new DeleteQuestionHandler());
        get("/questions", new GetAllQuestionsHandler());

        get("/tags/:id", new GetTagHandler());
        post("/tags", new CreateTagHandler());
        delete("/tags/:id", new DeleteTagHandler());
        put("/tags/:id", new UpdateTagHandler());
    }

    public static RouteRegister getInstance() {
        return INSTANCE;
    }

    private void get(String urlTemplate, Handler handler) {
        add("GET", urlTemplate, handler);
    }

    private void post(String urlTemplate, Handler handler) {
        add("POST", urlTemplate, handler);
    }

    private void put(String urlTemplate, Handler handler) {
        add("PUT", urlTemplate, handler);
    }

    private void delete(String urlTemplate, Handler handler) {
        add("DELETE", urlTemplate, handler);
    }

    private void add(String method, String urlTemplate, Handler handler) {
        routes.add(new Route(method, urlTemplate, handler));
    }

    public Optional<Route> findRoute(HttpServletRequest httpRequest) {
        String url = httpRequest.getPathInfo();
        String method = httpRequest.getMethod();

        return routes.stream()
                .filter(r -> r.getHttpMethod().equals(method)
                        && urlMatches(r.getUrlTemplate(), url))
                .findFirst();
    }

    private boolean urlMatches(String urlTemplate, String url) {
        List<String> urlTemplateParts = asList(urlTemplate.split("/"));
        List<String> urlParts = asList(url.split("/"));

        if (urlTemplateParts.size() != urlParts.size()) {
            return false;
        }

        for (int i = 0; i < urlTemplateParts.size(); i++) {
            String templatePart = urlTemplateParts.get(i);
            String part = urlParts.get(i);

            if (templatePart.startsWith(":")) {
                if (isNotNumber(part)) {
                    return false;
                }
            } else if (!templatePart.equals(part)) {
                return false;
            }
        }

        return true;
    }

    private boolean isNotNumber(String str) {
        try {
            Long.parseLong(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
