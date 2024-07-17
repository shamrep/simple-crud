package org.simplecrud.repository.connection;

import java.sql.Connection;

public interface ConnectionProvider {

    Connection getConnection();
}
