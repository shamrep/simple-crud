package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.AnswerDto;
import org.simplecrud.service.AnswerServiceImpl;
import org.simplecrud.service.Service;
import org.simplecrud.service.model.Answer;

public class UpdateAnswerHandler implements Handler {
    private final Service<Answer> answerService = new AnswerServiceImpl();

    @Override
    public Response handle(Request request) {
        AnswerDto answerDto = request.getBody(AnswerDto.class);

        try {
            answerService.update(AnswerDto.toAnswer(answerDto));
            return Response.noContent();
        } catch (RuntimeException e) {
            return Response.notFound();
        }
    }
}
