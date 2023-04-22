package com.limvik;

import com.limvik.controller.InputController;
import com.limvik.enums.MainMenu;
import com.limvik.flashcards.Board;
import com.limvik.view.MainView;
import com.limvik.view.View;

public class SimpleFlashCardsApplication 
{
    public static void main(String[] args)
    {

        // 메인 화면 출력
        View view = new MainView();
        printMainMenu(view);
        
        // 메인 메뉴 선택
        MainMenu mainMenu = getSelectedMainMenu(view);
        switch (mainMenu) {
            case START:
                // 사용자 메뉴 출력
                Board board = new Board();
                loadingUserList(view, board);
                break;
            case EXIT:
                InputController.getInstance().closeScanner();
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

    private static MainMenu getSelectedMainMenu(View view) {
        return (MainMenu) InputController.getMenuInput(view, MainMenu.values());
    }

    private static void loadingUserList(View view, Board board) {

        // 화면 청소
        View.clearScreen();

        // 로딩 메시지 출력
        view.printLoading();

        // 사용자 메뉴 출력
        board.showUserList();
        
    }
}
