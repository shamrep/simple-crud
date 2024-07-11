package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.QuestionServiceImpl;

public class DeleteQuestionHandler implements QuestionHandler {
    private final QuestionService questionService;

    public DeleteQuestionHandler() {
        questionService = new QuestionServiceImpl();
    }

    @Override
    public Response handle(Request request) {

        long questionId = request.getPathParameter("id", Long.class);
        questionService.deleteById(questionId);

        return Response.noContent();
    }
}
