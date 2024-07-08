package org.simplecrud.service;

import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.model.Question;

import java.util.Optional;

public interface QuestionService {
    Optional<Question> findQuestionById(long questionId);
    QuestionDto toQuestionDto(Question question);
}
