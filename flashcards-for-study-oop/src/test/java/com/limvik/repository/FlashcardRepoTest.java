package com.limvik.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.limvik.repository.entity.FlashcardEntity;
import java.util.List;

public class FlashcardRepoTest {

    private static DatabaseConnection databaseConnection;
    private static FlashcardRepo flashcardRepo;

    private static final long[] id = {1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L};
    private static final String[] front = {"front1", "front2", "front3", "front4", "front5", "front6", "front7", "front8", "front9", "front10"};
    private static final String[] back = {"back1", "back2", "back3", "back4", "back5", "back6", "back7", "back8", "back9", "back10"};

    @BeforeAll
    public static void setup() throws SQLException {
        databaseConnection = DatabaseConnection.getInstance();
        flashcardRepo = new FlashcardRepo();
    }

    @AfterEach
    public void cleanup() throws SQLException {
        String sql = "DELETE FROM flashcards";
        databaseConnection.getConnection().prepareStatement(sql).executeUpdate();
        sql = "DELETE FROM sqlite_sequence WHERE name='flashcards'";
        databaseConnection.getConnection().prepareStatement(sql).executeUpdate();
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        String sql = "VACUUM";
        databaseConnection.getConnection().prepareStatement(sql).executeUpdate();
        databaseConnection.closeConnection();
    }

    @Test
    @DisplayName("플래시카드 데이터 삽입 테스트")
    public void createFlashcard() {
        assertTrue(flashcardRepo.createFlashcard(new FlashcardEntity(id[0], front[0], back[0])),
                                                 () -> "플래시카드 삽입에 성공해야 하지만 실패");
    }

    @Test
    @DisplayName("플래시카드 데이터 수정 테스트")
    public void updateFlashcard() {
        for (int i = 0; i < id.length; i++) {
            flashcardRepo.createFlashcard(new FlashcardEntity(id[i], front[i], back[i]));
        }

        int targetId = 5;
        String newFrontContents = "newFrontContents";
        String newBackContents = "newBackContents";

        assertTrue(flashcardRepo.updateFlashcard(new FlashcardEntity(targetId, newFrontContents, newBackContents)),
                                                 () -> "플래시카드 수정에 성공해야 하지만 실패");
    }

    @Test
    @DisplayName("플래시카드 데이터 삭제 테스트")
    public void deleteFlashcardById() {
        flashcardRepo.createFlashcard(new FlashcardEntity(id[0], front[0], back[0]));

        assertTrue(flashcardRepo.deleteFlashcardById(id[0]), () -> "플래시카드 삭제에 성공해야 하지만 실패");
        assertFalse(flashcardRepo.deleteFlashcardById(id[0]), () -> "플래시카드 삭제에 실패해야 하지만 성공");
    }

    @Test
    @DisplayName("플래시카드 데이터 전체 조회 테스트")
    public void getAllFlashcards() {
        for (int i = 0; i < id.length; i++) {
            flashcardRepo.createFlashcard(new FlashcardEntity(id[i], front[i], back[i]));
        }

        List<FlashcardEntity> flashcardEntities = flashcardRepo.getAllFlashcards();

        assertEquals(id.length, flashcardEntities.size(), () -> "반환 받은 FlashcardEntity의 갯수가 저장한 데이터의 갯수와 같지 않음");
        
    }
}
