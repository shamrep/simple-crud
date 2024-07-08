package org.simplecrud.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.simplecrud.controller.handler.*;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class HandlerRegister {

    private static final HandlerRegister INSTANCE = new HandlerRegister();

    private final Map<String, Map<String, Handler>> handlerByUrlAndMethod = new ConcurrentHashMap<>();

    private HandlerRegister() {
        get("/questions/test", new GetQuestionHandler()); // TODO: delete, just for test

        get("/questions/:id", new GetQuestionHandler());
        post("/questions", new CreateQuestionHandler());
        put("/questions/:id", new UpdateQuestionHandler());
        delete("/questions/:id", new DeleteQuestionHandler());
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
        Map<String, Handler> handlerByUrl = handlerByUrlAndMethod.computeIfAbsent(method, k -> new ConcurrentHashMap<>());
        handlerByUrl.put(url, handler);
    }

    public Optional<Handler> findHandler(HttpServletRequest req) {
        Map<String, Handler> handlerByUrl = handlerByUrlAndMethod.get(req.getMethod());
        if (handlerByUrl == null) {
            return Optional.empty();
        }

        // TODO: /questions/:id should match with /questions/123
        String path = req.getPathInfo();
        return Optional.ofNullable(handlerByUrl.get(path));
    }

}
