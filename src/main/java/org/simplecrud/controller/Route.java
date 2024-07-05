package org.simplecrud.controller;

import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class Route {
    String path;
    HttpMethod method;

    public enum HttpMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route route)) return false;
        return Objects.equals(path, route.path) && method == route.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method);
    }
}
