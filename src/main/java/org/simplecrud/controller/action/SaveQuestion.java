package org.simplecrud.controller.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.simplecrud.controller.Req;
import org.simplecrud.controller.Resp;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.QuestionServiceImpl;

import java.io.IOException;
import java.util.ArrayList;

public class SaveQuestion implements Action {
    private final QuestionService questionService;

    public SaveQuestion() {
        this.questionService = new QuestionServiceImpl();
    }

    @Override
    public void process(Req req, Resp resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleQuestion simpleQuestion = mapper.readValue(req.getRequest().getInputStream(), SimpleQuestion.class);



        long generatedId = questionService.save(questionService.toQuestion(new QuestionDto(-1, simpleQuestion.getContent(), new ArrayList<>(), new ArrayList<>())));

        if(generatedId >= 0) {
            resp.getResponse().setContentType("application/json");
            resp.getResponse().setCharacterEncoding("UTF-8");
            resp.getResponse().setStatus(HttpServletResponse.SC_CREATED);
            resp.getResponse().setHeader("Location", "/question/" + generatedId);
        }
        else {

        }
    }


}
