package com.luna.db.providers;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionProvider {
    private final DAOFactory driverManager;
    protected Connection connection;

    public ConnectionProvider( DAOFactory driverManager ){
        this.driverManager = driverManager;
    }

    public Connection getConnection() throws SQLException {

        connection = driverManager.getConnection();
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}
