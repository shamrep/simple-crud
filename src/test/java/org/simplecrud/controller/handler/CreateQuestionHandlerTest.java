package org.simplecrud.controller.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.AnswerDto;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.controller.dto.TagDto;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.model.Answer;
import org.simplecrud.service.model.Question;
import org.simplecrud.service.model.Tag;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateQuestionHandlerTest {

    @Mock
    private QuestionService mockQuestionService;

    @Mock
    private Request mockRequest;

    @InjectMocks
    private CreateQuestionHandler getQuestionHandler;

    @Test
    public void handle_createQuestion_created() {
        // given
        long questionId = 1L;
        QuestionDto questionDto = new QuestionDto(
                questionId,
                "Sample question",
                List.of(
                        new AnswerDto(1L, "answer1", true),
                        new AnswerDto(2L, "answer2", false)),
                List.of(new TagDto(1L, "tag1")));

        when(mockRequest.getBody(QuestionDto.class)).thenReturn(questionDto);
        when(mockQuestionService.create(any(Question.class))).thenReturn(questionId);

        // when
        Response response = getQuestionHandler.handle(mockRequest);

        // then
        assertEquals(201, response.getStatusCode());
        assertEquals("/questions/" + questionId, response.getHeaders().get("Location"));
    }
}
