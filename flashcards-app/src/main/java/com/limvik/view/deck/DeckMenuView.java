package com.limvik.view.deck;

import com.limvik.enums.DeckMenu;
import com.limvik.view.View;

public class DeckMenuView implements View {
    private static final String WELCOME = "보관함 입니다. 학습할 보관함을 생성하거나 선택하세요.";
    private static final String MENU_GUIDE = "위 메뉴 번호 혹은 보관함 번호 중 하나를 입력 후 엔터 키를 눌러주세요. 예) 1\n>";
    private static final String LINE =        "-".repeat(30);
    private static final String CHILD_LIST = "하위 보관함 목록";
    private static final String COLUMN_NAME = "보관함이름\t미학습 카드\t오늘의 카드";
    private static final String ERROR = "정확한 번호를 입력하세요. 예) 1\n>";
    private static final String NO_CARDS = "현재 보관함에는 보유한 카드가 없습니다. 카드를 생성해주세요.";
    private static final String LOADING = "학습을 시작합니다.";

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
        
        int menuNumber = 1;

        for (var deckMenu : DeckMenu.values()) {
            System.out.println(menuNumber + ". " + deckMenu.getName());
            if (deckMenu.getName().equals(DeckMenu.EXIT)) {
                System.out.print(LINE);
                System.out.print(CHILD_LIST);
                System.out.println(LINE);
                System.out.println(COLUMN_NAME);
            }
            ++menuNumber;
        }
        System.out.print(MENU_GUIDE);
    }

    @Override
    public void printError() {
        System.out.print(ERROR);
    }

    public void printNoCards() {
        System.out.println(NO_CARDS);
    }
    
}
