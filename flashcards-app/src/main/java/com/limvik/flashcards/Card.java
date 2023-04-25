package com.limvik.flashcards;

import java.sql.SQLException;

import com.limvik.dao.CardDAO;
import com.limvik.dao.DatabaseConnection;

public class Card {

    private int id; // 카드 식별자
    private String front; // 카드 앞면 내용
    private String back; // 카드 뒷면 내용
    private int cardType; // 카드 종류

    // 새로운 카드를 생성하라
    public Card(String front, String back) {
        this.front = front;
        this.back = back;
    }

    public Card(int id, String front, String back, int cardType) {
        this.id = id;
        this.front = front;
        this.back = back;
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
