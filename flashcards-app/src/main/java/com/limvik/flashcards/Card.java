package com.limvik.flashcards;

public class Card {

    private int id; // 카드 식별자
    private String front; // 카드 앞면 내용
    private String back; // 카드 뒷면 내용

    // 새로운 카드를 생성하라
    public Card(String front, String back) {
        this.front = front;
        this.back = back;
     }
    
    // 학습내용을 기록하라
    public boolean writeContents() {
        // 데이터베이스에 기록
    }
    
    // 카드 정보를 찾아라
    public int getId() {
        return id;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }
    
    // 카드 앞면 내용 설정
    public void setFront(String front) {
        this.front = front;
    }
    
    // 카드 뒷면 내용 설정
    public void setBack(String back) {
        this.back = back;
    }

}
