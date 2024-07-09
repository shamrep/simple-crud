package org.simplecrud.repository;

import org.simplecrud.repository.entity.QuestionEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public boolean addTag(long questionId, long tagId) {
        try(var connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO question_tag VALUES (?, ?);")) {
            preparedStatement.setLong(1, questionId);
            preparedStatement.setLong(2, tagId);

            int rowAffected = preparedStatement.executeUpdate();

            if(rowAffected == 1) {
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
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
        return List.of();
    }

    @Override
    public long save(QuestionEntity questionEntity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO question (content) VALUES (?);",
                             PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, questionEntity.getContent());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return -1;
    }

    @Override
    public void update(QuestionEntity questionEntity, String[] params) {

    }

    @Override
    public void delete(QuestionEntity questionEntity) {

    }
}
