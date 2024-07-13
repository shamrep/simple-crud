package org.simplecrud.repository.impl;

import org.simplecrud.repository.exception.DaoException;
import org.simplecrud.repository.DataSourceManager;
import org.simplecrud.repository.TagDao;
import org.simplecrud.repository.entity.TagEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class TagDaoImpl implements TagDao {

    private final DataSource dataSource;

    public TagDaoImpl() {
        this.dataSource = DataSourceManager.getDataSource();
    }

    @Override
    public List<TagEntity> getTagsByQuestionId(long questionId) {
        String sql = """
                SELECT tag.id, tag.name
                FROM tag
                JOIN question_tag ON (tag.id = question_tag.tag_id)
                WHERE question_id = ?""";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, questionId);

            try (ResultSet rs = ps.executeQuery()) {
                return toTagEntities(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Couldn't find tags by question id = " + questionId, e);
        }
    }

    @Override
    public Optional<TagEntity> get(long tagId) {
        String sql = "SELECT * FROM tag WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, tagId);

            try (ResultSet rs = ps.executeQuery()) {
                List<TagEntity> tagEntities = toTagEntities(rs);

                if (tagEntities.isEmpty()) {
                    return Optional.empty();
                }

                if (tagEntities.size() > 1) {
                    throw new DaoException("There are several tags by id = " + tagId);
                }

                return Optional.of(tagEntities.getFirst());
            }
        } catch (SQLException e) {
            throw new DaoException("Could not find tag by id = " + tagId, e);
        }
    }

    @Override
    public List<TagEntity> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM tag");
             ResultSet rs = ps.executeQuery()) {

            return toTagEntities(rs);

        } catch (SQLException e) {
            throw new DaoException("Couldn't get all tags", e);
        }
    }

    @Override
    public long save(TagEntity tagEntity) {
        String sql = "INSERT INTO tag(name) VALUES(?);";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {

            ps.setString(1, tagEntity.getName());

            long affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Could not create tag with name = " + tagEntity.getName());
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new DaoException("Could not create tag with name = " + tagEntity.getName());
                }

                return generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Could not create tag with name = " + tagEntity.getName(), e);
        }
    }

    @Override
    public void update(TagEntity tagEntity) {
        String sql = "UPDATE tag SET name = ? WHERE id = ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, tagEntity.getName());
            ps.setLong(2, tagEntity.getId());

            long affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Could not update tag with id = " + tagEntity.getId());
            }
        } catch (SQLException e) {
            throw new DaoException("Could not update tag with id = " + tagEntity.getId(), e);
        }
    }

    @Override
    public void delete(long tagEntityId) {
        String sql = "DELETE FROM tag WHERE id = ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, tagEntityId);

            long affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Couldn't delete tag by id = " + tagEntityId);
            }
        } catch (SQLException e) {
            throw new DaoException("Couldn't delete tag by id = " + tagEntityId);
        }
    }

    private List<TagEntity> toTagEntities(ResultSet rs) throws SQLException {
        List<TagEntity> tagEntities = new ArrayList<>();
        while (rs.next()) {
            TagEntity tagEntity = new TagEntity(rs.getLong("id"), rs.getString("name"));
            tagEntities.add(tagEntity);
        }
        return tagEntities;
    }
}

