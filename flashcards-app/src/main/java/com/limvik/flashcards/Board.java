package com.limvik.flashcards;

import java.time.LocalDate;
import java.util.List;

public class Board {

    private List<Deck> decks; // 보관함 목록
    private List<Plan> plans; // 학습 일정 목록

    // 보관함 목록 및 보관함 별 학습 대상 카드의 개수를 보여라
    public void showBoard(Deck deck, Planner planner) {

        // 보관함 목록 불러오기
        decks = deck.getDeckList();

        // 오늘 학습 일정 불러오기
        LocalDate today = LocalDate.now();
        plans = planner.searchStudyPlan(today, today);

    }

}