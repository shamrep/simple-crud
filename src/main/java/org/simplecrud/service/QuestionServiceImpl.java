package org.simplecrud.service;

import org.simplecrud.repository.AnswerDaoImpl;
import org.simplecrud.repository.QuestionDaoImpl;
import org.simplecrud.repository.TagDaoImpl;
import org.simplecrud.repository.entity.AnswerEntity;
import org.simplecrud.repository.entity.QuestionEntity;
import org.simplecrud.repository.entity.TagEntity;
import org.simplecrud.service.model.Answer;
import org.simplecrud.service.model.Question;
import org.simplecrud.service.model.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionServiceImpl implements Service<Question> {

    private final AnswerDaoImpl answerDao = new AnswerDaoImpl();
    private final QuestionDaoImpl questionDao = new QuestionDaoImpl();
    private final TagDaoImpl tagDao = new TagDaoImpl();

    public Optional<Question> get(long questionId) {
        Optional<QuestionEntity> optionalQuestionEntity = questionDao.get(questionId);

        if (optionalQuestionEntity.isPresent()) {
            QuestionEntity questionEntity = optionalQuestionEntity.get();
            List<AnswerEntity> answerEntities = answerDao.findAnswersByQuestionId(questionId);
            List<TagEntity> tagEntities = tagDao.getTagsByQuestionId(questionId);

            return Optional.of(toQuestion(questionEntity, answerEntities, tagEntities));
        }

        return Optional.empty();
    }

    private Question toQuestion(QuestionEntity questionEntity, List<AnswerEntity> answerEntities, List<TagEntity> tagEntities) {
        List<Answer> answers = answerEntities.stream()
                .map(entity -> new Answer(entity.getId(), entity.getContent(), entity.isCorrect()))
                .toList();

        List<Tag> tags = tagEntities.stream()
                .map(tagEntity -> new Tag(tagEntity.getId(), tagEntity.getName()))
                .toList();

        return new Question(questionEntity.getId(), questionEntity.getContent(), answers, tags);
    }

    public long save(Question question) {
        long questionId = questionDao.save(new QuestionEntity(null, question.getContent()));

        question.getAnswers().stream()
                .map(answer -> new AnswerEntity(null, answer.getContent(), answer.isCorrect(), questionId))
                .forEach(answerEntity -> answerDao.save(answerEntity));
        question.getTags().stream().map(tag -> new TagEntity(tag.getId(),tag.getName())).forEach(tagEntity -> tagDao.save(tagEntity));
        question.getTags().stream()
                .forEach(tag -> questionDao.addTag(questionId, tag.getId()));

        return questionId;
    }

    public void update(Question question) {
       questionDao.update(new QuestionEntity(null, question.getContent()));
    }

    public void delete(Question question) {
        questionDao.deleteById(question.getId());
    }

    @Override
    public void delete(long id) {

    }

    public boolean deleteById(Long questionId) {
        return questionDao.deleteById(questionId);
    }

    public List<Question> getAll() {
        List<QuestionEntity> questionEntities = questionDao.getAll();
        List<Question> questions = new ArrayList<>();

        for (QuestionEntity entity : questionEntities) {
            List<Answer> answers = answerDao.findAnswersByQuestionId(entity.getId())
                    .stream()
                    .map(answerEntity -> new Answer(
                            answerEntity.getId(),
                            answerEntity.getContent(),
                            answerEntity.isCorrect()))
                    .toList();

            List<Tag> tags = tagDao.getTagsByQuestionId(entity.getId())
                    .stream()
                    .map(tagEntity -> new Tag(tagEntity.getId(), tagEntity.getName()))
                    .toList();

            questions.add(new Question(entity.getId(), entity.getContent(), answers, tags));
        }

        return questions;
    }
}
