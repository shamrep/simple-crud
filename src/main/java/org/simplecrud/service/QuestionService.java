package org.simplecrud.service;

import org.simplecrud.service.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    Optional<Question> get(long questionId);

    List<Question> getAll();

    long create(Question question);

    void update(Question question);

    void delete(long questionId);
}
