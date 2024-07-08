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

public class AnswerDaoImpl  {

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
                    answers.add(new AnswerEntity(resultSet.getLong("id"), resultSet.getString("content"), resultSet.getBoolean("is_correct")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return answers;
    }
}
