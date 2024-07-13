package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.AnswerDto;
import org.simplecrud.service.AnswerServiceImpl;
import org.simplecrud.service.Service;
import org.simplecrud.service.model.Answer;

import java.util.Optional;

public class GetAnswerHandler implements Handler {
    private final Service<Answer> answerService = new AnswerServiceImpl();

    @Override
    public Response handle(Request request) {
        long answerId = request.getPathParameter("id", Long.class);
        Optional<Answer> answer = answerService.get(answerId);

        return answer.map(a -> Response.ok(AnswerDto.of(a))).orElse(Response.notFound());
    }
}
