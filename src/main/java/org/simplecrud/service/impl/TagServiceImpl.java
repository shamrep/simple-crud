package org.simplecrud.service.impl;

import org.simplecrud.repository.TagDao;
import org.simplecrud.repository.impl.TagDaoImpl;
import org.simplecrud.repository.entity.TagEntity;
import org.simplecrud.service.TagService;
import org.simplecrud.service.model.Tag;

import java.util.Optional;

public class TagServiceImpl implements TagService {

    private final TagDao tagDao = new TagDaoImpl();

    @Override
    public Optional<Tag> get(long tagId) {
        return tagDao.get(tagId).map(Tag::of);
    }

    @Override
    public long save(Tag tag) {
        TagEntity tagEntity = toTagEntity(tag);
        return tagDao.save(tagEntity);
    }

    @Override
    public void update(Tag tag) {
        TagEntity tagEntity = toTagEntity(tag);
        tagDao.update(tagEntity);
    }

    @Override
    public void delete(long tagId) {
        tagDao.delete(tagId);
    }

    private TagEntity toTagEntity(Tag tag) {
        return new TagEntity(tag.getId(), tag.getName());
    }
}
