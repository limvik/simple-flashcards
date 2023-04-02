package com.limvik.flashcards;

import java.util.List;

public class Deck {

    // 보관함을 관리하라(생성)
    public Deck(String deckName) { }
    
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
    public boolean modifyDeckName(int deckId, String newDeckName) { }

}
