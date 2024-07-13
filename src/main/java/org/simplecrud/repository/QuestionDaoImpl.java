package org.simplecrud.repository;

import org.simplecrud.repository.entity.QuestionEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionDaoImpl implements Dao<QuestionEntity> {

    private final DataSource dataSource;

    public QuestionDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public QuestionDaoImpl() {
        this.dataSource = DataSourceManager.getDataSource();
    }

    public long addTag(long questionId, long tagId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO question_tag(question_id, tag_id) VALUES(?,?);",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, questionId);
            preparedStatement.setLong(2, tagId);

            long affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Could not add tag with id = " + tagId + " to a question with id = " + questionId);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong("id");
                } else {
                    throw new RuntimeException("Could not add tag with id = " + tagId + " to a question with id = " + questionId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<QuestionEntity> get(long id) {
        QuestionEntity questionEntity = null;

        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement("SELECT * FROM question WHERE id = ?;")) {

            preparedStatement.setLong(1, id);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    questionEntity = new QuestionEntity(resultSet.getLong("id"), resultSet.getString("content"));
                    return Optional.of(questionEntity);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<QuestionEntity> getAll() {
        List<QuestionEntity> questionEntities = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM question");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                questionEntities.add(new QuestionEntity(resultSet.getLong("id"), resultSet.getString("content")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return questionEntities;
    }

    @Override
    public long save(QuestionEntity questionEntity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO question (content) VALUES (?);",
                             PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, questionEntity.getContent());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Could not save question in database!");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new RuntimeException("Could not save question in database!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(QuestionEntity questionEntity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE question SET content = ?;")) {
            preparedStatement.setString(1, questionEntity.getContent());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated == 0) {
                throw new RuntimeException("Couldn't update question with id = " + questionEntity.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(QuestionEntity questionEntity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM question WHERE id = ?;")) {
            preparedStatement.setLong(1, questionEntity.getId());

            int rowDeleted = preparedStatement.executeUpdate();

            if (rowDeleted == 0) {
                throw new RuntimeException("Could not DELETE FROM question WHERE id = " + questionEntity.getId());
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM question WHERE id = ?;")) {
            preparedStatement.setLong(1, id);

            int rowDeleted = preparedStatement.executeUpdate();

            if (rowDeleted == 0) {
                throw new RuntimeException("Could not DELETE FROM question WHERE id = " + id);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
