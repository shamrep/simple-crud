package org.simplecrud.repository;

import org.simplecrud.entity.AnswerEntity;
import org.simplecrud.service.model.Answer;

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
                    answers.add(new AnswerEntity(resultSet.getLong("id"), resultSet.getString("text"), resultSet.getBoolean("is_correct")));
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
    public void save(AnswerEntity answerEntity) {

    }

    @Override
    public void update(AnswerEntity answerEntity, String[] params) {

    }

    @Override
    public void delete(AnswerEntity answerEntity) {

    }
}
