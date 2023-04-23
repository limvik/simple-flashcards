package com.limvik.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public class UserSelectionMenu implements Menu {
    
    private static final Map<String, UserSelectionMenu> VALUES = new LinkedHashMap<>();
    public static final String BEFORE = "이전 메뉴로 돌아가기";
    public static final String EXIT = "종료";

    private final String name;

    private UserSelectionMenu(String name) {
        this.name = name;
    }

    public static void create(String name) {
        UserSelectionMenu newMenu = new UserSelectionMenu(name);
        VALUES.put(name, newMenu);
    } 

    public static UserSelectionMenu[] values() {
        return VALUES.values().toArray(new UserSelectionMenu[0]);
    }

    public String getName() {
        return name;
    }

    public static void clearUserList() {
        VALUES.clear();
        setDefaultMenus();
    }

    private static void setDefaultMenus() {
        VALUES.put(BEFORE, new UserSelectionMenu(BEFORE));
        VALUES.put(EXIT, new UserSelectionMenu(EXIT));
    }

    // 선택된 메인 메뉴 유효성 검사용 정규표현식 반환
    @Override
    public String getMenuRegex() {        
        return "[1-" + UserSelectionMenu.values().length + "]";
    }
}
