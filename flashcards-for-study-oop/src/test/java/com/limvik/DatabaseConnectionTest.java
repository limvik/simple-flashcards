package com.limvik;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.limvik.repository.DatabaseConnection;

public class DatabaseConnectionTest {
    private DatabaseConnection databaseConnection;

    @BeforeEach
    public void setup() throws SQLException {
        databaseConnection = DatabaseConnection.getInstance();
    }

    @AfterEach
    void tearDown() {
        databaseConnection.closeConnection();
    }

    @Test
    void testConnectionNotNull() {
        Connection connection = databaseConnection.getConnection();
        assertNotNull(connection, "Connection should not be null");
    }

    @Test
    void testConnectionIsOpen() throws SQLException {
        Connection connection = databaseConnection.getConnection();
        assertFalse(connection.isClosed(), "Connection should be open");
    }

    @Test
    void testConnectionIsClosed() throws SQLException {
        databaseConnection.closeConnection();
        Connection connection = databaseConnection.getConnection();
        assertTrue(connection.isClosed(), "Connection should be closed");
    }
}
