package org.simplecrud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import java.io.InputStream;
import java.util.List;

import static java.util.Arrays.asList;

@AllArgsConstructor
public class Request {

    private final HttpServletRequest httpRequest;
    private final String urlTemplate;

    public <T> T getBody(Class<T> type) {
        try (InputStream is = httpRequest.getInputStream()) {
            return new ObjectMapper().readValue(is, type);
        } catch (Exception e) {
            throw new RuntimeException("Could not read request body", e);
        }
    }

    public long getPathParameter(String parameterName) {
        List<String> urlTemplateParts = asList(urlTemplate.split("/"));
        List<String> urlParts = asList(httpRequest.getPathInfo().split("/"));

        for (int i = 0; i < urlTemplateParts.size(); i++) {
            String urlTemplatePart = urlTemplateParts.get(i);

            if (urlTemplatePart.startsWith(":") && urlTemplatePart.equals(":" + parameterName)) {
                String urlPart = urlParts.get(i);
                return Long.parseLong(urlPart);
            }
        }

        throw new RuntimeException("Could not find parameter by name = " + parameterName);
    }
}
