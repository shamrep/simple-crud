package org.simplecrud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Request {

    private final HttpServletRequest request;

    public <T> T getBody(Class<T> type) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(request.getInputStream(), type);
        } catch (Exception e) {
            throw new RuntimeException("Could not read request body", e);
        }
    }

    public <T> T getPathParameter(String parameterName, Class<T> type) {
        return null;
    }
}
