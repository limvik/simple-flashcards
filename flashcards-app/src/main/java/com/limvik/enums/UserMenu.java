package com.limvik.enums;

public enum UserMenu implements Menu {
    
    CREATE("새로운 사용자 생성"),
    LIST("사용자 목록 불러오기"),
    EXIT("종료");

    private final String description;

    UserMenu(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // 선택된 메인 메뉴 유효성 검사용 정규표현식 반환
    @Override
    public String getMenuRegex() {
        return "[1-" + UserMenu.values().length + "]";
    }
}
