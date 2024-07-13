package org.simplecrud.repository.impl;

import org.simplecrud.repository.AnswerDao;
import org.simplecrud.repository.exception.DaoException;
import org.simplecrud.repository.DataSourceManager;
import org.simplecrud.repository.entity.AnswerEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class AnswerDaoImpl implements AnswerDao {

    private final DataSource dataSource;

    public AnswerDaoImpl() {
        this.dataSource = DataSourceManager.getDataSource();
    }

    @Override
    public List<AnswerEntity> getAnswersByQuestionId(long questionId) {
        String sql = "SELECT * FROM answer WHERE question_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, questionId);

            try (ResultSet rs = ps.executeQuery()) {
                return toAnswerEntities(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Couldn't get answers by question id = " + questionId, e);
        }
    }

    @Override
    public Optional<AnswerEntity> get(long answerId) {
        String sql = "SELECT * FROM answer WHERE id = ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, answerId);

            try (ResultSet rs = ps.executeQuery()) {
                List<AnswerEntity> answers = toAnswerEntities(rs);

                if (answers.isEmpty()) {
                    return Optional.empty();
                }

                if (answers.size() > 1) {
                    throw new DaoException("There are several answers by id = " + answerId);
                }

                return Optional.of(answers.getFirst());
            }
        } catch (SQLException e) {
            throw new DaoException("Couldn't get answer by id = " + answerId, e);
        }
    }

    @Override
    public long create(AnswerEntity answerEntity) {
        String sql = "INSERT INTO answer(content, is_correct, question_id) VALUES(?,?, ?);";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {

            ps.setString(1, answerEntity.getContent());
            ps.setBoolean(2, answerEntity.isCorrect());
            ps.setLong(3, answerEntity.getQuestionId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Could not save answer = " + answerEntity.getContent());
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new DaoException("Could not save answer = " + answerEntity.getContent());
                }

                return generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Could not save answer = " + answerEntity.getContent(), e);
        }
    }

    @Override
    public void update(AnswerEntity answerEntity) {
        String sql = "UPDATE answer SET content = ?, is_correct = ?, question_id = ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, answerEntity.getContent());
            ps.setBoolean(2, answerEntity.isCorrect());
            ps.setLong(3, answerEntity.getQuestionId());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DaoException("Could not update answer with id = " + answerEntity.getId());
            }
        } catch (SQLException e) {
            throw new DaoException("Could not update answer with id = " + answerEntity.getId(), e);
        }
    }

    @Override
    public void delete(long answerId) {
        String sql = "DELETE FROM answer WHERE id = ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, answerId);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted == 0) {
                throw new DaoException("Could not delete answer by id = " + answerId);
            }
        } catch (SQLException e) {
            throw new DaoException("Could not delete answer by id = " + answerId, e);
        }
    }

    private List<AnswerEntity> toAnswerEntities(ResultSet rs) throws SQLException {
        List<AnswerEntity> answers = new ArrayList<>();
        while (rs.next()) {
            AnswerEntity answer = new AnswerEntity(
                    rs.getLong("id"),
                    rs.getString("content"),
                    rs.getBoolean("is_correct"),
                    rs.getLong("question_id"));

            answers.add(answer);
        }
        return answers;
    }
}
