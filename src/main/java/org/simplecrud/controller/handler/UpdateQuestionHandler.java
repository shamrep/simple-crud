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
        this(new QuestionServiceImpl());
    }

    public UpdateQuestionHandler(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Response handle(Request request) {
        long questionId = request.getPathParameter("id");


        Optional<Question> question = questionService.get(questionId);
        if (question.isEmpty()) {
            return Response.notFound();
        }

        QuestionDto questionDto = request.getBody(QuestionDto.class);
        questionService.update(new Question(questionId, questionDto.getContent(), questionDto.toQuestion().getAnswers(), questionDto.toQuestion().getTags()));

        return Response.noContent();
    }
}
