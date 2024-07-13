package org.simplecrud.service.validator;

import org.simplecrud.service.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagValidatorImpl implements TagValidator {

    @Override
    public List<String> validate(Tag tag) {
        List<String> errors = new ArrayList<>();

        validateName(errors, tag.getName());

        return errors;
    }

    private void validateName(List<String> errors, String tagName) {
        if (tagName == null || tagName.isBlank()) {
            errors.add("Tag name is blank.");
        }
    }
}
