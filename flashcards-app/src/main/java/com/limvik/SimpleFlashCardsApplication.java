package com.limvik;

import java.sql.SQLException;
import java.util.List;

import com.limvik.controller.InputController;
import com.limvik.dao.DatabaseConnection;
import com.limvik.dao.UserDAO;
import com.limvik.enums.MainMenu;
import com.limvik.enums.SelectedUserMenu;
import com.limvik.flashcards.Board;
import com.limvik.flashcards.Deck;
import com.limvik.flashcards.Planner;
import com.limvik.flashcards.User;
import com.limvik.view.MainView;
import com.limvik.view.View;
import com.limvik.view.user.DeleteUserView;
import com.limvik.view.user.SelectedUserView;
import com.limvik.view.user.UpdateUserView;

public class SimpleFlashCardsApplication 
{
    public static void main(String[] args)
    {

        // 메인 화면 출력
        View mainView = new MainView();
        printMenu(mainView);
        
        // 메인 메뉴 선택
        MainMenu mainMenu = getSelectedMainMenu(mainView);
        switch (mainMenu) {
            case START:
                // 사용자 선택 메뉴 출력
                Board board = new Board();
                User user = getSelectedUser(mainView, board);
                while (true) {
                    // 선택된 사용자 메뉴 출력
                    var selectedUserMenu = getSelectedUserMenu(mainView, user);
                    switch (selectedUserMenu) {
                        case BEFORE:
                            // 사용자 선택 메뉴로 돌아가기
                            user = getSelectedUser(mainView, board);
                            break;
                        case STUDY:
                            List<Deck> studyDeck = board.showDecks(user, new Planner());
                            break;
                        case UPDATE:
                            View.clearScreen();
                            var updateUserView = new UpdateUserView();
                            updateUserView.printFirstMessage();
                            updateUserView.printMenu();

                            String newName = InputController.getUserTextInput();
                            // 중복 검사
                            try {
                                var userDAO = new UserDAO(DatabaseConnection.getInstance().getConnection());
                                if (userDAO.isDuplicatedName(newName)) throw new SQLException();
                            } catch (SQLException e) {
                                System.err.println(e.getMessage());
                                System.out.print(UpdateUserView.DUPLICATE); 
                                updateUserView.printError();
                                View.pause(1);
                                break;
                            }

                            // 사용자 수정
                            user.setName(newName);
                            int updatedRow = 0;
                            try {
                                var userDAO = new UserDAO(DatabaseConnection.getInstance().getConnection());
                                updatedRow = userDAO.updateUser(user);
                                if (updatedRow == 0) throw new SQLException();
                            } catch (SQLException e) {
                                System.err.println(e.getMessage());
                                updateUserView.printError();
                                View.pause(1);
                                break;
                            }

                            updateUserView.printLoading();
                            View.pause(1);
                            updateUserView.returnToMenu();
                            View.pause(1);
                            break;
                        case DELETE:
                            View view = new DeleteUserView();
                            System.out.print("'" + user.getName() + "'");
                            printMenu(view);

                            boolean isDelete = InputController.isYesOrNo(view);

                            if (isDelete) {
                                int deletedRow = 0;
                                try {
                                    var userDAO = new UserDAO(DatabaseConnection.getInstance().getConnection());
                                    deletedRow = userDAO.deleteUser(user.getId());
                                    if (deletedRow == 0) throw new SQLException();
                                } catch (SQLException e) {
                                    System.err.println(e.getMessage());
                                    break;
                                }
                                view.printLoading();
                                View.pause(1);
                                user = getSelectedUser(mainView, board);
                            } else {
                                ((DeleteUserView) view).printCancelDelete();
                                View.pause(1);
                            }
                            break;
                        case EXIT:
                            exit();
                            break;
                    } // end switch SelectedUserMenu
                } // end while
            case EXIT:
                exit();
                break;
        }


    }

    private static void printMenu(View view) {

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
        selectedUserView.printLoading();
        View.pause(1);
        View.clearScreen();
        return selectedUserMenu;
    }

    private static void exit() {
        InputController.getInstance().closeScanner();
        System.exit(0);
    }
}
