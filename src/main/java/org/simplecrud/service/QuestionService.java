package org.simplecrud.service;

import org.simplecrud.service.model.Question;

import java.util.Optional;

public interface QuestionService {

    Optional<Question> findQuestionById(long questionId);
}
