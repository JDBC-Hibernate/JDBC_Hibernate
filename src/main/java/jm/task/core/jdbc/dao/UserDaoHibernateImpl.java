package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id SERIAL PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "lastname VARCHAR(255) NOT NULL, " +
                "age INTEGER NOT NULL);";
        Session session = Util.getHibernateSession();
        Transaction transaction = null;
        try(session) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("[+] Таблица успешно создана");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users;";
        Session session = Util.getHibernateSession();
        Transaction transaction = null;
        try(session) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("[+] Таблица успешно удалена");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getHibernateSession();
        User user = new User(name, lastName, age);
        Transaction transaction = null;
        try(session) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("[+] User с именем " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("[-] User с именем " + name + " не сохранен!");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getHibernateSession();
        Transaction transaction = null;
        try(session) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
            System.out.println("[+] User успешно удален!");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String queryString = "FROM User";
        Session session = Util.getHibernateSession();
        List<User> userList = null;
        Transaction transaction = null;
        try(session) {
            transaction = session.beginTransaction();
            userList = session.createQuery(queryString).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String queryString = "DELETE FROM User";
        Session session = Util.getHibernateSession();
        Transaction transaction = null;
        try(session) {
            transaction = session.beginTransaction();
            session.createQuery(queryString).executeUpdate();
            transaction.commit();
            System.out.println("[+] Таблица users успешно очищена");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
