package org.simplecrud.repository;

import org.simplecrud.entity.Question;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class QuestionDaoImpl implements Dao<Question> {

    private final DataSource dataSource;

    public QuestionDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    @Override
    public Optional<Question> get(long id) {
        Question question = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM question WHERE id = ?;")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    question = new Question(resultSet.getLong("id"), resultSet.getString("text"));
                    return Optional.of(question);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Question> getAll() {
        return List.of();
    }

    @Override
    public void save(Question question) {

    }

    @Override
    public void update(Question question, String[] params) {

    }

    @Override
    public void delete(Question question) {

    }
}
