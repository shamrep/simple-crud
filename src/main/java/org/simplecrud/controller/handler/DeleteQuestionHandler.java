package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.impl.QuestionServiceImpl;

public class DeleteQuestionHandler implements Handler {

    private final QuestionService questionService;

    public DeleteQuestionHandler() {
        questionService = new QuestionServiceImpl();
    }

    @Override
    public Response handle(Request request) {
        long questionId = request.getPathParameter("id");
        questionService.delete(questionId);

        return Response.noContent();
    }
}
