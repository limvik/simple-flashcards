package com.limvik.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.limvik.repository.entity.FlashcardEntity;

public class FlashcardRepo {
    private Connection connection;

    FlashcardRepo() {
        try {
            connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 신규 플래시카드를 1개 저장하고, 성공 시 true를 반환합니다.
     * @param flashcardEntity 신규 플래시카드 콘텐츠가 저장된 Entity 객체
     * @return 신규 플래시카드 1개 저장 성공 시 true, 그렇지 않을 경우 false 반환
     */
    public boolean createFlashcard(FlashcardEntity flashcardEntity) {

        String sql = "INSERT INTO flashcards(front, back) VALUES (?, ?)";
        int rowCount = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, flashcardEntity.front());
            statement.setString(2, flashcardEntity.back());
            rowCount = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowCount == 1;

    }

    /**
     * 플래시카드를 수정하고, 성공 시 true를 반환합니다.
     * @param flashcardEntity 수정된 플래시카드 콘텐츠가 저장된 Entity 객체
     * @return 플래시카드 1개 수정 성공 시 true, 그렇지 않을 경우 false 반환
     */
    public boolean updateFlashcard(FlashcardEntity flashcardEntity) {

        String sql = "UPDATE flashcards SET front = ?, back = ? WHERE id = ?";
        int rowCount = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, flashcardEntity.front());
            statement.setString(2, flashcardEntity.back());
            statement.setLong(3, flashcardEntity.id());
            rowCount = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowCount == 1;

    }

    /**
     * 지정된 id를 가진 플래시카드를 삭제하고, 성공 시 true를 반환합니다.
     * @param id 삭제하려는 플래시카드의 id
     * @return 지정된 id를 갖는 플래시카드 삭제 성공 시 true, 그렇지 않을 경우 false 반환
     */
    public boolean deleteFlashcardById(long id) {

        String sql = "DELETE FROM flashcards WHERE id = ?";
        int rowCount = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            rowCount = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowCount == 1;

    }

    /**
     * 데이터베이스에 저장된 전체 플래시카드를 반환합니다.
     * @return 데이터베이스에 저장된 전체 플래시카드를 List로 반환합니다.
     */
    public List<FlashcardEntity> getAllFlashcards() {

        List<FlashcardEntity> flashcardEntities = new ArrayList<>();
        String sql = "SELECT * FROM flashcards";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flashcardEntities.add(new FlashcardEntity(resultSet.getLong("id"), resultSet.getString("front"), resultSet.getString("back")));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flashcardEntities;

    }

}
