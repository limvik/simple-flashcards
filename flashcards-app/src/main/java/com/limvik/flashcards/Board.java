package com.limvik.flashcards;

import java.sql.SQLException;
import java.util.List;

import com.limvik.controller.InputController;
import com.limvik.dao.DatabaseConnection;
import com.limvik.dao.DeckDAO;
import com.limvik.dao.UserDAO;
import com.limvik.enums.DeckMenu;
import com.limvik.enums.UserMenu;
import com.limvik.enums.UserSelectionMenu;
import com.limvik.view.View;
import com.limvik.view.deck.CreateDeckView;
import com.limvik.view.deck.DeckMenuView;
import com.limvik.view.deck.DeleteDeckView;
import com.limvik.view.deck.UpdateDeckView;
import com.limvik.view.user.CreateUserView;
import com.limvik.view.user.UserMenuView;
import com.limvik.view.user.UserSelectionView;

public class Board {

    private List<Deck> decks; // 보관함 목록
    private List<Plan> plans; // 학습 일정 목록
    private List<User> users; // 사용자 목록

    // 사용자 목록을 보여라
    public User showUserList() {

        UserDAO userDAO = null;
        // 사용자 데이터 불러오기
        try {
            userDAO = new UserDAO(DatabaseConnection.getInstance().getConnection());
            users = userDAO.getAllUsers();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        while (true) {
            // 이전 화면 메시지 지우기
            View.clearScreen();
    
            // 사용자 메뉴 출력
            View view = new UserMenuView();
            printMenu(view);
    
            UserMenu userMenu = (UserMenu) InputController.getMenuInput(view, UserMenu.values());
            view.printLoading();
            View.pause(1);
            switch (userMenu) {
                case CREATE:

                    while (true) {
                        // 이전 화면 메시지 지우기
                        View.clearScreen();

                        // 사용자 생성 메뉴 출력
                        view = new CreateUserView();
                        printMenu(view);

                        // 사용자 이름 입력 받기
                        String name = null;
                        do {
                            name = InputController.getUserTextInput();
                        } while (isDuplicatedUserName(view, name));

                        // 신규 사용자를 데이터베이스에 저장
                        int lastUserId = 0;
                        if (!users.isEmpty()) {
                            lastUserId = users.get(users.size() - 1).getId();
                        }
                        var newUser = new User(lastUserId + 1, name);
                        view.printLoading();
                        View.pause(1);

                        if (userDAO.insertUser(newUser) == 1) {
                            
                            // 사용자 기본 보관함 생성
                            createInitialDeck(newUser);

                            // 이전 메뉴로 돌아가기
                            users.add(newUser);
                            System.out.print("'"+newUser.getName()+"'");
                            ((CreateUserView)view).returnToMenu();
                            View.pause(2);
                            break;
                        } else {
                            System.out.println(CreateUserView.FAILED);
                            view.printError();
                        }
                    } // end while
                    break; // end CREATE menu
                case LIST:
                    // 이전 화면 메시지 지우기
                    View.clearScreen();
                    // 사용자 선택 메뉴 초기화
                    UserSelectionMenu.clearUserList();
                    // 사용자 목록 추가
                    for (var user : users) {
                        UserSelectionMenu.create(user.getName());
                    }

                    // 사용자 목록 출력
                    view = new UserSelectionView();
                    if (users.isEmpty()) {
                        UserSelectionView.printNoUsers();
                        View.pause(2);
                    } else {
                        // 메뉴 및 사용자 목록 출력
                        printMenu(view);

                        var userSelectionMenu = 
                        (UserSelectionMenu) InputController.getMenuInput(view, UserSelectionMenu.values());
                        
                        // 입력에 따른 처리
                        switch (userSelectionMenu.getName()) {
                            case UserSelectionMenu.BEFORE:
                                break;
                            case UserSelectionMenu.EXIT:
                                exit();
                                break;
                            default: // 사용자 선택한 경우
                                for (var user : users) {
                                    if (user.getName().equals(userSelectionMenu.getName())) {
                                        return user;
                                    }
                                }
                                break;
                        }

                        // 화면 이동 메시지 출력
                        view.printLoading();
                        View.pause(2);
                    }
                    break;
                case EXIT:
                    exit();

            } // end switch userMenu
        } // end while

    }

    private boolean isDuplicatedUserName(View view, String name) {
        
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

        return isDuplicate;
    }

    // 신규 사용자를 위한 최상위 보관함 생성
    private void createInitialDeck(User user) {

        DeckDAO deckDAO = null;

        try {
            deckDAO = new DeckDAO(DatabaseConnection.getInstance().getConnection());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        int deckId = deckDAO.getLastDeckId() + 1;
        deckDAO.insertDeck(new Deck(deckId, user.getId(), "root",  "0"));
    }

    // 보관함 목록 및 보관함 별 학습 대상 카드 갯수를 보여라
    public List<Deck> showDecks(User user, Planner planner) {

        // 보관함 데이터 액세스 객체 생성
        DeckDAO deckDAO = null;
        try {
            deckDAO = new DeckDAO(DatabaseConnection.getInstance().getConnection());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        // 사용자의 보관함 상위(root 다음) 목록 불러오기 위한 조상 변수 설정
        String delimeter = "::";
        // 사용자의 root 보관함은 모두 0이고, 그 자식들을 조회하기 위해 0:: 를 초기 ancestry로 설정
        String ancestry = "0" + delimeter;

        while(true) {
            View view = new DeckMenuView();
            

            // 오늘 학습 일정 불러오기
            // LocalDate today = LocalDate.now();
            // plans = planner.searchStudyPlan(user.getId(), today, today);

            initializeDeckMenu(view, deckDAO, user, ancestry, delimeter);

            // 보관함 목록 및 보관함 별 학습 대상 카드 갯수 시현하기
            DeckMenu deckMenu = (DeckMenu) InputController.getMenuInput(view, DeckMenu.values());
            switch (deckMenu.getName()) {
                case DeckMenu.BEFORE:
                    if (ancestry.equals("0" + delimeter)) {
                        decks = null;
                        return decks;
                    } else {
                        ancestry = getParentDeckAncestry(ancestry, delimeter);
                    }
                    break;
                case DeckMenu.START:
                    return decks;
                case DeckMenu.NEW_CARD:
                    
                    break;
                case DeckMenu.NEW_DECK:
                    view = new CreateDeckView();
                    initializeCreateDeckMenu(view);
                    createDeck(view, deckDAO, user, ancestry);
                    break;
                case DeckMenu.EDIT_DECKNAME:
                    view = new UpdateDeckView();
                    initializeUpdateDeckMenu(view);
                    updateDeck(view, deckDAO, user, getCurrentDeckId(ancestry, delimeter));
                    break;
                case DeckMenu.DELETE_DECK:
                    view = new DeleteDeckView();
                    initializeDeleteDeckMenu(view, deckDAO, ancestry, delimeter);
                    ancestry = chooseDeleteDeck(view, deckDAO, ancestry, delimeter);
                    break;
                case DeckMenu.EXIT:
                    exit();
                    break;
                default:
                    ancestry += moveToChildDeck(deckMenu, delimeter);
                    break;
            } //end switch DeckMenu
        } // end while

    }

    private void initializeDeckMenu(View view, DeckDAO deckDAO, User user, String ancestry, String delimeter) {
        
        // 이전 화면 메시지 지우기
        View.clearScreen();
        // 보관함 선택 메뉴 초기화
        DeckMenu.clearDeckList(ancestry.equals("0" + delimeter));
        // 선택된 보관함의 한 단계 아래 계층 보관함 불러오기
        decks = deckDAO.getDecksByUserId(user, ancestry);
        for (var deck : decks) {
            DeckMenu.create(deck.getName());
        }
        
        // 메뉴 출력하기
        System.out.print("'" + user.getName() + "'님의 ");
        if (!ancestry.equals("0" + delimeter)) {
            Deck deck = deckDAO.getDeckById(getCurrentDeckId(ancestry, delimeter));
            System.out.print("'" + deck.getName() + "'"); 
        }
        printMenu(view);

    }

    private void initializeCreateDeckMenu(View view) {
        
        // 이전 화면 메시지 지우기
        View.clearScreen();
        // 보관함 생성 메뉴 출력하기
        printMenu(view);

    }

    private void initializeUpdateDeckMenu(View view) {
        
        // 이전 화면 메시지 지우기
        View.clearScreen();
        // 보관함 생성 메뉴 출력하기
        printMenu(view);

    }

    private void initializeDeleteDeckMenu(View view, DeckDAO deckDAO, String ancestry, String delimeter) {
        
        // 이전 화면 메시지 지우기
        View.clearScreen();
        // 대상 보관함 불러오기
        String[] splited = ancestry.split(delimeter);
        Deck deck = deckDAO.getDeckById(Integer.valueOf(splited[splited.length-1]));
        decks.add(deck);
        System.out.print("'" + deck.getName() + "'");
        // 보관함 삭제 메뉴 출력하기
        printMenu(view);

    }

    private String chooseDeleteDeck(View view, DeckDAO deckDAO, String ancestry, String delimeter) {

        if (InputController.isYesOrNo(view)) {
            view.printLoading();
            deckDAO.deleteDeck(decks.get(decks.size()-1));
            View.pause(1);
            return getParentDeckAncestry(ancestry, delimeter);
            
        } else {
            ((DeleteDeckView) view).printCancelDelete();
            View.pause(1);
            return ancestry;
        }
        
    }

    private int getCurrentDeckId(String ancestry, String delimeter) {
        String[] splited = ancestry.split(delimeter);
        return Integer.parseInt(splited[splited.length-1]);
    }

    private String getParentDeckAncestry(String ancestry, String delimeter) {

        int lastDelimeterIndex = ancestry.lastIndexOf(delimeter);
        lastDelimeterIndex = ancestry.substring(0, lastDelimeterIndex).indexOf(delimeter);
        return ancestry.substring(0, lastDelimeterIndex + delimeter.length());

    }

    private String moveToChildDeck(DeckMenu deckMenu, String delimeter) {

        Deck selectedDeck = null;
        for (Deck deck : decks) {
            if (deck.getName().equals(deckMenu.getName())) {
                selectedDeck = deck;
                break;
            }
        }

        System.out.println(deckMenu.getName() + "으로 이동합니다.");
        return selectedDeck.getId() + delimeter;

    }

    private void createDeck(View view, DeckDAO deckDAO, User user, String ancestry) {
        // 새로운 덱 이름 입력 받기
        String deckName = null;
        do {
            deckName = InputController.getUserTextInput();
        } while(isDuplicatedDeckName(view, deckDAO, user, deckName));

        view.printLoading();

        // 데이터베이스에 저장
        deckDAO.insertDeck(new Deck(deckDAO.getLastDeckId() + 1, user.getId(), deckName, ancestry));
        View.pause(2);
    }

    private void updateDeck(View view, DeckDAO deckDAO, User user, int deckId) {
        // 새로운 덱 이름 입력 받기
        String deckName = null;
        do {
            deckName = InputController.getUserTextInput();
        } while(isDuplicatedDeckName(view, deckDAO, user, deckName));

        view.printLoading();

        // 데이터베이스에 저장
        deckDAO.updateDeck(new Deck(deckId, user.getId(), deckName, ""));
        View.pause(2);
    }

    private boolean isDuplicatedDeckName(View view, DeckDAO deckDAO, User user, String deckName) {
        boolean isDuplicate = deckDAO.isDuplicatedDeckName(user, deckName);

        if (isDuplicate) {
            System.out.print("'" + deckName + "'은 ");
            view.printError();
            View.pause(2);
        }

        return isDuplicate;
    }

    private void printMenu(View view) {
        view.printFirstMessage();
        view.printMenu();
    }

    private void exit() {
        InputController.getInstance().closeScanner();
        System.exit(0);
    }

}