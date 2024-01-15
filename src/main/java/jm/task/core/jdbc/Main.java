package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        User user1 = new User("John", "Lennon", (byte) 45);
        User user2 = new User("Buddy", "Holly", (byte) 50);
        User user3 = new User("Ringo", "Starr", (byte) 44);
        User user4 = new User("George", "Harrison", (byte) 35);

        // *** JDBC ***
        /*UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userDao.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userDao.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userDao.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        List<User> usersList = userDao.getAllUsers();
        usersList.forEach(System.out::println);

        userDao.removeUserById(3);
        List<User> newUsersList = userDao.getAllUsers();
        newUsersList.forEach(System.out::println);

        userDao.cleanUsersTable();
        userDao.dropUsersTable();*/

        // *** Hibernate ***
        UserDao userDao = new UserDaoHibernateImpl();

        // createUsersTable
        userDao.createUsersTable();

        // saveUser
        userDao.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userDao.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userDao.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userDao.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        // getAllUsers
        List<User> usersList = userDao.getAllUsers();
        usersList.forEach(System.out::println);

        // removeUserById
        userDao.removeUserById(2);

        // cleanUsersTable
        userDao.cleanUsersTable();

        // dropUsersTable
        userDao.dropUsersTable();

    }
}
