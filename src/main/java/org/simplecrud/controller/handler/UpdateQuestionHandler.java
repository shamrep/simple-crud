package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.impl.QuestionServiceImpl;
import org.simplecrud.service.model.Question;

import java.util.Optional;

public class UpdateQuestionHandler implements Handler {
    private final QuestionService questionService;

    public UpdateQuestionHandler() {
        this.questionService = new QuestionServiceImpl();
    }

    @Override
    public Response handle(Request request) {
        long questionId = request.getPathParameter("id");
        QuestionDto questionDto = request.getBody(QuestionDto.class);

        Optional<Question> question = questionService.get(questionId);
        if (question.isEmpty()) {
            return Response.notFound();
        }

        questionService.update(questionDto.toQuestion());

        return Response.noContent();
    }
}
