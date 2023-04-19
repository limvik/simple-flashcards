package com.limvik.enums;

public enum MainMenu {

    START("학습 시작"),
    EXIT("종료");


    private final String description;

    MainMenu(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // 메인 메뉴 목록 반환
    public static MainMenu[] getMainMenu() {
        return MainMenu.values();
    }

    // 선택된 메인 메뉴 유효성 검사용 정규표현식 반환
    public static String getMenuRegex() {
        return "[1-" + MainMenu.values().length + "]";
    }
}
