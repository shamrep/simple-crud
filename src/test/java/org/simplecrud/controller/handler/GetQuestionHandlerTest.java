package org.simplecrud.controller.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.model.Answer;
import org.simplecrud.service.model.Question;
import org.simplecrud.service.model.Tag;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetQuestionHandlerTest {

    @Mock
    private QuestionService mockQuestionService;

    @Mock
    private Request mockRequest;

    @InjectMocks
    private GetQuestionHandler getQuestionHandler;

    @Test
    public void handle_questionFound_responseOk() {
        // given
        long questionId = 1L;
        Question mockQuestion = new Question(
                questionId,
                "Sample question",
                List.of(new Answer(1L, "answer1", true),
                        new Answer(2L, "answer2", false)),
                List.of(new Tag(1L, "tag1")));

        when(mockQuestionService.get(anyLong())).thenReturn(Optional.of(mockQuestion));
        when(mockRequest.getPathParameter("id")).thenReturn(questionId);

        // when
        Response response = getQuestionHandler.handle(mockRequest);

        // then
        assertEquals(200, response.getStatusCode());
        QuestionDto expectedQuestionDto = QuestionDto.of(mockQuestion);

        assertEquals(expectedQuestionDto, response.getBody());
    }

    @Test
    public void handle_questionNotFound_notFound() {
        // given
        long questionId = 1L;
        when(mockQuestionService.get(questionId)).thenReturn(Optional.empty());
        when(mockRequest.getPathParameter("id")).thenReturn(questionId);

        // when
        Response response = getQuestionHandler.handle(mockRequest);

        // then
        assertEquals(404, response.getStatusCode());
    }

}
