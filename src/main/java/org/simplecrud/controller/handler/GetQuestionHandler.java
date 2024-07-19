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
        this(new QuestionServiceImpl());
    }

    public GetQuestionHandler(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Response handle(Request request) {
        long questionId = request.getPathParameter("id");
        Optional<Question> question = questionService.get(questionId);

        return question
                .map(q -> Response.ok(QuestionDto.of(q)))
                .orElse(Response.notFound());
    }
}
