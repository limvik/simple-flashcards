package com.limvik.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.limvik.flashcards.Deck;
import com.limvik.flashcards.User;

public class DeckDAO {

    private Connection connection;

    public DeckDAO(Connection connection) {
        this.connection = connection;
    } 

    public List<Deck> getDecksByUserId(User user, String ancestry) {

        // 반환할 보관함 목록 변수
        List<Deck> decks = new ArrayList<>();
        // 특정 사용자의 보관함 목록을 불러오기 위한 SQL
        String sql = "SELECT * FROM Decks WHERE user_id = ? AND ancestry LIKE ?";
        // SQL 실행 및 데이터 저장
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.setString(2, ancestry);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                decks.add(new Deck(resultSet.getInt("id"),
                                   resultSet.getInt("user_id"),
                                   resultSet.getString("name"),
                                   resultSet.getString("ancestry")));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return decks;
    }
    
    public Deck getDeckById(int id) {

        // 반환할 보관함 변수
        Deck deck = null;
        // 특정 사용자의 보관함 목록을 불러오기 위한 SQL
        String sql = "SELECT * FROM Decks WHERE id = ?";
        // SQL 실행 및 데이터 저장
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                deck = new Deck(resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getString("name"),
                                resultSet.getString("ancestry"));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deck;
    }

    public boolean isDuplicatedDeckName(User user, String deckName) {
        boolean isDuplicated = false;

        // 특정 사용자의 보관함 목록을 불러오기 위한 SQL
        String sql = "SELECT * FROM Decks WHERE user_id = ? AND name = ?";
        // SQL 실행 및 데이터 저장
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.setString(2, deckName);
            ResultSet resultSet = statement.executeQuery();

            isDuplicated = resultSet.next();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isDuplicated;
    }

    public int getLastDeckId() {

        // 마지막 보관함 데이터의 id를 얻기 위한 sql
        String sql = "SELECT id FROM Decks ORDER BY id DESC LIMIT 1";
        // SQL 실행 및 데이터 저장
        int id = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            id = resultSet.getInt("id");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (id == 0) id = 1;
        return id;
    }

    public int insertDeck(Deck deck) {

        // 보관함 생성을 위한 SQL
        String sql = "INSERT INTO Decks (id, user_id, name, ancestry) VALUES (?, ?, ?, ?)";
        int rowCount = 0;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, deck.getId());
            statement.setInt(2, deck.getUserId());
            statement.setString(3, deck.getName());
            statement.setString(4, deck.getAncestryIds());
            rowCount = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return rowCount;
    }

    public int deleteDeck(Deck deck) {
        
        // 보관함 삭제를 위한 SQL
        String sql = "DELETE FROM Decks WHERE id = ?";
        int rowCount = 0;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, deck.getId());
            rowCount = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return rowCount;
    }

    public int updateDeck(Deck deck) {

        // 보관함 이름 변경을 위한 SQL
        String sql = "UPDATE Decks SET name = ? WHERE id = ?";
        int rowCount = 0;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, deck.getName());
            statement.setInt(2, deck.getId());
            rowCount = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return rowCount;
    }
}
