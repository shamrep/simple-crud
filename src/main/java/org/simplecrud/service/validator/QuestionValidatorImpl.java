package org.simplecrud.service.validator;

import org.simplecrud.service.model.Question;

import java.util.List;

public class QuestionValidatorImpl implements QuestionValidator {

    @Override
    public List<String> validate(Question question) {
        // TODO: add validation here
        return List.of();
    }
}