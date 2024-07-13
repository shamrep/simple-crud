package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.impl.QuestionServiceImpl;

public class CreateQuestionHandler implements Handler {

    private final QuestionService questionService;

    public CreateQuestionHandler() {
        this.questionService = new QuestionServiceImpl();
    }

    @Override
    public Response handle(Request request) {
        QuestionDto questionDto = request.getBody(QuestionDto.class);

        try {
            long questionId = questionService.save(questionDto.toQuestion());
            return Response.created("/questions/" + questionId);
        } catch (RuntimeException e) {
            return Response.internalServerError(e);
        }
    }
}
