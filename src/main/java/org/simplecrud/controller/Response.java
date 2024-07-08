package org.simplecrud.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

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

}
