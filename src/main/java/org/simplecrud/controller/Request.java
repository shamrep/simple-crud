package org.simplecrud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import java.io.InputStream;

@AllArgsConstructor
public class Request {

    private final HttpServletRequest request;

    public <T> T getBody(Class<T> type) {
        try (InputStream is = request.getInputStream()) {
            return new ObjectMapper().readValue(is, type);
        } catch (Exception e) {
            throw new RuntimeException("Could not read request body", e);
        }
    }

    public <T> T getPathParameter(String parameterName, Class<T> type) {
        String path = request.getPathInfo();
        String[] parts = path.split("/");

        if (parts.length >= 3) { // Assuming path format "/resource/id"
            String idStr = parts[2];

            if (type.equals(Integer.class)) {
                return type.cast(Integer.parseInt(idStr));
            } else if (type.equals(Long.class)) {
                return type.cast(Long.parseLong(idStr));
            } else if (type.equals(String.class)) {
                return type.cast(idStr);
            } else {
                throw new IllegalArgumentException("Invalid path format");
            }
        }

        return null;
    }
}
