package org.simplecrud.repository;

import org.simplecrud.repository.entity.QuestionEntity;

import java.util.List;
import java.util.Optional;

public interface QuestionDao {

    Optional<QuestionEntity> get(long questionId);

    List<QuestionEntity> getAll();

    long create(QuestionEntity questionEntity);

    void update(QuestionEntity questionEntity);

    void delete(long questionId);

    void addTag(long questionId, long tagId);
}
