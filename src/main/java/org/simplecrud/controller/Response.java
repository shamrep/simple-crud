package org.simplecrud.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;

@AllArgsConstructor
@Getter
public class Response {

    private final int statusCode;
    private final Map<String, Object> headers;
    private final Object body;

    public static Response notFound() {
        return new Response(404, emptyMap(), null);
    }

    public static Response created(String location) {
        return new Response(201, Map.of("Location", location), null);
    }

    public static Response ok(Object body) {
        return new Response(200, emptyMap(), body);
    }

    public static Response noContent() {
        return new Response(204, emptyMap(), null);
    }

    public static Response internalServerError() {
        return new Response(500, emptyMap(), null);
    }

    public static Response dataAlreadyExists() {
        return new Response(409, emptyMap(), null);
    }

    public static Response badRequest(List<String> errors) {
        return new Response(400, emptyMap(), new ErrorBody(errors));
    }

    @RequiredArgsConstructor
    @Getter
    private static class ErrorBody {
        private final List<String> errors;
    }

}
