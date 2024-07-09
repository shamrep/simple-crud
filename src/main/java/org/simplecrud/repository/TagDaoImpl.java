package org.simplecrud.repository;

import org.simplecrud.repository.entity.TagEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TagDaoImpl implements Dao<TagEntity> {

    private final DataSource dataSource;

    public TagDaoImpl() {
        this.dataSource = DataSourceManager.getDataSource();
    }

    public List<TagEntity> getTagsByQuestionId(long questionId) {
        List<TagEntity> tagEntities = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT tag.id, tag.name FROM tag JOIN question_tag ON tag.id = question_tag.tag_id WHERE question_id = ?")) {
            preparedStatement.setLong(1, questionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tagEntities.add(new TagEntity(resultSet.getLong("id"), resultSet.getString("name")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tagEntities;
    }

    public Optional<TagEntity> get(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM tag WHERE id = ?")) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    TagEntity entity = new TagEntity(
                            resultSet.getLong("id"),
                            resultSet.getString("name"));

                    return Optional.of(entity);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<TagEntity> getAll() {
        return List.of();
    }

    @Override
    public long save(TagEntity tagEntity) {
        return 0;
    }

    public long save(long questionId, long tagId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO question_tag VALUES(?,?);",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, questionId);
            preparedStatement.setLong(2, tagId);

            long affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong("id");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return -1;
    }

    @Override
    public void update(TagEntity tagEntity, String[] params) {

    }

    @Override
    public void delete(TagEntity tagEntity) {

    }
}
