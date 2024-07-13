package org.simplecrud.service.validator;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {

    private final List<String> errors;

    public ValidationException(List<String> errors) {
        super("Validation errors: " + errors);
        this.errors = errors;
    }
}
