package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.QuestionServiceImpl;
import org.simplecrud.service.model.Answer;
import org.simplecrud.service.model.Question;
import org.simplecrud.service.model.Tag;

import java.util.List;
import java.util.stream.Collectors;

public class CreateQuestionHandler implements Handler {

    private final QuestionService questionService;

    public CreateQuestionHandler() {
        this.questionService = new QuestionServiceImpl();
    }

    @Override
    public Response handle(Request request) {
        QuestionDto questionDto = request.getBody(QuestionDto.class);

        long questionId = questionService.save(toQuestion(questionDto));

        return Response.created("/questions/" + questionId);
    }

    private Question toQuestion(QuestionDto questionDto) {
        List<Answer> answers = questionDto.getAnswers().stream()
                .map(answerDto -> new Answer(
                        null,
                        answerDto.getContent(),
                        answerDto.isCorrect()))
                .toList();

        List<Tag> tags = questionDto.getTags().stream()
                .map(tagDto -> new Tag(
                        null,
                        tagDto.getName()))
                .toList();

        return new Question(null, questionDto.getContent(), answers, tags);
    }
}
