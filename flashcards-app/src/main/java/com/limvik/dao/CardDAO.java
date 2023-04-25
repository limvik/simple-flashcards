package com.limvik.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.limvik.flashcards.Card;

public class CardDAO {
    private Connection connection;

    public CardDAO(Connection connection) {
        this.connection = connection;
    }

    public int getLastCardId() {
        // 마지막 카드 데이터의 id를 얻기 위한 sql
        String sql = "SELECT id FROM Cards ORDER BY id DESC LIMIT 1";
        // SQL 실행 및 데이터 저장
        int id = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            id = resultSet.getInt("id");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int insertCard(int userId, int deckId, Card card) {

        // 신규 사용자 추가를 위한 SQL
        String sql = "INSERT INTO Cards(id, user_id, deck_id, front, back, card_type)" +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        int rowCount = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, getLastCardId() + 1);
            statement.setInt(2, userId);
            statement.setInt(3, deckId);
            statement.setString(4, card.getFront());
            statement.setString(5, card.getBack());
            statement.setInt(6, 1);
            rowCount = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowCount;

    }
}
