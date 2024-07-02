package org.simplecrud.dao;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class DataSourceManager {
    private static final PGSimpleDataSource dataSource = new PGSimpleDataSource();

    static {
        dataSource.setURL("jdbc:postgresql://localhost:5432/quizzes");
        dataSource.setUser("postgres");
        dataSource.setPassword("1234");
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
