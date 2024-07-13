package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.impl.QuestionServiceImpl;
import org.simplecrud.service.model.Question;

import java.util.Optional;

public class GetQuestionHandler implements Handler {

    private final QuestionService questionService;

    public GetQuestionHandler() {
        questionService = new QuestionServiceImpl();
    }

    @Override
    public Response handle(Request request) {
        long questionId = request.getPathParameter("id", Long.class);
        Optional<Question> question = questionService.get(questionId);

        return question
                .map(q -> Response.ok(QuestionDto.of(q)))
                .orElse(Response.notFound());
    }
}
