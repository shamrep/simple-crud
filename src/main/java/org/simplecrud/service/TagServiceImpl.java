package org.simplecrud.service;

import org.simplecrud.repository.TagDaoImpl;
import org.simplecrud.repository.entity.TagEntity;
import org.simplecrud.service.model.Question;
import org.simplecrud.service.model.Tag;

import java.util.Optional;

public class TagServiceImpl implements Service<Tag> {
    private final TagDaoImpl tagDao = new TagDaoImpl();

    @Override
    public Optional<Tag> get(long tagId) {
        Optional<TagEntity> optionalTagEntity = tagDao.get(tagId);

        if(optionalTagEntity.isPresent()) {
            return Optional.of(toTag(optionalTagEntity.get()));
        }

        //or throw Exception?
        return  Optional.empty();
    }

    @Override
    public long save(Tag tag) {
        return tagDao.save(toTagEntity(tag));
    }

    @Override
    public boolean update(Tag tag) {
        return tagDao.update(toTagEntity(tag));
    }

    @Override
    public boolean delete(Tag tag) {
        return tagDao.delete(toTagEntity(tag));
    }

    private Tag toTag(TagEntity tagEntity) {
        return new Tag(tagEntity.getId(), tagEntity.getName());
    }

    private TagEntity toTagEntity(Tag tag) {
        return new TagEntity(tag.getId(), tag.getName());
    }

    @Override
    public boolean delete(long tagId) {
        return tagDao.delete(tagId);
    }
}
