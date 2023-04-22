package com.limvik.view;

import com.limvik.enums.MainMenu;

public class MainView implements View {

    private static final String WELCOME = "Welcome to Limvik's Flashcards";
    private static final String LOADING = "loading...";
    private static final String MENU_GUIDE = "위 메뉴 중 하나를 입력 후 엔터 키를 눌러주세요. 예) 1\n>";
    private static final String ERROR = "정확한 메뉴 번호를 입력하세요. 예) 1\n>";

    // 프로그램 시작 인사
    @Override
    public void printFirstMessage() {
        System.out.println(WELCOME);
    }

    // 대기 메시지 출력
    @Override
    public void printLoading() {
        System.out.println(LOADING);
    }

    // 메인 메뉴 출력
    // 메뉴가 늘어나면 순서를 바꾸는 경우를 고려하여 여기서 넘버링
    @Override
    public void printMenu() {

        int menuNumber = 1;

        for (MainMenu mainMenu : MainMenu.values()) {
            System.out.println(menuNumber + ". " + mainMenu.getDescription());
            ++menuNumber;
        }

        System.out.print(MENU_GUIDE);

    }

    @Override
    public void printError() {
        System.out.print(ERROR);
    }

}
