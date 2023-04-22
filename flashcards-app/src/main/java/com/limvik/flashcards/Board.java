package com.limvik.flashcards;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.limvik.controller.InputController;
import com.limvik.dao.DatabaseConnection;
import com.limvik.dao.UserDAO;
import com.limvik.enums.Menu;
import com.limvik.enums.UserMenu;
import com.limvik.view.CreateUserView;
import com.limvik.view.UserMenuView;
import com.limvik.view.UserSelectionView;
import com.limvik.view.View;

public class Board {

    private List<Deck> decks; // 보관함 목록
    private List<Plan> plans; // 학습 일정 목록
    private List<User> users; // 사용자 목록

    // 사용자 목록을 보여라
    public void showUserList() {
        UserDAO userDAO = null;
        // 사용자 데이터 불러오기
        if (users == null) {
            try {
                userDAO = new UserDAO(DatabaseConnection.getInstance().getConnection());
                users = userDAO.getAllUsers();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                System.exit(0);
            }
        }
        while (true) {
            // 이전 화면 메시지 지우기
            View.clearScreen();
    
            // 사용자 메뉴 출력
            View view = new UserMenuView();
            printMenu(view);
    
            UserMenu userMenu = (UserMenu) InputController.getMenuInput(view, UserMenu.values());
            
            switch (userMenu) {
                case CREATE:
                    // 이전 화면 메시지 지우기
                    View.clearScreen();

                    // 사용자 생성 메뉴 출력
                    view = new CreateUserView();
                    printMenu(view);

                    while (true) {
                        String name = InputController.getUserNameInput();
                        
                        // 중복 체크
                        boolean isDuplicate = false;
                        for (var user : users) {
                            if (user.getName().equals(name)) {
                                System.out.print(CreateUserView.DUPLICATE);
                                view.printError();
                                isDuplicate = true;
                                View.pause(2);
                                break;
                            }
                        }
                        if (isDuplicate) continue;

                        // 사용자 데이터베이스에 저장
                        var newUser = new User(users.get(users.size() - 1).getId() + 1, name);
                        view.printLoading();
                        View.pause(1);
                        if (userDAO.insertUser(newUser) == 1) {
                            users.add(newUser);
                            System.out.print("'"+newUser.getName()+"'");
                            CreateUserView.returnToMenu();
                            View.pause(2);
                            break;
                        } else {
                            System.out.println(CreateUserView.FAILED);
                            view.printError();
                        }
                    }
                    break;
                case LIST:
                    // 사용자 목록 출력
                    
                case EXIT:
                    InputController.getInstance().closeScanner();
                    System.exit(0);
            }
        }

    }

    private void printMenu(View view) {
        view.printFirstMessage();
        view.printMenu();
    }

    // 보관함 목록 및 보관함 별 학습 대상 카드 갯수를 보여라
    public void showBoard(int userSequenceNumber, Planner planner) {

        // 선택된 사용자 정보 식별
        User user = users.get(userSequenceNumber);

        // 사용자의 보관함 전체 목록 불러오기
        decks = /* 데이터베이스 호출 */;

        // 오늘 학습 일정 불러오기
        LocalDate today = LocalDate.now();
        plans = planner.searchStudyPlan(user.getId(), today, today);

        // 보관함 목록 및 보관함 별 학습 대상 카드 갯수 시현하기

    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}