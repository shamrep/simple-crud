package org.simplecrud.service;

import org.simplecrud.repository.AnswerDaoImpl;
import org.simplecrud.repository.Dao;
import org.simplecrud.repository.QuestionDaoImpl;
import org.simplecrud.repository.entity.AnswerEntity;
import org.simplecrud.repository.entity.QuestionEntity;
import org.simplecrud.service.model.Answer;

import java.util.Optional;

public class AnswerServiceImpl implements Service<Answer> {
    private final Dao<QuestionEntity> questionEntityDao;
    private final Dao<AnswerEntity> answerEntityDao;

    public AnswerServiceImpl() {
        this.questionEntityDao = new QuestionDaoImpl();
        this.answerEntityDao = new AnswerDaoImpl();
    }

    @Override
    public Optional<Answer> get(long id) {
        Optional<AnswerEntity> optionalAnswerEntity = answerEntityDao.get(id);
        AnswerEntity answerEntity = optionalAnswerEntity.get();

        return Optional.of(Answer.of(answerEntity));
    }

    @Override
    public long save(Answer answer) {
       return answerEntityDao.save(toAnswerEntity(answer));
    }

    @Override
    public void update(Answer answer) {
        answerEntityDao.update(toAnswerEntity(answer));
    }

    @Override
    public void delete(Answer answer) {
        answerEntityDao.delete(toAnswerEntity(answer));
    }

    @Override
    public void delete(long id) {
        answerEntityDao.delete(id);
    }

    private AnswerEntity toAnswerEntity(Answer answer) {
        return new AnswerEntity(answer.getId(), answer.getContent(), answer.isCorrect(), answer.getQuestionId());
    }
}
