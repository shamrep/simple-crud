package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.impl.QuestionServiceImpl;
import org.simplecrud.service.model.Question;

import java.util.List;

public class GetAllQuestionsHandler implements Handler {

    private final QuestionService questionService;

    public GetAllQuestionsHandler() {
        this.questionService = new QuestionServiceImpl();
    }

    @Override
    public Response handle(Request request) {
        List<Question> questions = questionService.getAll();

        if (questions.isEmpty()) {
            return Response.notFound();
        } else {
            return Response.ok(questions.stream().map(q -> QuestionDto.of(q)).toList());
        }
    }
}
