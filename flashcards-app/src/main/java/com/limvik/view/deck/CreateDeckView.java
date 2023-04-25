package com.limvik.view.deck;

import com.limvik.view.View;

public class CreateDeckView implements View {

    private static final String WELCOME = "새로운 보관함 생성을 시작합니다.";
    private static final String LOADING = "새로운 보관함 정보를 등록중 입니다. 잠시만 기다려주세요.";
    private static final String MENU = "보관함 이름 작성 후 엔터 키를 눌러주세요.";
    private static final String RESTRICT = "(보관함 이름은 최대 70글자 까지 입력 가능합니다.)\n>";
    private static final String DUPLICATE = "중복된 보관함 이름입니다. 다시 입력해주세요. \n>";

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
        System.out.println(MENU);
        System.out.print(RESTRICT);
    }

    @Override
    public void printError() {
        System.out.print(DUPLICATE);
    }
        
}
