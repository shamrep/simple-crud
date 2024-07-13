package org.simplecrud.repository;

import org.simplecrud.repository.entity.AnswerEntity;

import java.util.List;
import java.util.Optional;

public interface AnswerDao {

    Optional<AnswerEntity> get(long answerId);

    List<AnswerEntity> getAnswersByQuestionId(long questionId);

    long save(AnswerEntity answerEntity);

    void update(AnswerEntity answerEntity);

    void delete(long answerId);
}
