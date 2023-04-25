package com.limvik.flashcards;

import java.sql.SQLException;

import com.limvik.dao.CardDAO;
import com.limvik.dao.DatabaseConnection;

public class Card {

    private int id; // 카드 식별자
    private int usreId; // 사용자 식별자
    private int deckId; // 보관함 식별자
    private String front; // 카드 앞면 내용
    private String back; // 카드 뒷면 내용
    private String memo; // 카드 추가 메모
    private int cardType; // 카드 종류

    // 새로운 카드를 생성하라
    public Card(String front, String back) {
        this.front = front;
        this.back = back;
    }

    public Card(int id, int userId, int deckId, String front, String back, String memo, int cardType) {
        this.id = id;
        this.usreId = userId;
        this.deckId = deckId;
        this.front = front;
        this.back = back;
        this.memo = memo;
        this.cardType = cardType;
    }
    
    // 학습내용을 보관하라
    public boolean writeContents(int userId, int deckId) {
        // 데이터베이스에 기록
        try {
            var cardDAO = new CardDAO(DatabaseConnection.getInstance().getConnection());
            return cardDAO.insertCard(userId, deckId, this) == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
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

    public int getCardType() {
        return cardType;
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
