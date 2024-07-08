package org.simplecrud.service;

import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.repository.Dao;
import org.simplecrud.service.model.Question;

import java.util.Optional;

public interface QuestionService extends Service<Question> {
    Optional<Question> findQuestionById(long questionId);
    QuestionDto toQuestionDto(Question question);

    Question toQuestion(QuestionDto questionDto);
}
