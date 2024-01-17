package jm.task.core.jdbc.util;

import jm.task.core.jdbc.ConnectionData;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    static SessionFactory sessionFactory = null;
    static Properties props = new Properties();

    public static Connection getPostgresConnection() {
        /*try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/
        props.setProperty("user", ConnectionData.USER);
        props.setProperty("password", ConnectionData.PASSWORD);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(ConnectionData.URL, props);
        } catch (SQLException e) {
            System.out.println("[-] Нет подключения к базе данных");
            e.printStackTrace();
        }
        return conn;
    }

    public static void closePostgresConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("[-] Не удалось закрыть соединение с базой данных");
                e.printStackTrace();
            }
        }
    }

    public static Properties getHibernateProps() {
        props.setProperty("hibernate.connection.driver_class", ConnectionData.DRIVER);
        props.setProperty("hibernate.connection.url", ConnectionData.URL);
        props.setProperty("hibernate.connection.username", ConnectionData.USER);
        props.setProperty("hibernate.connection.password", ConnectionData.PASSWORD);
        props.setProperty("hibernate.dialect", ConnectionData.DIALECT);
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.hbm2ddl", "validate");
        return props;
    }

    public static Configuration getHibernateConfig(Properties props) {
        Configuration hibernateConfiguration = new Configuration();
        hibernateConfiguration.setProperties(props);
        hibernateConfiguration.addAnnotatedClass(User.class);
        return hibernateConfiguration;
    }

    public static SessionFactory createSessionFactory() {
        Properties props = Util.getHibernateProps();
        Configuration hibernateConfig = Util.getHibernateConfig(props);

        /*ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(hibernateConfig.getProperties())
                .build();
        sessionFactory = hibernateConfig.buildSessionFactory(serviceRegistry);*/

        sessionFactory = hibernateConfig.buildSessionFactory();
        return sessionFactory;
    }

    public static Session getHibernateSession() {
        return Util.createSessionFactory().openSession();
    }

    public static void shutdownSessionFactory() {
        if(sessionFactory != null) {
            sessionFactory.close();
        }
    }

}
