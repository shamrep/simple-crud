package org.simplecrud.repository;

import org.simplecrud.repository.entity.QuestionEntity;

import java.util.List;
import java.util.Optional;

public interface QuestionDao {

    Optional<QuestionEntity> get(long questionId);

    List<QuestionEntity> getAll();

    long save(QuestionEntity question);

    void update(QuestionEntity question);

    void delete(long questionId);

    long addTag(long questionId, long tagId);
}
