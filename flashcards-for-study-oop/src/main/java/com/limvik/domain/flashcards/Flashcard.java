package com.limvik.domain.flashcards;

/**
 * 이 클래스는 플래시카드(Flashcard) 객체를 정의합니다.
 */
class Flashcard {
    private Long id;
    private CardFace frontFace;
    private CardFace backFace;

    Flashcard(Long id, CardFace frontFace, CardFace backFace) {
        this.id = id;
        this.frontFace = frontFace;
        this.backFace = backFace;
    }

    public Long getId() {
        return id;
    }

    /**
     * 플래시카드 앞면에 저장되어 있는 내용을 반환합니다.
     * @return 플래시카드 앞면에 저장되어 있는 내용 반환
     */
    public String getFrontContents() {
        return frontFace.getContents();
    }

    /**
     * 플래시카드 뒷면에 저장되어 있는 내용을 반환합니다.
     * @return 플래시카드 뒷면에 저장되어 있는 내용 반환
     */
    public String getBackContents() {
        return backFace.getContents();
    }
}