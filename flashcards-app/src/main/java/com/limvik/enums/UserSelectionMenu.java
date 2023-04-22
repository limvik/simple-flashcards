package com.limvik.enums;

import java.sql.SQLException;

import com.limvik.dao.DatabaseConnection;
import com.limvik.dao.UserDAO;

public enum UserSelectionMenu implements Menu {
    
    BEFORE("이전 메뉴로 돌아가기"),
    EXIT("종료");

    private final String description;

    UserSelectionMenu(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // 선택된 메인 메뉴 유효성 검사용 정규표현식 반환
    @Override
    public String getMenuRegex() {
        UserDAO userDAO = null;
        int userNumber = 0;
        try {
            userDAO = new UserDAO(DatabaseConnection.getInstance().getConnection());
            userNumber = userDAO.getUserSize();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        return "[1-" + UserMenu.values().length + userNumber + "]";
    }
}
