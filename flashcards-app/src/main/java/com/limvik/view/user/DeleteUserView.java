package com.limvik.view.user;

import com.limvik.view.View;

public class DeleteUserView implements View {

    private static final String WELCOME = "님의 정보를 삭제합니다.";
    private static final String DELETE = "정말 삭제하시겠습니까? 모든 보관함, 카드 및 학습기록도 함께 삭제됩니다.";
    private static final String MENU_GUIDE = "삭제를 원하시면 Y, 취소하려면 N을 입력 후 엔터를 눌러주세요.\n>";
    private static final String ERROR = "잘못된 입력입니다. Y 또는 N을 입력해주세요.\n>";
    private static final String CANCEL = "삭제가 취소되었습니다. 이전 메뉴로 돌아갑니다.";
    private static final String LOADING = "삭제가 완료되었습니다. 이전 메뉴로 이동합니다.";

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
        System.out.println(DELETE);
        System.out.print(MENU_GUIDE);
    }

    @Override
    public void printError() {
        System.out.println(ERROR);
    }

    public void printCancelDelete() {
        System.out.println(CANCEL);
    }
    
}
