package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id SERIAL PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "lastname VARCHAR(255) NOT NULL, " +
                "age INTEGER NOT NULL);";
        try (Connection conn = new Util().getPostgresConnection();
             Statement statement = conn.createStatement()) {

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("[ - ] UserServiceImpl -> Create users table is failed!");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users;";
        try (Connection conn = new Util().getPostgresConnection();
             Statement statement = conn.createStatement()) {

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("[ - ] UserServiceImpl -> Drop users table is failed!");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(id, name, lastname, age) " +
                "VALUES(DEFAULT, ?, ?, ?);";
        try (Connection conn = new Util().getPostgresConnection();
             PreparedStatement pStatement = conn.prepareStatement(sql)) {

            pStatement.setString(1, name);
            pStatement.setString(2, lastName);
            pStatement.setByte(3, age);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[ - ] UserServiceImpl -> Пользователь не сохранен!");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id=?;";
        try (Connection conn = new Util().getPostgresConnection();
             PreparedStatement pStatement = conn.prepareStatement(sql)) {

            pStatement.setLong(1, id);
            int rows = pStatement.executeUpdate();
            if (rows > 0) {
                System.out.println(
                        "[ + ] UserServiceImpl -> Пользователь успешно удален"
                );
            } else {
                System.out.println("[ + ] UserServiceImpl -> Пользователь не найден!");
            }
        } catch (SQLException e) {
            System.out.println("[ - ] UserServiceImpl -> removeUserById");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> resultList = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = new Util().getPostgresConnection();
             Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                byte age = resultSet.getByte("age");
                User user = new User(id, name, lastname, age);
                resultList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("[ - ] UserServiceImpl -> getAllUsers");
            e.printStackTrace();
        }
        return resultList;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users;";
        try (Connection conn = new Util().getPostgresConnection();
             Statement statement = conn.createStatement()) {

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("[ - ] UserServiceImpl -> Таблица не очищена!");
            e.printStackTrace();
        }
    }
}
