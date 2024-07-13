package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.QuestionServiceImpl;
import org.simplecrud.service.Service;
import org.simplecrud.service.model.Question;

public class CreateQuestionHandler implements QuestionHandler {

    private final Service<Question> questionService;

    public CreateQuestionHandler() {
        this.questionService = new QuestionServiceImpl();
    }

    @Override
    public Response handle(Request request) {
        QuestionDto questionDto = request.getBody(QuestionDto.class);

        long questionId = questionService.save(toQuestion(questionDto));

        return Response.created("/questions/" + questionId);
    }
}
