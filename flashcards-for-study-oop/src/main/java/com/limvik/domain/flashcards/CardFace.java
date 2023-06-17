package com.limvik.domain.flashcards;

/**
 * 이 클래스는 플래시카드(Flashcard)에 포함되는 내용이 저장되는 객체를 정의합니다.
 * 플래시카드의 한쪽 면에 표시될 콘텐츠가 저장 됩니다.
 */
public class CardFace {

    private String contents;

    CardFace(String contents) {
        this.contents = contents;
    }

    /**
     * 저장 가능한 콘텐츠일 경우 콘텐츠를 저장하고 true를 반환합니다.
     * @param contents 콘텐츠 내용
     * @return 저장 가능한 콘텐츠일 경우 true 그렇지 않을 경우 false
     */
    public boolean setContents(String contents) {
        if (contents != null) {
            this.contents = contents;
            return true;
        }
        return false;
    }

    /**
     * 저장되어 있는 콘텐츠를 불러옵니다.
     * @return 저장된 콘텐츠 문자열(String) 반환
     */
    public String getContents() {
        return contents;
    }

    /**
     * 콘텐츠가 저장되어 있지 않은 경우 true를 반환합니다.
     * @return 콘텐츠가 저장되어 있지 않은 경우 true 그렇지 않을 경우 false
     */
    public boolean isEmpty() {
        return contents == null || contents.length() == 0;
    }
}
