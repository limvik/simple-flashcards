package com.limvik.view.card;

import com.limvik.controller.InputController;
import com.limvik.view.View;

public class CreateCardView implements View {

    private static final String WELCOME = "새로운 카드 제작을 시작합니다.";
    private static final String FRONT = "카드 `앞`면의 내용을 입력해주세요.";
    private static final String BACK = "카드 `뒷`면의 내용을 입력해주세요.";
    private static final String RESTRICT = "한 글자 이상 입력해 주세요.\n>";
    private static final String ERROR = "잘못된 입력입니다.\n>";
    private static final String LOADING = "카드를 저장 중입니다. 잠시만 기다려 주세요.";
    private static final String MENU_GUIDE = "입력에 이상이 없으면 Y, 취소하려면 N을 입력 후 엔터를 눌러주세요.\n>";
    private static final String SAVE_ERROR = "카드 저장 중 오류가 발생하였습니다. 다시 시도해 주세요.";
    private static final String SAVE_SUCCESS = "카드 저장을 완료하였습니다. 이전 화면으로 돌아갑니다.";

    private String front;
    private String back;

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }

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

        // 카드 앞면 입력 받기
        do {
            front = "";
            System.out.println(FRONT);
            while (front.length() < 1) {
                System.out.print(RESTRICT);
                front = InputController.getUserCardInput();
            }
            System.out.print(MENU_GUIDE);
        } while (!InputController.isYesOrNo(this));
        
        if (front.length() > 0) {
            
            // 카드 뒷면 입력 받기
            do {
                back = "";
                System.out.println(BACK);
                while (back.length() < 1) {
                    System.out.print(RESTRICT);
                    back = InputController.getUserCardInput();
                }
                System.out.print(MENU_GUIDE);
            } while (!InputController.isYesOrNo(this));

        }
        
    }

    @Override
    public void printError() {
        System.out.println(ERROR);
    }

    public void printSaveError() {
        System.out.println(SAVE_ERROR);
    }

    public void printSuccess() {
        System.out.println(SAVE_SUCCESS);
    }

}
