package com.limvik.flashcards;

public class Pen {

    // 내용을 전달하라(학습 일정)
    public boolean sendContents(Planner planner, Plan plan) {
        return planner.writePlan(plan);
    }

    // 내용을 전달하라(보관함 이름 수정)
    public boolean sendContents(Deck deck, int id, String name) {
        return deck.modifyDeckName(id, name);
    }

    // 내용을 전달하라(학습 내용)
    public boolean sendContents(Card card, String front, String back) {
        card.setFront(front);
        card.setBack(back);
        return card.writeContents(card);
    }

}
