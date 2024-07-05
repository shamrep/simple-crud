package org.simplecrud.controller.action;

import jakarta.servlet.http.HttpServletRequest;
import org.simplecrud.controller.ReqResp;
import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.QuestionServiceImpl;
import org.simplecrud.service.model.Question;

public class GetQuestionAction implements Action {
    private QuestionService service;

    public GetQuestionAction() {
        service = new QuestionServiceImpl();
    }

    @Override
    public void process(ReqResp reqResp) {
        HttpServletRequest request = reqResp.getRequest();

        String path = request.getPathInfo();
        String[] splitPath = path.split("/");

        long id = Long.valueOf(splitPath[2]);

        Question question = service.findQuestionById(id);


    }

    private QuestionDto toQuestionDto(Question question) {
        return new QuestionDto(question.getId(), question.getContent());
    }
}
