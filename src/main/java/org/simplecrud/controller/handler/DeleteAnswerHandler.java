package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.service.AnswerServiceImpl;
import org.simplecrud.service.Service;
import org.simplecrud.service.model.Answer;

public class DeleteAnswerHandler implements Handler {
    private final Service<Answer> answerService = new AnswerServiceImpl();

    @Override
    public Response handle(Request request) {
        long answerId = request.getPathParameter("id", Long.class);

        try {
            answerService.get(answerId);
            return Response.noContent();
        } catch (RuntimeException e) {
            return Response.internalServerError(e);
        }
    }
}
