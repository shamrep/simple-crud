package org.simplecrud.controller;

import org.simplecrud.service.QuestionService;
import org.simplecrud.service.QuestionServiceImpl;
import org.simplecrud.service.model.Question;

public class GetQuestionAction implements Action{
    private QuestionService service;

    public GetQuestionAction() {
        service = new QuestionServiceImpl();
    }

    @Override
    public void process(ReqResp r) {

    }
}
