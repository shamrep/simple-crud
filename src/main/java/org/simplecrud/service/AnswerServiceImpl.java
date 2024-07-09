package org.simplecrud.service;

import org.simplecrud.repository.AnswerDaoImpl;
import org.simplecrud.repository.Dao;
import org.simplecrud.repository.QuestionDaoImpl;
import org.simplecrud.repository.entity.AnswerEntity;
import org.simplecrud.repository.entity.QuestionEntity;
import org.simplecrud.service.model.Answer;
import org.simplecrud.service.model.Question;
import org.simplecrud.service.model.Tag;

public class AnswerServiceImpl implements AnswerService {
    private Dao<QuestionEntity> questionEntityDao;
    private Dao<AnswerEntity> answerEntityDao;

    public AnswerServiceImpl() {
        this.questionEntityDao = new QuestionDaoImpl();
        this.answerEntityDao = new AnswerDaoImpl();
    }

    @Override
    public long save(Question question) {
        return 0;
    }
}
