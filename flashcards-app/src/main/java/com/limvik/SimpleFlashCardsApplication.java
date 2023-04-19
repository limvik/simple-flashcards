package com.limvik;

import java.util.Scanner;
import java.util.regex.Pattern;

import com.limvik.controller.InputController;
import com.limvik.enums.MainMenu;
import com.limvik.flashcards.Board;
import com.limvik.view.MainView;
import com.limvik.view.View;

public class SimpleFlashCardsApplication 
{
    public static void main(String[] args)
    {
        // 입력 도구 불러오기
        InputController inputController = InputController.getInstance();
        Scanner stdIn = inputController.getScanner();

        // 메인 화면 출력
        View view = new MainView();
        printMainMenu(view);
        
        // 메인 메뉴 선택
        MainMenu mainMenu = getSelectedMainMenu(stdIn, view);
        switch (mainMenu) {
            case START:
                // 사용자 목록 출력
                Board board = new Board();
                loadingUserList(view, board);
                break;
            case EXIT:
                stdIn.close();
                System.exit(0);
        }      
    }

    private static void printMainMenu(View view) {

        // 화면 청소
        View.clearScreen();
        
        // 인사 메시지 출력
        view.printFirstMessage();
        
        // 메뉴 출력
        view.printMenu();

    }

    private static MainMenu getSelectedMainMenu(Scanner stdIn, View view) {
        
        // 유효성 검사용 정규표현식 설정
        Pattern pattern = Pattern.compile(MainMenu.getMenuRegex());

        // 입력 및 유효성 검사
        while (!stdIn.hasNext(pattern)) {
            // 오류 안내 메시지 출력
            view.printError();
            // 잘못된 입력 버퍼에서 제거
            stdIn.nextLine();
        }
        
        // 입력받은 메뉴 번호 저장
        int menu = stdIn.nextInt();
        // 개행문자 제거
        stdIn.nextLine();

        // 메뉴 탐색
        MainMenu mainMenu = null;
        for (MainMenu m : MainMenu.values()) {
            if (m.ordinal() + 1 == menu) {
                mainMenu = m;
                break;
            }
        }

        return mainMenu;
    }

    private static void loadingUserList(View view, Board board) {
        // 로딩 메시지 출력
        view.printLoading();
        // 사용자 목록 출력
        board.showUserList();
    }
}
