package org.simplecrud.service;

import org.simplecrud.repository.AnswerDaoImpl;
import org.simplecrud.repository.Dao;
import org.simplecrud.repository.QuestionDaoImpl;
import org.simplecrud.repository.entity.AnswerEntity;
import org.simplecrud.repository.entity.QuestionEntity;
import org.simplecrud.service.model.Answer;

public class AnswerServiceImpl implements AnswerService {
    private final Dao<QuestionEntity> questionEntityDao;
    private final Dao<AnswerEntity> answerEntityDao;

    public AnswerServiceImpl() {
        this.questionEntityDao = new QuestionDaoImpl();
        this.answerEntityDao = new AnswerDaoImpl();
    }

    @Override
    public long save(Answer answer) {
        return 0;
    }
}
