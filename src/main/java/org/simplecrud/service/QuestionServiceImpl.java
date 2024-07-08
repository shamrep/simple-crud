package org.simplecrud.service;

import org.simplecrud.controller.dto.QuestionDto;
import org.simplecrud.repository.entity.AnswerEntity;
import org.simplecrud.repository.entity.QuestionEntity;
import org.simplecrud.repository.entity.TagEntity;
import org.simplecrud.repository.AnswerDaoImpl;
import org.simplecrud.repository.QuestionDaoImpl;
import org.simplecrud.repository.TagDaoImpl;
import org.simplecrud.service.model.Answer;
import org.simplecrud.service.model.Question;
import org.simplecrud.service.model.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionServiceImpl implements QuestionService {
    private final AnswerDaoImpl answerDao = new AnswerDaoImpl();
    private final QuestionDaoImpl questionDao = new QuestionDaoImpl();
    private final TagDaoImpl tagDao = new TagDaoImpl();

    public Optional<Question> findQuestionById(long questionId) {
        Optional<QuestionEntity> optionalQuestionEntity = questionDao.get(questionId);

        if(optionalQuestionEntity.isPresent()) {
            QuestionEntity questionEntity = optionalQuestionEntity.get();
            List<AnswerEntity> answerEntities = answerDao.findAnswersByQuestionId(questionId);
            List<TagEntity> tagEntities = tagDao.getTagsByQuestionId(questionId);

            return Optional.of(toQuestion(questionEntity, answerEntities, tagEntities));
        }

       return  Optional.empty();
    }

    @Override
    public QuestionDto toQuestionDto(Question question) {
        return new QuestionDto(question.getId(), question.getContent(), question.getAnswers(), question.getTags());
    }

    private Question toQuestion(QuestionEntity questionEntity, List<AnswerEntity> answerEntities, List<TagEntity> tagEntities) {
        List<Answer> answers = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();

        for (AnswerEntity entity : answerEntities) {
            answers.add(new Answer(entity.getId(), entity.getContent(), entity.isCorrect()));
        }

        for (TagEntity entity : tagEntities) {
            tags.add(new Tag(entity.getId(), entity.getName()));
        }

        return new Question(questionEntity.getId(), questionEntity.getContent(), answers, tags);
    }

    private QuestionEntity toQuestionEntity(Question question) {
        return new QuestionEntity(question.getId(), question.getContent());
    }

    public Question toQuestion(QuestionDto questionDto) {
        return new Question(questionDto.getId(), questionDto.getContent(), new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public long save(Question question) {
        return questionDao.save(toQuestionEntity(question));
    }
}
