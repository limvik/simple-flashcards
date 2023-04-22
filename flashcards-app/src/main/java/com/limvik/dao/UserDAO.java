package com.limvik.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.limvik.flashcards.User;

public class UserDAO {

    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    } 

    public List<User> getAllUsers() {

        // 반환할 사용자 목록 변수
        List<User> users = new ArrayList<>();
        // 전체 사용자 목록을 불러오기 위한 SQL
        String sql = "SELECT * FROM Users";
        // SQL 실행 및 데이터 저장
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(new User(resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public int getUserSize() {

        // 사용자 수를 조회하기 위한 SQL
        String sql = "SELECT COUNT(*) AS user_size FROM Users";

        // SQL 실행 및 데이터 저장
        int userSize = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                userSize = resultSet.getInt("user_size");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userSize;
    }

    public int insertUser(User user) {

        // 신규 사용자 추가를 위한 SQL
        String sql = "INSERT INTO Users VALUES (?, ?)";
        int rowCount = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getName());
            rowCount = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowCount;

    }

    public int deleteUser(int userId) {

        // 사용자 삭제를 위한 SQL
        String sql = "DELETE FROM users WHERE id = ?";
        int rowCount = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            rowCount = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowCount;
    }

    public int updateUser(User user) {

        // 사용자 이름 수정을 위한 SQL
        String sql = "UPDATE Users SET name = ? WHERE id = ?";
        int rowCount = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setInt(2, user.getId());
            rowCount = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowCount;
    }

}
