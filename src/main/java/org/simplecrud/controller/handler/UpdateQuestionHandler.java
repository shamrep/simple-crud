package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.QuestionServiceImpl;
import org.simplecrud.service.Service;
import org.simplecrud.service.model.Question;

public class UpdateQuestionHandler implements QuestionHandler {
    private final Service<Question> questionService;

    public UpdateQuestionHandler() {
        this.questionService = new QuestionServiceImpl();
    }

    @Override
    public Response handle(Request request) {
        QuestionDto questionDto = request.getBody(QuestionDto.class);

        try {
            questionService.update(toQuestion(questionDto));
        } catch (RuntimeException e) {
            return Response.notFound();

        }

        return Response.noContent();
    }
}
