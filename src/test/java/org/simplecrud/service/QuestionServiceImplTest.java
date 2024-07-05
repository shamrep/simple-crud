package org.simplecrud.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simplecrud.service.model.Answer;
import org.simplecrud.service.model.Question;
import org.simplecrud.service.model.Tag;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionServiceImplTest {
    private QuestionService  service;

    @BeforeEach
    void setUp() {
        service = new QuestionServiceImpl();
    }

    @Test
    public void findQuestion_questionId_question() {
        // given
        long questionId = 1l;

//        Question question = new Question(1,"", answers, tags);

        //when
        Question question = service.findQuestionById(questionId);

        //then
//        assertEquals();
    }

}
