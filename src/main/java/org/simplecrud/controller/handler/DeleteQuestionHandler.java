package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.service.QuestionServiceImpl;
import org.simplecrud.service.Service;
import org.simplecrud.service.model.Question;

public class DeleteQuestionHandler implements QuestionHandler {
    private final Service<Question> questionService;

    public DeleteQuestionHandler() {
        questionService = new QuestionServiceImpl();
    }

    @Override
    public Response handle(Request request) {

        try {
            long questionId = request.getPathParameter("id", Long.class);
            questionService.get(questionId);
            return Response.noContent();
        } catch (RuntimeException e) {
            return Response.internalServerError(e);
        }


    }
}
