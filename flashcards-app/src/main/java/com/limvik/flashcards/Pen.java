package com.limvik.flashcards;

public class Pen {
    // 내용을 전달하라(학습 일정)
    public Plan sendContents(Plan plan) { }
    // 내용을 전달하라(보관함 이름)
    public String sendContents(int deckId, String deckName) { }
    // 내용을 전달하라(학습 내용)
    public Card sendContents(int cardId, String front, String back) { }

}
