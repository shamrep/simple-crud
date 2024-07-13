package org.simplecrud.service.validator;

import org.simplecrud.service.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionValidatorImpl implements QuestionValidator {

    @Override
    public List<String> validate(Question question) {
        List<String> errors = new ArrayList<>();

        if (question.getContent() == null || question.getContent().isBlank()) {
            errors.add("Question content must be blank.");
        }

        // TODO: add validation here
        return errors;
    }
}
