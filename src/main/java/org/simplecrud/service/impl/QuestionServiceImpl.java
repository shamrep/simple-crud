package org.simplecrud.service.impl;

import org.simplecrud.repository.AnswerDao;
import org.simplecrud.repository.QuestionDao;
import org.simplecrud.repository.TagDao;
import org.simplecrud.repository.entity.AnswerEntity;
import org.simplecrud.repository.entity.QuestionEntity;
import org.simplecrud.repository.entity.TagEntity;
import org.simplecrud.repository.impl.AnswerDaoImpl;
import org.simplecrud.repository.impl.QuestionDaoImpl;
import org.simplecrud.repository.impl.TagDaoImpl;
import org.simplecrud.service.QuestionService;
import org.simplecrud.service.model.Answer;
import org.simplecrud.service.model.Question;
import org.simplecrud.service.model.Tag;
import org.simplecrud.service.validator.QuestionValidator;
import org.simplecrud.service.validator.QuestionValidatorImpl;
import org.simplecrud.service.validator.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionServiceImpl implements QuestionService {

    private final AnswerDao answerDao = new AnswerDaoImpl();
    private final QuestionDao questionDao = new QuestionDaoImpl();
    private final TagDao tagDao = new TagDaoImpl();
    private final QuestionValidator questionValidator = new QuestionValidatorImpl();

    @Override
    public Optional<Question> get(long questionId) {
        Optional<QuestionEntity> questionEntity = questionDao.get(questionId);

        if (questionEntity.isEmpty()) {
            return Optional.empty();
        }

        List<AnswerEntity> answerEntities = answerDao.getAnswersByQuestionId(questionId);
        List<TagEntity> tagEntities = tagDao.getTagsByQuestionId(questionId);

        Question question = toQuestion(questionEntity.get(), answerEntities, tagEntities);

        return Optional.of(question);
    }

    @Override
    public long save(Question question) {
        List<String> errors = questionValidator.validate(question);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        QuestionEntity questionEntity = new QuestionEntity(null, question.getContent());
        long questionId = questionDao.save(questionEntity);

        question.getAnswers().stream()
                .map(answer -> new AnswerEntity(null, answer.getContent(), answer.isCorrect(), questionId))
                .forEach(answerDao::save);

        question.getTags().forEach(tag -> questionDao.addTag(questionId, tag.getId()));

        return questionId;
    }

    @Override
    public void update(Question question) {
        List<String> errors = questionValidator.validate(question);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        QuestionEntity questionEntity = new QuestionEntity(null, question.getContent());
        questionDao.update(questionEntity);
    }

    @Override
    public void delete(long questionId) {
        questionDao.delete(questionId);
    }

    @Override
    public List<Question> getAll() {
        List<Question> questions = new ArrayList<>();

        for (QuestionEntity entity : questionDao.getAll()) {
            List<AnswerEntity> answerEntities = answerDao.getAnswersByQuestionId(entity.getId());
            List<TagEntity> tagEntities = tagDao.getTagsByQuestionId(entity.getId());

            Question question = toQuestion(entity, answerEntities, tagEntities);
            questions.add(question);
        }

        return questions;
    }

    private Question toQuestion(
            QuestionEntity questionEntity, List<AnswerEntity> answerEntities, List<TagEntity> tagEntities) {

        List<Answer> answers = answerEntities.stream().map(Answer::of).toList();
        List<Tag> tags = tagEntities.stream().map(Tag::of).toList();

        return new Question(questionEntity.getId(), questionEntity.getContent(), answers, tags);
    }
}
