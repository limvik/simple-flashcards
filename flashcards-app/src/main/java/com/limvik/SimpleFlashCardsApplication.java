package com.limvik;

import com.limvik.controller.InputController;
import com.limvik.enums.MainMenu;
import com.limvik.enums.SelectedUserMenu;
import com.limvik.flashcards.Board;
import com.limvik.flashcards.User;
import com.limvik.view.MainView;
import com.limvik.view.SelectedUserView;
import com.limvik.view.View;

public class SimpleFlashCardsApplication 
{
    public static void main(String[] args)
    {

        // 메인 화면 출력
        View mainView = new MainView();
        printMainMenu(mainView);
        
        // 메인 메뉴 선택
        MainMenu mainMenu = getSelectedMainMenu(mainView);
        switch (mainMenu) {
            case START:
                // 사용자 메뉴 출력
                Board board = new Board();
                User user = getSelectedUser(mainView, board);
                var selectedUserMenu = getSelectedUserMenu(mainView, user);
                break;
            case EXIT:
                exit();
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

    private static MainMenu getSelectedMainMenu(View mainView) {
        return (MainMenu) InputController.getMenuInput(mainView, MainMenu.values());
    }

    private static void moveOtherScreen(View mainView) {

        // 화면 청소
        View.clearScreen();

        // 로딩 메시지 출력
        mainView.printLoading();

        View.pause(1);
    }

    private static User getSelectedUser(View mainView, Board board) {

        moveOtherScreen(mainView);

        // 사용자 메뉴 출력 및 선택된 사용자 정보 반환
        return board.showUserList();
        
    }

    private static SelectedUserMenu getSelectedUserMenu(View mainView, User user) {
        
        moveOtherScreen(mainView);

        // 화면 청소
        View.clearScreen();

        // 선택된 사용자 메뉴 출력
        View selectedUserView = new SelectedUserView();
        System.out.print("'" + user.getName() + "'");
        selectedUserView.printFirstMessage();
        selectedUserView.printMenu();

        var selectedUserMenu = (SelectedUserMenu) InputController.getMenuInput(selectedUserView, SelectedUserMenu.values());

        if(selectedUserMenu == SelectedUserMenu.EXIT) exit();

        return selectedUserMenu;
    }

    private static void exit() {
        InputController.getInstance().closeScanner();
        System.exit(0);
    }
}
