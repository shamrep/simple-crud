package org.simplecrud.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class Response {

    private final int statusCode;
    private final Map<String, Object> headers;
    private final Object body;

    public static Response notFound() {
        return new Response(404, defaultHeaders(), null);
    }

    public static Response created(String location) {
        Map<String, Object> headers = new HashMap<>(defaultHeaders());
        headers.put("Location", location);

        return new Response(201, Map.copyOf(headers), null);
    }

    public static Response ok(Object body) {
        return new Response(200, defaultHeaders(), body);
    }

    public static Response noContent() {
        return new Response(204, defaultHeaders(), null);
    }

    public static Response internalServerError() {
        return new Response(500, defaultHeaders(), null);
    }

    public static Response badRequest(List<String> errors) {
        return new Response(400, defaultHeaders(), new ErrorBody(errors));
    }

    private static Map<String, Object> defaultHeaders() {
        return Map.of("Content-Type", "application/json");
    }

    @RequiredArgsConstructor
    @Getter
    private static class ErrorBody {
        private final List<String> errors;
    }

}
