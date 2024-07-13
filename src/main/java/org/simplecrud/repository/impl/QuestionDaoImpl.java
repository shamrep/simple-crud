package org.simplecrud.repository.impl;

import org.simplecrud.repository.DaoException;
import org.simplecrud.repository.DataSourceManager;
import org.simplecrud.repository.QuestionDao;
import org.simplecrud.repository.entity.QuestionEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class QuestionDaoImpl implements QuestionDao {

    private final DataSource dataSource;

    public QuestionDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public QuestionDaoImpl() {
        this.dataSource = DataSourceManager.getDataSource();
    }

    @Override
    public long addTag(long questionId, long tagId) {
        String sql = "INSERT INTO question_tag(question_id, tag_id) VALUES(?,?);";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {

            ps.setLong(1, questionId);
            ps.setLong(2, tagId);

            long affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Could not add tag = " + tagId + " to a question = " + questionId);
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new DaoException("Could not add tag = " + tagId + " to a question = " + questionId);
                }

                return generatedKeys.getLong("id");
            }
        } catch (SQLException e) {
            throw new DaoException("Could not add tag = " + tagId + " to a question = " + questionId, e);
        }
    }

    @Override
    public Optional<QuestionEntity> get(long questionId) {
        String sql = "SELECT * FROM question WHERE id = ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, questionId);

            try (ResultSet rs = ps.executeQuery()) {
                List<QuestionEntity> questions = toQuestionEntities(rs);

                if (questions.isEmpty()) {
                    return Optional.empty();
                }

                if (questions.size() > 1) {
                    throw new DaoException("There are several questions by id = " + questionId);
                }

                return Optional.of(questions.getFirst());
            }
        } catch (SQLException e) {
            throw new DaoException("Couldn't get question by id = " + questionId, e);
        }
    }

    @Override
    public List<QuestionEntity> getAll() {
        String sql = "SELECT * FROM question";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            return toQuestionEntities(rs);

        } catch (SQLException e) {
            throw new DaoException("Couldn't get all questions", e);
        }
    }

    @Override
    public long save(QuestionEntity question) {
        String sql = "INSERT INTO question (content) VALUES (?);";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {

            ps.setString(1, question.getContent());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Could not create question = " + question.getContent());
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new DaoException("Could not create question = " + question.getContent());
                }

                return generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Could not create question = " + question.getContent(), e);
        }
    }

    @Override
    public void update(QuestionEntity question) {
        String sql = "UPDATE question SET content = ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, question.getContent());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DaoException("Couldn't update question with id = " + question.getId());
            }
        } catch (SQLException e) {
            throw new DaoException("Couldn't update question with id = " + question.getId(), e);
        }
    }

    @Override
    public void delete(long questionId) {
        String sql = "DELETE FROM question WHERE id = ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, questionId);

            int rowDeleted = ps.executeUpdate();
            if (rowDeleted == 0) {
                throw new DaoException("Could not delete question = " + questionId);
            }
        } catch (SQLException e) {
            throw new DaoException("Could not delete question = " + questionId, e);
        }
    }

    private List<QuestionEntity> toQuestionEntities(ResultSet rs) throws SQLException {
        List<QuestionEntity> questions = new ArrayList<>();
        while (rs.next()) {
            QuestionEntity question = new QuestionEntity(rs.getLong("id"), rs.getString("content"));
            questions.add(question);
        }
        return questions;
    }
}
