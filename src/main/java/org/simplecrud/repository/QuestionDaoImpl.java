package org.simplecrud.repository;

import org.simplecrud.repository.entity.QuestionEntity;

import javax.sql.DataSource;
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

    @Override
    public Optional<QuestionEntity> get(long id) {
        QuestionEntity questionEntity = null;

        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement("SELECT * FROM question WHERE id = ?;")) {

            preparedStatement.setLong(1, id);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    questionEntity = new QuestionEntity(resultSet.getLong("id"), resultSet.getString("text"));
                    return Optional.of(questionEntity);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        var test = (Object) null;

        return Optional.empty();
    }

    @Override
    public List<QuestionEntity> getAll() {
        return List.of();
    }

    @Override
    public void save(QuestionEntity questionEntity) {

    }

    @Override
    public void update(QuestionEntity questionEntity, String[] params) {

    }

    @Override
    public void delete(QuestionEntity questionEntity) {

    }
}
