package com.limvik.view;

import com.limvik.enums.UserSelectionMenu;

public class UserSelectionView implements View {

    private static final String WELCOME = "사용자 선택 화면입니다. 아래에서 사용자 또는 메뉴를 선택해주세요.";
    public static final String MENU_GUIDE = "위 메뉴 번호 혹은 사용자 번호 중 하나를 입력 후 엔터 키를 눌러주세요. 예) 1\n>";
    private static final String LOADING = "선택하신 메뉴 혹은 사용자 개인 화면으로 이동합니다.";
    private static final String NO_USERS = "사용자 정보가 없습니다. 먼저 사용자를 추가해주세요.";
    private static final String ERROR = "정확한 번호를 입력하세요. 예) 1\n>";

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

        for (UserSelectionMenu userSelectionMenu : UserSelectionMenu.values()) {
            System.out.println(menuNumber + ". " + userSelectionMenu.getDescription());
            ++menuNumber;
        }

    }

    @Override
    public void printError() {
        System.out.println(ERROR);
    }

    public static void printNoUsers() {
        System.out.println(NO_USERS);
    }

}
