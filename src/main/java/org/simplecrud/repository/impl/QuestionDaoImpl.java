package org.simplecrud.repository.impl;

import org.simplecrud.repository.connection.ConnectionProvider;
import org.simplecrud.repository.connection.ConnectionProviderImpl;
import org.simplecrud.repository.exception.DaoException;
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

//    private final DataSource dataSource;
    private final ConnectionProvider connectionProvider;

//    public QuestionDaoImpl(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    public QuestionDaoImpl(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public QuestionDaoImpl() {
//        this.dataSource = DataSourceManager.getDataSource();
        this(new ConnectionProviderImpl());
    }

    @Override
    public void addTag(long questionId, long tagId) {
        String sql = "INSERT INTO question_tag(question_id, tag_id) VALUES(?,?);";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {

            ps.setLong(1, questionId);
            ps.setLong(2, tagId);

            long affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Could not add tag = " + tagId + " to question = " + questionId);
            }
        } catch (SQLException e) {
            throw new DaoException("Could not add tag = " + tagId + " to question = " + questionId, e);
        }
    }

    @Override
    public Optional<QuestionEntity> get(long questionId) {
        String sql = "SELECT * FROM question WHERE id = ?;";

        try (Connection connection = connectionProvider.getConnection();
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

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            return toQuestionEntities(rs);

        } catch (SQLException e) {
            throw new DaoException("Couldn't get all questions", e);
        }
    }

    @Override
    public long create(QuestionEntity questionEntity) {
        String sql = "INSERT INTO question (content) VALUES (?);";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {

            ps.setString(1, questionEntity.getContent());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Could not create question = " + questionEntity.getContent());
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new DaoException("Could not create question = " + questionEntity.getContent());
                }

                return generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Could not create question = " + questionEntity.getContent(), e);
        }
    }

    @Override
    public void update(QuestionEntity questionEntity) {
        String sql = "UPDATE question SET content = ? WHERE id = ?;";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, questionEntity.getContent());
            ps.setLong(2, questionEntity.getId());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DaoException("Could not update question with id = " + questionEntity.getId());
            }
        } catch (SQLException e) {
            throw new DaoException("Could not update question with id = " + questionEntity.getId(), e);
        }
    }

    @Override
    public void delete(long questionEntityId) {
        String sql = "DELETE FROM question WHERE id = ?;";

        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, questionEntityId);

            int rowDeleted = ps.executeUpdate();
            if (rowDeleted == 0) {
                throw new DaoException("Could not delete question by id = " + questionEntityId);
            }
        } catch (SQLException e) {
            throw new DaoException("Could not delete question by id = " + questionEntityId, e);
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
