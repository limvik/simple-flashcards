package com.limvik.repository;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

public class DatabaseConnection {
    private static DatabaseConnection databaseInstance;
    private Connection connection;

    private DatabaseConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        String dbPath = Path.of("src", "main", "resources", "db", "flashcards.db").toString();
        connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath, config.toProperties());
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (databaseInstance == null || databaseInstance.getConnection().isClosed()) {
            databaseInstance = new DatabaseConnection();
        } 

        return databaseInstance;
    
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
