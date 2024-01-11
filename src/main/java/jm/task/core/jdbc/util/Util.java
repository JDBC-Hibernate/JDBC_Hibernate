package jm.task.core.jdbc.util;

import jm.task.core.jdbc.ConnectionData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соединения с БД

    public Connection getPostgresConnection() {
        /*try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/
        Connection conn = null;
        Properties props = new Properties();
        props.setProperty("user", ConnectionData.USER);
        props.setProperty("password", ConnectionData.PASSWORD);
        try {
            conn = DriverManager.getConnection(ConnectionData.URL, props);
        } catch (SQLException e) {
            System.out.println("[!] Нет подключения к базе данных");
            e.printStackTrace();
        }
        return conn;
    }

    public void closePostgresConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("[!] Не удалось закрыть соединение с базой данных");
                e.printStackTrace();
            }
        }
    }

}
