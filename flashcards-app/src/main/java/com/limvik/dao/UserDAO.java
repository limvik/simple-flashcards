package com.limvik.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.limvik.flashcards.User;

public class UserDAO {
    public List<User> getAllUsers() throws SQLException{
        // 데이터베이스 인스턴스 획득
        Connection connection = DatabaseConnection.getInstance().getConnection();
        // 반환할 사용자 목록 변수
        List<User> users = new ArrayList<>();
        // 전체 사용자 목록을 불러오기 위한 SQL
        String sql = "SELECT * FROM users";
        // SQL 실행 및 데이터 저장
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User(resultSet.getInt("id"), resultSet.getString("name"));
                users.add(user);
            }
        }

        return users;
    }
}
