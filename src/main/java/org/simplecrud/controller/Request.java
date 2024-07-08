package org.simplecrud.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Request {

    private final HttpServletRequest request;

    public <T> T getBody(Class<T> type) {
        return null;
    }

    public <T> T getPathParameter(String parameterName, Class<T> type) {
        return null;
    }
}
