package org.simplecrud.controller.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.QuestionServiceImpl;
import org.simplecrud.service.model.Question;

import java.io.IOException;
import java.util.Optional;

public class GetQuestionHandler implements Handler {

    private final QuestionService service;

    public GetQuestionHandler() {
        service = new QuestionServiceImpl();
    }

    @Override
    public Response handle(Request request) {
        return Response.ok(new QuestionDto(1, "Test"));

//        String path = request.getRequest().getPathInfo();
//        String[] splitPath = path.split("/");
//        long id = Long.valueOf(splitPath[2]);
//
//        Optional<Question> optionalQuestion = service.findQuestionById(id);
//
//        try {
//            if (optionalQuestion.isPresent()) {
//                Question question = optionalQuestion.get();
//                QuestionDto questionDto = service.toQuestionDto(question);
//
//                ObjectMapper mapper = new ObjectMapper();
//
//                String json = mapper.writeValueAsString(questionDto);
//
//                resp.getResponse().getWriter().print(json);
//                resp.getResponse().setContentType("application/json");
//                resp.getResponse().setCharacterEncoding("UTF-8");
//                resp.getResponse().setStatus(HttpServletResponse.SC_OK);
//
//            } else {
//                Handler handler = new NotFoundHandler();
//                handler.process(request, resp);
//            }
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
