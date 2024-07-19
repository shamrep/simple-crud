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
import org.simplecrud.service.model.Question;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateQuestionHandlerTest {
    @Mock
    private QuestionService mockQuestionService;

    @Mock
    private Request mockRequest;

    @InjectMocks
    private UpdateQuestionHandler updateQuestionHandler;

    @Test
    public void handle_updateQuestion_success() {
        // given
        long questionId = 1L;
        QuestionDto questionDto = new QuestionDto(questionId, "Updated content", Collections.emptyList(), Collections.emptyList());
        Question question = new Question(questionId, "Updated content", Collections.emptyList(), Collections.emptyList());

        when(mockRequest.getPathParameter("id")).thenReturn(questionId);
        when(mockRequest.getBody(QuestionDto.class)).thenReturn(questionDto);
        when(mockQuestionService.get(questionId)).thenReturn(Optional.of(question));
        doNothing().when(mockQuestionService).update(any(Question.class));

        // when
        Response response = updateQuestionHandler.handle(mockRequest);

        // then
        assertEquals(204, response.getStatusCode());  // No Content
        verify(mockQuestionService, times(1)).update(any(Question.class));
    }

    @Test
    public void handle_updateQuestion_notFound() {
        // given
        long questionId = 1L;
        QuestionDto questionDto = new QuestionDto(questionId, "Updated content", Collections.emptyList(), Collections.emptyList());

        when(mockRequest.getPathParameter("id")).thenReturn(questionId);
        when(mockRequest.getBody(QuestionDto.class)).thenReturn(questionDto);
        when(mockQuestionService.get(questionId)).thenReturn(Optional.empty());

        // when
        Response response = updateQuestionHandler.handle(mockRequest);

        // then
        assertEquals(404, response.getStatusCode());  // Not Found
        verify(mockQuestionService, never()).update(any(Question.class));
    }
}
