package org.simplecrud.controller.handler;

import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.AnswerDto;
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

        int correctAnswers = 0;
        int answerCounter = 0;

        for(AnswerDto answerDto : questionDto.getAnswers()) {
            if(answerDto.isCorrect()) {
                correctAnswers++;
            }

            answerCounter++;
        }

        if (correctAnswers == 0) {
            return Response.badRequest(new RuntimeException("There are no correct answers."));
        }

        if (answerCounter != 4) {
            return Response.badRequest(new RuntimeException("There are must be 4 answer to a question."));
        }

        try {
            long questionId = questionService.save(toQuestion(questionDto));
            return Response.created("/questions/" + questionId);
        } catch (RuntimeException e) {
            return Response.internalServerError(e);
        }
    }
}
