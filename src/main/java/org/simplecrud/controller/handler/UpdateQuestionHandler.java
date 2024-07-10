package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.QuestionServiceImpl;

public class UpdateQuestionHandler implements QuestionHandler {
    private final QuestionService questionService;

    public UpdateQuestionHandler() {
        this.questionService = new QuestionServiceImpl();
    }

    @Override
    public Response handle(Request request) {
        QuestionDto questionDto = request.getBody(QuestionDto.class);

        boolean updated = questionService.update(toQuestion(questionDto));

        if (updated) {
            return Response.noContent();
        }

        return Response.notFound();
    }
}
