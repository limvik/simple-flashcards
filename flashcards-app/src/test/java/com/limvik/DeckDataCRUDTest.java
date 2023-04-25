package com.limvik;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.limvik.dao.DeckDAO;
import com.limvik.dao.TestDatabaseConnection;
import com.limvik.dao.UserDAO;
import com.limvik.flashcards.Deck;
import com.limvik.flashcards.User;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeckDataCRUDTest {
    public static TestDatabaseConnection databaseConnection;
    public static DeckDAO deckDAO;

    // User Test Data
    private static final int[] userIds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final String[] names = {"User 1", "User 2", "User 3", "User 4", "User 5", "User 6", "User 7", "User 8", "User 9", "User 10"};

    // Deck Test Data
    private static final int[] deckIds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final String[] deckNames = {"Deck 1", "Deck 2", "Deck 3", "Deck 4", "Deck 5", "Deck 6", "Deck 7", "Deck 8", "Deck 9", "Deck 10"};
    private static final String[] updatedDeckNames = {"Deck 1 Updated", "Deck 2 Updated", "Deck 3 Updated", "Deck 4 Updated", "Deck 5 Updated",
                                 "Deck 6 Updated", "Deck 7 Updated", "Deck 8 Updated", "Deck 9 Updated", "Deck 10 Updated"};

    @BeforeAll
    public static void setup() throws SQLException {

        databaseConnection = TestDatabaseConnection.getInstance();
        deckDAO = new DeckDAO(databaseConnection.getConnection());

        // 사용자 데이터 삽입
        var userDAO = new UserDAO(databaseConnection.getConnection());
        for (int i = 0; i < userIds.length; i++) {
            userDAO.insertUser(new User(userIds[i], names[i]));
        }

    }

    @AfterAll
    public static void tearDown() throws SQLException {

        String sql = "DELETE FROM users";
        databaseConnection.getConnection().prepareStatement(sql).executeUpdate();
        sql = "DELETE FROM decks";
        databaseConnection.getConnection().prepareStatement(sql).executeUpdate();
        sql = "VACUUM";
        databaseConnection.getConnection().prepareStatement(sql).executeUpdate();

        databaseConnection.closeConnection();
    }

    @Test
    @Order(1)
    void testInsertRootDeck() {

        // 사용자 최상위 보관함 삽입 테스트
        for (int i = 0; i < userIds.length; i++) {
            int row = deckDAO.insertDeck(new Deck(deckIds[i], userIds[i], "root", "0"));
            assertEquals(1, row, "Row should be 1");
        }

    }

    @Test
    @Order(2)
    void testInsertChildDeck() {
        
        for (int i = 0; i < userIds.length; i++) {

            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(deckIds[0]));

            for (int j = 0; j < deckIds.length; j++) {
                int deckId = deckDAO.getLastDeckId() + 1;
                int row = deckDAO.insertDeck(new Deck(deckId, userIds[i], deckNames[j], sb.toString()));
                // 하위 덱으로 만들기 위한 구분자
                sb.append("::");
                assertEquals(1, row, "Row should be 1");
                row = deckDAO.insertDeck(new Deck(0, 999, deckNames[j], sb.toString()));
                assertEquals(0, row, "Row should be 0");
            }

        }
    
    }
}
