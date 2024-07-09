package org.simplecrud.service;

import org.simplecrud.repository.TagDaoImpl;
import org.simplecrud.service.model.Question;
import org.simplecrud.service.model.Tag;

public class TagServiceImpl {
    private final TagDaoImpl tagDao = new TagDaoImpl();

    public long addTag(Question question, Tag tag) {
        return tagDao.save(question.getId(), tag.getId());
    }
}
