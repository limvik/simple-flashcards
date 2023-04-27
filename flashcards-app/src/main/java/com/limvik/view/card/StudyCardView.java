package com.limvik.view.card;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import com.limvik.controller.InputController;
import com.limvik.enums.StudyCardMenu;
import com.limvik.flashcards.Card;
import com.limvik.flashcards.Deck;
import com.limvik.view.View;

public class StudyCardView implements View {

    private static final String WELCOME = "보관함에서의 학습을 시작합니다.";
    private static final String ERROR = "정확한 번호를 입력하세요. 예) 1\n>";
    private static final String LOADING = "학습이 모두 완료되었습니다.";
    private static final String MENU_GUIDE = "위 메뉴 중 하나를 입력 후 엔터 키를 눌러주세요. 예) 1\n>";

    Deck deck;
    Deque<Card> cards;

    public StudyCardView(Deck deck, List<Card> cards) {
        this.deck = deck;
        this.cards = new ArrayDeque<>(cards);
    }

    @Override
    public void printFirstMessage() {
        System.out.println(WELCOME);
    }

    @Override
    public void printLoading() {
        System.out.println(LOADING);
    }

    @Override
    public void printMenu() {
        
        // 진입 메시지 출력
        View.clearScreen();
        printDeckName();
        printFirstMessage();
        View.pause(1);
        while (!cards.isEmpty()) {
            View.clearScreen();
    
            // 카드 앞면 출력
            printCardFront();
    
            // 카드 뒷면 출력
            printCardBack();
    
            // 메뉴 출력 및 사용자 선택 입력받기
            for (var menu : StudyCardMenu.values()) {
                System.out.println(menu.ordinal() + 1 + ". " + menu.getDescription());
            }
            System.out.print(MENU_GUIDE);
    
            var menu = (StudyCardMenu) InputController.getMenuInput(this, StudyCardMenu.values());
            switch(menu) {
                case WRONG, CORRECT:
                    // 틀렸을 때 혹은 맞았을 때의 가중치를 반영한 다음 학습일정 계산
                    break;
                case BEFORE:
                    return;
                case EXIT:
                    exit();
                    break;
            }
        } // end while
        printLoading();
        View.pause(1);
    }

    private void printCardFront() {
        String front = cards.getFirst().getFront();
        int len = front.length() + 10;
        System.out.print("=".repeat(len));
        System.out.printf("%n%n%"+ len/2 + "s%n%n", front);
        System.out.println("=".repeat(len));
        for (int i = 3; i > 0; --i) {
            System.out.print(i + "초 후 카드 뒷면을 확인합니다.");
            View.pause(1);
            System.out.print("\033[2K"); // 현재 커서 위치의 줄에 있는 문자 삭제
            System.out.print("\r"); // 현재 줄의 가장 앞으로 커서 옮기기
        }
    }

    private void printCardBack() {
        int len = cards.getFirst().getFront().length() + 10;
        System.out.printf("%n%n%"+ len/2 +"s%n%n", cards.removeFirst().getBack());
        System.out.println("=".repeat(len));
    }

    private void exit() {
        InputController.getInstance().closeScanner();
        System.exit(0);
    }

    @Override
    public void printError() {
        System.out.print(ERROR);
    }

    private void printDeckName() {
        System.out.print("'" + deck.getName() + "'");
    }
    
}
