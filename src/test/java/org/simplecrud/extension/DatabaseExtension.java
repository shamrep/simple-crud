package org.simplecrud.extension;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseExtension implements BeforeAllCallback, AfterEachCallback {

    private final String tableName;
    private DataSource dataSource;

    public DatabaseExtension(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL("jdbc:postgresql://localhost:5432/quizzes_test");
        ds.setUser("postgres");
        ds.setPassword("1234");

        dataSource = ds;
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        execute("delete from " + tableName);
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(String sql) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }
}
