package com.limvik;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.limvik.dao.TestDatabaseConnection;
import com.limvik.dao.UserDAO;
import com.limvik.flashcards.User;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDataCRUDTest {
    public static TestDatabaseConnection databaseConnection;
    public static UserDAO userDAO;

    // Test Data
    int[] userIds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    String[] names = {"John", "Jane", "Jack", "Jill", "Joe", "Jake", "Jessica", "Jennifer", "Jordan", "Josh"};
    String[] updateNames = {"Johnny", "Janey", "Jacque", "Jille", "Joo", "Jack", "Jessie", "Jennie", "Jordie", "Joshua"};

    @BeforeAll
    public static void setup() throws SQLException {

        databaseConnection = TestDatabaseConnection.getInstance();
        userDAO = new UserDAO(databaseConnection.getConnection());

    }

    @AfterAll
    public static void tearDown() throws SQLException {

        String sql = "DELETE FROM users";
        databaseConnection.getConnection().prepareStatement(sql).executeUpdate();
        sql = "VACUUM";
        databaseConnection.getConnection().prepareStatement(sql).executeUpdate();

        databaseConnection.closeConnection();
    }

    @Test
    @Order(1)
    void testInsertUser() {

        for (int i = 0; i < userIds.length; i++) {
            User user = new User(userIds[i], names[i]);
            int row = userDAO.insertUser(user);
            assertEquals(1, row, "Row should be 1");
        }

    }

    @Test
    @Order(2)
    void testSelectUsers() {

        List<User> users = userDAO.getAllUsers();
        assertEquals(userIds.length, users.size(), "Should be " + userIds.length);

        for (int i = 0; i < userIds.length; i++) {
            assertEquals(userIds[i], users.get(i).getId(), "Should be " + userIds[i]);
            assertEquals(names[i], users.get(i).getName(), "Should be " + names[i]);
        }

    }

    @Test
    @Order(3)
    void testUpdateUser() {

        List<User> users = userDAO.getAllUsers();
        assertEquals(userIds.length, users.size(), "Should be " + userIds.length);

        for (int i = 0; i < userIds.length; i++) {
            users.get(i).setName(updateNames[i]);
            int row = userDAO.updateUser(users.get(i));
            assertEquals(1, row, "Row should be 1");

            users = userDAO.getAllUsers();
            assertEquals(updateNames[i], users.get(i).getName(), "Should be " + updateNames[i]);
        }

    }

    @Test
    @Order(4)
    void testDeleteUser() {

        List<User> users = userDAO.getAllUsers();
        assertEquals(userIds.length, users.size(), "Should be " + userIds.length);

        for (int i = 0; i < userIds.length; i++) {
            int row = userDAO.deleteUser(users.get(i).getId());
            assertEquals(1, row, "Row should be 1");
            List<User> remainUsers = userDAO.getAllUsers();
            assertEquals(userIds.length - i - 1, remainUsers.size(), "Should be " + (userIds.length - i - 1));
        }
        
        users = userDAO.getAllUsers();
        assertEquals(0, users.size(), "Should be 0");
    }

}