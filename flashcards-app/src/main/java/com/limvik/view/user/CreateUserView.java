package com.limvik.view.user;

import com.limvik.view.View;

public class CreateUserView implements View {

    private static final String WELCOME = "새로운 사용자 생성을 시작합니다.";
    private static final String LOADING = "새로운 사용자 정보를 등록중 입니다. 잠시만 기다려주세요.";
    private static final String MENU = "사용하실 이름 작성 후 엔터 키를 눌러주세요.";
    private static final String RESTRICT = "(이름은 최대 70글자 까지 입력 가능합니다.)\n>";
    private static final String ERORR = "오류가 발생하였습니다. 다시 입력해주세요.\n>";
    private static final String RETURN = "사용자 정보를 저장하였습니다. 선택 화면으로 돌아갑니다.";
    public static final String DUPLICATE = "사용자명 중복 ";
    public static final String FAILED = "알 수 없는 ";

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
        System.out.print(ERORR);
    }

    public void returnToMenu() {
        System.out.println(RETURN);
    }
}
