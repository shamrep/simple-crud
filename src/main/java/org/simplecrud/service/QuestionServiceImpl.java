package org.simplecrud.service;

import org.simplecrud.entity.AnswerEntity;
import org.simplecrud.entity.QuestionEntity;
import org.simplecrud.entity.TagEntity;
import org.simplecrud.repository.AnswerDaoImpl;
import org.simplecrud.repository.QuestionDaoImpl;
import org.simplecrud.repository.TagDaoImpl;
import org.simplecrud.service.model.Answer;
import org.simplecrud.service.model.Question;
import org.simplecrud.service.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class QuestionServiceImpl implements QuestionService {
    private final AnswerDaoImpl answerDao = new AnswerDaoImpl();
    private final QuestionDaoImpl questionDao = new QuestionDaoImpl();
    private final TagDaoImpl tagDao = new TagDaoImpl();

    public Question findQuestionById(long questionId) {
        List<AnswerEntity> answerEntities = answerDao.findAnswersByQuestionId(questionId);
        QuestionEntity questionEntity = questionDao.get(questionId).get();
        List<TagEntity> tagEntities = tagDao.getTagsByQuestionId(questionId);

        return toQuestion(questionEntity, answerEntities, tagEntities);
    }

    private Question toQuestion(QuestionEntity questionEntity, List<AnswerEntity> answerEntities, List<TagEntity> tagEntities) {
        List<Answer> answers = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();

        for (AnswerEntity entity : answerEntities) {
            answers.add(new Answer(entity.getId(), entity.getText(), entity.isCorrect()));
        }

        for (TagEntity entity : tagEntities) {
            tags.add(new Tag(entity.getId(), entity.getText()));
        }

        return new Question(questionEntity.getId(), questionEntity.getText(), answers, tags);
    }
}
