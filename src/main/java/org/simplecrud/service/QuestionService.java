package org.simplecrud.service;

import org.simplecrud.service.model.Question;

import java.util.Optional;

public interface QuestionService {
    long save(Question question);
    Optional<Question> findQuestionById(long questionId);
}
