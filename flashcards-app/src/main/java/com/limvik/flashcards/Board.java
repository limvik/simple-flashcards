package com.limvik.flashcards;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.limvik.dao.UserDAO;

public class Board {

    private List<Deck> decks; // 보관함 목록
    private List<Plan> plans; // 학습 일정 목록
    private List<User> users; // 사용자 목록

    // 사용자 목록을 보여라
    public void showUserList() {

        // 사용자 목록 불러오기
        try {
            users = new UserDAO().getAllUsers();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // 사용자가 없을 경우
        if (users.size() == 0) {
            // 새로운 사용자 생성 화면 시현
        } else {
            // 사용자 목록 선택 화면 시현
        }

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