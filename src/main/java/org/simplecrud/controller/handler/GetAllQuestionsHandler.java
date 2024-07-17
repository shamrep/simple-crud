package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.impl.QuestionServiceImpl;

import java.util.List;

public class GetAllQuestionsHandler implements Handler {

    private final QuestionService questionService;

    public GetAllQuestionsHandler() {
        this.questionService = new QuestionServiceImpl();
    }

    @Override
    public Response handle(Request request) {
        List<QuestionDto> questionDtos = questionService.getAll().stream().map(QuestionDto::of).toList();

        return Response.ok(questionDtos);
    }
}

