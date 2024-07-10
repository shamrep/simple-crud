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
        String path = request.getPathInfo();

        if (type.equals(Integer.class)) {
            String[] parts = path.split("/");
            if (parts.length >= 3) { // Assuming path format "/resource/id"
                String idStr = parts[2];
                return type.cast(Integer.parseInt(idStr));
            } else {
                throw new IllegalArgumentException("Invalid path format");
            }
        }

        // Example: Extracting a Long parameter
        else if (type.equals(Long.class)) {
            String[] parts = path.split("/");
            if (parts.length >= 3) { // Assuming path format "/resource/id"
                String idStr = parts[2];
                return type.cast(Long.parseLong(idStr));
            } else {
                throw new IllegalArgumentException("Invalid path format");
            }
        }

        // Example: Extracting a String parameter
        else if (type.equals(String.class)) {
            String[] parts = path.split("/");
            if (parts.length >= 3) { // Assuming path format "/resource/id"
                return type.cast(parts[2]);
            } else {
                throw new IllegalArgumentException("Invalid path format");
            }
        }

        return null;
    }
}
