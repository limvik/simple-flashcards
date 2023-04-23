package com.limvik.enums;

public enum SelectedUserMenu implements Menu {
    
    BEFORE("이전 메뉴로 돌아가기"),
    STUDY("학습 목록 확인하기"),
    UPDATE("사용자 이름 수정하기"),
    DELETE("사용자 삭제하기"),
    EXIT("종료");

    private final String description;

    SelectedUserMenu(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // 선택된 메뉴 유효성 검사용 정규표현식 반환
    @Override
    public String getMenuRegex() {       
        return "[1-" + SelectedUserMenu.values().length + "]";
    }
    
}
