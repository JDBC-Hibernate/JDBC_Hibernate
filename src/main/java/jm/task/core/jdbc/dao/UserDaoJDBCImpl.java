package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id SERIAL PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "lastname VARCHAR(255) NOT NULL, " +
                "age INTEGER NOT NULL);";
        try (Connection conn = Util.getPostgresConnection();
             Statement statement = conn.createStatement()) {

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("[-] Create users table id failed!");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users;";
        try (Connection conn = Util.getPostgresConnection();
             Statement statement = conn.createStatement()) {

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("[-] Drop users table is failed!");
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(id, name, lastname, age) " +
                "VALUES(DEFAULT, ?, ?, ?);";
        try (Connection conn = Util.getPostgresConnection();
             PreparedStatement pStatement = conn.prepareStatement(sql)) {

            pStatement.setString(1, name);
            pStatement.setString(2, lastName);
            pStatement.setByte(3, age);
            pStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("[-] Пользователь не сохранен!");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id=?;";
        try (Connection conn = Util.getPostgresConnection();
             PreparedStatement pStatement = conn.prepareStatement(sql)) {

            pStatement.setLong(1, id);
            int rows = pStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("[+] Пользователь успешно удален");
            } else {
                System.out.println("[+] Пользователь не найден!");
            }
        } catch (SQLException e) {
            System.out.println("[-] removeUserById -> " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> resultList = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = Util.getPostgresConnection();
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
            System.out.println("[-] getAllUsers -> " + e.getMessage());
        }
        return resultList;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users;";
        try (Connection conn = Util.getPostgresConnection();
             Statement statement = conn.createStatement()) {

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("[-] Таблица не очищена!");
            e.printStackTrace();
        }
    }
}
