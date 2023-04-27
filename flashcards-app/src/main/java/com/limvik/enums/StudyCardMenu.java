package com.limvik.enums;

public enum StudyCardMenu implements Menu {

    WRONG("틀림"),
    CORRECT("맞음"),
    BEFORE("이전 메뉴로 돌아가기"),
    EXIT("종료");

    private final String description;

    StudyCardMenu(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getMenuRegex() {
        // 선택된 메뉴 유효성 검사용 정규표현식 반환
        return "[1-" + StudyCardMenu.values().length + "]";
    }
    
}
