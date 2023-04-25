package com.limvik.flashcards;

import java.util.List;

public class Deck {

    private int id; // 보관함 식별자
    private int userId; // 보관함 사용자 식별자
    private String name; // 보관함 이름
    private String ancestryIds; // 조상 보관함 식별자

    // 보관함을 관리하라(생성)
    public Deck(int id, int userId, String name, String ancestryIds) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.ancestryIds = ancestryIds;
    }
    
    // // 보관함을 관리하라(이동)
    // public boolean changeParentDeck(int parentId) { }
    
    // // 보관함을 관리하라(삭제)
    // public boolean removeDeck() { }
    
    // // 카드를 분류하라
    // public boolean classifyCards(int cardId, int to) { }
    
    // // 카드를 찾아라(키워드 검색)
    // public List<Card> findCards(String keywords) { }
    // // 카드를 찾아라(플래너를 통해 card의 식별자를 알고 있는 경우)
    // public List<Card> findCards(List<Integer> cardIdList) { }
    
    // // 이름을 기록하라
    // public boolean modifyDeckName(int deckId, String name) {
    //     // 데이터베이스 수정
    // }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAncestryIds() {
        return ancestryIds;
    }

    public void setAncestryIds(String ancestryIds) {
        this.ancestryIds = ancestryIds;
    }

}
