package com.limvik.dao;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

public class TestDatabaseConnection {
    private static TestDatabaseConnection instance;
    private Connection connection;

    private TestDatabaseConnection() throws SQLException { 
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        String dbPath = Path.of("src", "main", "resources", "db", "test.db").toString();
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath, config.toProperties());
    }

    public static TestDatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new TestDatabaseConnection();
        } 

        return instance;
    
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }    
    }
}
