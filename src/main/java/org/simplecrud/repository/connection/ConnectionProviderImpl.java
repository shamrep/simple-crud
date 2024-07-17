package org.simplecrud.repository.connection;

import org.postgresql.ds.PGSimpleDataSource;
import org.simplecrud.repository.exception.DaoException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProviderImpl implements ConnectionProvider {

    private static final PGSimpleDataSource dataSource = new PGSimpleDataSource();
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        dataSource.setURL(properties.getProperty("db.url"));
        dataSource.setUser(properties.getProperty("db.username"));
        dataSource.setPassword(properties.getProperty("db.password"));
    }


    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DaoException("Couldn't get connection", e);
        }
    }
}
