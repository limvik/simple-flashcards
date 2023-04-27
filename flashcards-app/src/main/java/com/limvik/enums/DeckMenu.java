package com.limvik.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public class DeckMenu implements Menu {

    private static final Map<String, DeckMenu> VALUES = new LinkedHashMap<>();
    public static final String BEFORE = "이전 메뉴로 돌아가기";
    public static final String START = "현재 보관함에서 학습 시작하기";
    public static final String NEW_CARD = "현재 보관함에 새로운 카드 추가하기";
    public static final String NEW_DECK = "새로운 하위 보관함 만들기";
    public static final String EDIT_DECKNAME = "현재 보관함 이름 수정하기";
    public static final String DELETE_DECK = "현재 보관함 삭제하기";
    public static final String EXIT = "종료";

    private final String name;

    private DeckMenu(String name) {
        this.name = name;
    }

    public static void create(String name) {
        DeckMenu newMenu = new DeckMenu(name);
        VALUES.put(name, newMenu);
    } 

    public static DeckMenu[] values() {
        return VALUES.values().toArray(new DeckMenu[0]);
    }

    public String getName() {
        return name;
    }

    public static void clearDeckList(boolean isRoot) {
        VALUES.clear();
        setDefaultMenus(isRoot);
    }

    private static void setDefaultMenus(boolean isRoot) {
        VALUES.put(BEFORE, new DeckMenu(BEFORE));
        if(!isRoot) {
            VALUES.put(START, new DeckMenu(START));
            VALUES.put(NEW_CARD, new DeckMenu(NEW_CARD));
        }
        VALUES.put(NEW_DECK, new DeckMenu(NEW_DECK));
        if(!isRoot) {
            VALUES.put(EDIT_DECKNAME, new DeckMenu(EDIT_DECKNAME));
            VALUES.put(DELETE_DECK, new DeckMenu(DELETE_DECK));
        }
        VALUES.put(EXIT, new DeckMenu(EXIT));
    }

    // 선택된 메뉴 유효성 검사용 정규표현식 반환
    @Override
    public String getMenuRegex() {
        int max = DeckMenu.values().length;
        if (max <= 9) {
            return "[1-" + max + "]";
        } else {
            StringBuilder patternBuilder = new StringBuilder();
            patternBuilder.append("[1-9]");
            
            for (int i = 1; i <= max/10; ++i) {
                patternBuilder.append("|").append(i).append("[0-").append(max%10).append("]");
            }
    
            return patternBuilder.toString();
        }
    }
    
}
