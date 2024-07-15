package org.simplecrud;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathParameterProvider {

    private final String uriTemplate;
    private final String actualUri;

    public PathParameterProvider(String uriTemplate, String actualUri) {
        this.uriTemplate = uriTemplate;
        this.actualUri = actualUri;
    }

    public <T> T getPathParameter(String parameterName, Class<T> type) {
        Map<String, String> pathParams = extractPathParameters(uriTemplate, actualUri);
        String parameterValue = pathParams.get(parameterName);

        if (parameterValue == null) {
            throw new IllegalArgumentException("Path parameter not found: " + parameterName);
        }

        return convertToType(parameterValue, type);
    }

    private Map<String, String> extractPathParameters(String template, String uri) {
        Map<String, String> pathParams = new HashMap<>();
        String regex = template.replaceAll("(:[^/]+)", "(?<$1>[^/]+)").replaceAll(":", "");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(uri);

        if (matcher.matches()) {
            for (String name : getGroupNames(matcher)) {
                pathParams.put(name, matcher.group(name));
            }
        } else {
            throw new IllegalArgumentException("URI does not match the template");
        }

        return pathParams;
    }

    private Set<String> getGroupNames(Matcher matcher) {
        Set<String> groupNames = new HashSet<>();
        Pattern groupNamePattern = Pattern.compile("\\?<([a-zA-Z][a-zA-Z0-9]*)>");
        Matcher groupNameMatcher = groupNamePattern.matcher(matcher.pattern().toString());

        while (groupNameMatcher.find()) {
            groupNames.add(groupNameMatcher.group(1));
        }

        return groupNames;
    }

    private <T> T convertToType(String value, Class<T> type) {
        if (type == String.class) {
            return type.cast(value);
        } else if (type == Integer.class || type == int.class) {
            return type.cast(Integer.parseInt(value));
        } else if (type == Long.class || type == long.class) {
            return type.cast(Long.parseLong(value));
        } else if (type == Double.class || type == double.class) {
            return type.cast(Double.parseDouble(value));
        } else if (type == UUID.class) {
            return type.cast(UUID.fromString(value));
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }
}
