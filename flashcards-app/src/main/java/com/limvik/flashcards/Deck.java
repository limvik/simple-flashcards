package com.limvik.flashcards;

import java.util.List;

public class Deck {

    private int id; // 보관함 식별자
    private String name;
    private int parentId; // 부모 보관함 식별자
    private List<Integer> childIds; // 자식 보관함 식별자 목록
    private List<Integer> cardIds; // 포함한 카드 식별자 목록

    // 보관함을 관리하라(생성)
    public Deck(String name) { }
    
    // 보관함을 관리하라(수정)
    public boolean changeParentDeck() { }
    
    // 보관함을 관리하라(삭제)
    public boolean removeDeck(int deckId) { }
    
    // 카드를 분류하라
    public boolean classifyCards(int deckId, int cardId) { }
    
    // 카드를 찾아라(키워드 검색)
    public List<Card> findCards(String keywords) { }
    // 카드를 찾아라(플래너를 통해 card의 식별자를 알고 있는 경우)
    public List<Card> findCards(List<Integer> cardIdList) { }
    
    // 이름을 기록하라
    public boolean modifyDeckName(int deckId, String name) {
        // 데이터베이스 수정
    }

    // 보관함 목록을 찾아라
    public List<Deck> getDeckList() { }

}
