package com.limvik.domain.flashcards;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FlashcardTest {
    
    @Test
    @DisplayName("Flashcard 객체 생성 테스트")
    public void flashcardTest() {
        long testId = 1L;
        String testFront = "front";
        String testBack = "back";
        
        Flashcard flashcard = new Flashcard(testId,
        new CardFace(testFront),
        new CardFace(testBack));

        assertAll("정상적으로 객체가 생성되지 않았습니다.",
        () -> assertEquals(testId, flashcard.getId()),
        () -> assertEquals(testFront, flashcard.getFrontContents()),
        () -> assertEquals(testBack, flashcard.getBackContents()));
    }
}
