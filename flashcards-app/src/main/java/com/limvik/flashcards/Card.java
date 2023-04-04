package com.limvik.flashcards;

public class Card {

    private int id; // 카드 식별자
    private String front; // 카드 앞면 내용
    private String back; // 카드 뒷면 내용
    
    // 카드를 생성하라
    public Card() { }
    
    // 학습내용을 기록하라
    public boolean writeContents(Card card) {
        // 데이터베이스에 기록
    }
    
    // 카드 정보를 찾아라
    public Card findCardContents(int cardId) { }
    
    // 카드 앞면 내용 설정
    public void setFront(String front) {
        this.front = front;
    }
    
    // 카드 뒷면 내용 설정
    public void setBack(String back) {
        this.back = back;
    }

}
