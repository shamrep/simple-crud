package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.impl.QuestionServiceImpl;

public class UpdateQuestionHandler implements Handler {
    private final QuestionService questionService;

    public UpdateQuestionHandler() {
        this.questionService = new QuestionServiceImpl();
    }

    @Override
    public Response handle(Request request) {
        QuestionDto questionDto = request.getBody(QuestionDto.class);

        try {
            questionService.update(questionDto.toQuestion());
        } catch (RuntimeException e) {
            return Response.notFound();

        }

        return Response.noContent();
    }
}
