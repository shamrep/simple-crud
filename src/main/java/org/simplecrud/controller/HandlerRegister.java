package org.simplecrud.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.simplecrud.controller.handler.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HandlerRegister {

    private static final HandlerRegister INSTANCE = new HandlerRegister();

    private final Map<String, Map<String, Handler>> handlerByUrlAndMethod = new HashMap<>();

    private HandlerRegister() {
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

    public static HandlerRegister getInstance() {
        return INSTANCE;
    }

    private void get(String url, Handler handler) {
        add("GET", url, handler);
    }

    private void post(String url, Handler handler) {
        add("POST", url, handler);
    }

    private void put(String url, Handler handler) {
        add("PUT", url, handler);
    }

    private void delete(String url, Handler handler) {
        add("DELETE", url, handler);
    }

    private void add(String method, String url, Handler handler) {
        Map<String, Handler> handlerByUrl = handlerByUrlAndMethod.computeIfAbsent(method, k -> new HashMap<>());
        handlerByUrl.put(url, handler);
    }

    public Optional<Handler> findHandler(HttpServletRequest req) {
        Map<String, Handler> handlerByUrl = handlerByUrlAndMethod.get(req.getMethod());

        if (handlerByUrl == null) {
            return Optional.empty();
        }

        String path = req.getPathInfo();
        String transformed = path.replaceAll("/\\d+$", "/:id");

        return Optional.ofNullable(handlerByUrl.get(transformed));
    }
}
