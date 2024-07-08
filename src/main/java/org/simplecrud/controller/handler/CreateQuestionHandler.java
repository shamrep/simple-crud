package org.simplecrud.controller.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.simplecrud.controller.Request;
import org.simplecrud.controller.Response;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.QuestionServiceImpl;

import java.io.IOException;
import java.util.ArrayList;

public class CreateQuestionHandler implements Handler {

    private final QuestionService questionService;

    public CreateQuestionHandler() {
        this.questionService = new QuestionServiceImpl();
    }

    @Override
    public Response handle(Request request) {
        return Response.created("/questions/123");
//        ObjectMapper mapper = new ObjectMapper();
//        SimpleQuestion simpleQuestion = mapper.readValue(request.getRequest().getInputStream(), SimpleQuestion.class);
//
//
//
//        long generatedId = questionService.save(questionService.toQuestion(new QuestionDto(-1, simpleQuestion.getContent(), new ArrayList<>(), new ArrayList<>())));
//
//        if(generatedId >= 0) {
//            resp.getResponse().setContentType("application/json");
//            resp.getResponse().setCharacterEncoding("UTF-8");
//            resp.getResponse().setStatus(HttpServletResponse.SC_CREATED);
//            resp.getResponse().setHeader("Location", "/question/" + generatedId);
//        }
//        else {
//
//        }
    }


}
