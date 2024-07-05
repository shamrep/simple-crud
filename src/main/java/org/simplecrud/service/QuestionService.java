package org.simplecrud.service;

import org.simplecrud.service.model.Question;

public interface QuestionService {
    Question findQuestionById(long questionId);
}
