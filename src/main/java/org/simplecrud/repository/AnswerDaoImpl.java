package org.simplecrud.repository;

import org.simplecrud.repository.entity.AnswerEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnswerDaoImpl implements Dao<AnswerEntity> {

    private final DataSource dataSource;

    public AnswerDaoImpl() {
        this.dataSource = DataSourceManager.getDataSource();
    }

    public List<AnswerEntity> findAnswersByQuestionId(long questionId) {
        List<AnswerEntity> answers = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM answer WHERE question_id = ?")
        ) {
            preparedStatement.setLong(1, questionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    answers.add(new AnswerEntity(resultSet.getLong("id"), resultSet.getString("content"), resultSet.getBoolean("is_correct"), resultSet.getLong("question_id")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return answers;
    }

    @Override
    public Optional<AnswerEntity> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<AnswerEntity> getAll() {
        return List.of();
    }

    @Override
    public long save(AnswerEntity answerEntity) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO answer(content, is_correct, question_id) VALUES(?,?,?);",
                            PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, answerEntity.getContent());
            preparedStatement.setBoolean(2, answerEntity.isCorrect());
            preparedStatement.setLong(3, answerEntity.getQuestionId());

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
    public boolean update(AnswerEntity answerEntity) {

        return false;
    }

    @Override
    public boolean delete(AnswerEntity answerEntity) {

        return false;
    }
}
