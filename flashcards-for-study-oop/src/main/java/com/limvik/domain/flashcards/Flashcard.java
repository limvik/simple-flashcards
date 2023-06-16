package com.limvik.domain.flashcards;

/**
 * 이 클래스는 플래시카드(Flashcard) 객체를 정의합니다.
 */
class Flashcard {
    Long id;
    CardFace frontFace;
    CardFace backFace;

    Flashcard(Long id, CardFace frontFace, CardFace backFace) {
        this.id = id;
        this.frontFace = frontFace;
        this.backFace = backFace;
    }

    public Long getId() {
        return id;
    }

    public String getFrontFace() {
        return frontFace.getContents();
    }

    public String getBackFace() {
        return backFace.getContents();
    }
}