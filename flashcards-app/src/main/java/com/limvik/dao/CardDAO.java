package com.limvik.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.limvik.flashcards.Card;
import com.limvik.flashcards.Deck;

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
        String sql = "INSERT INTO Cards(id, user_id, deck_id, front, back, card_type) " +
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

    public int getDeckCardCount(int deckId) {

        // 보관함 내의 카드의 갯수를 얻기 위한 sql
        String sql = "SELECT count(*) AS count FROM Cards WHERE deck_id = ?";
        // SQL 실행 및 데이터 저장
        int count = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, deckId);
            ResultSet resultSet = statement.executeQuery();
            count = resultSet.getInt("count");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;

    }

    public List<Card> getCards(List<Deck> decks) {
        List<Card> cards = new ArrayList<>();

        // 보관함 내의 카드를 얻기 위한 sql
        String sql = "SELECT id, user_id, deck_id, front, back, memo, card_type FROM Cards " +
                     "WHERE deck_id IN (?";
        if (decks.size() > 1) sql += ", ?".repeat(decks.size() - 1);
        sql += ")";
        // SQL 실행 및 데이터 저장
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 1; i <= decks.size(); i++) {
                statement.setInt(i, decks.get(i-1).getId());
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cards.add(new Card(resultSet.getInt("id"),
                                   resultSet.getInt("user_id"),
                                   resultSet.getInt("deck_id"),
                                   resultSet.getString("front"),
                                   resultSet.getString("back"),
                                   resultSet.getString("memo"),
                                   resultSet.getInt("card_type")));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cards;
    }
}
