package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.AnswerDto;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.controller.dto.TagDto;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.QuestionServiceImpl;
import org.simplecrud.service.model.Question;

import java.util.List;
import java.util.Optional;

public class GetQuestionHandler implements Handler {

    private final QuestionService questionService;

    public GetQuestionHandler() {
        questionService = new QuestionServiceImpl();
    }

    @Override
    public Response handle(Request request) {
//        long questionId = request.getPathParameter("id", Long.class); // TODO: implement
        long questionId = 1;
        Optional<Question> question = questionService.findQuestionById(questionId);

        return question
                .map(q -> Response.ok(toQuestionDto(q)))
                .orElse(Response.notFound());
    }

    private static QuestionDto toQuestionDto(Question question) {
        List<TagDto> tagDtos = question.getTags()
                .stream()
                .map(t -> new TagDto(t.getId(), t.getName()))
                .toList();

        List<AnswerDto> answerDtos = question.getAnswers()
                .stream()
                .map(a -> new AnswerDto(a.getId(), a.getContent(), a.isCorrect()))
                .toList();

        return new QuestionDto(question.getId(), question.getContent(), answerDtos, tagDtos);
    }
}
