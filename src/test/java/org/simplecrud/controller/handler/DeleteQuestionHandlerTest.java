package org.simplecrud.controller.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.service.QuestionService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteQuestionHandlerTest {
    @Mock
    private QuestionService mockQuestionService;

    @Mock
    private Request mockRequest;

    @InjectMocks
    private DeleteQuestionHandler deleteQuestionHandler;

    @Test
    public void handle_deleteQuestion_deleted() {
        //  given
        long questionId = 1L;
        when(mockRequest.getPathParameter("id")).thenReturn(questionId);
        doNothing().when(mockQuestionService).delete(questionId);

        // when
        Response response = deleteQuestionHandler.handle(mockRequest);

        // then
        assertEquals(204, response.getStatusCode());
        verify(mockQuestionService).delete(questionId);
    }
}
