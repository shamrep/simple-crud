package org.simplecrud.service.validator;

import org.simplecrud.service.model.Question;

import java.util.List;

public interface QuestionValidator {

    List<String> validate(Question question);
}
