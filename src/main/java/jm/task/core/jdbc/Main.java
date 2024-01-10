package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        User user1 = new User("John", "Lennon", (byte) 45);
        User user2 = new User("Buddy", "Holly", (byte) 50);
        User user3 = new User("Ringo", "Starr", (byte) 44);
        User user4 = new User("George", "Harrison", (byte) 35);

        userDao.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userDao.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userDao.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userDao.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        System.out.println("User с именем " + user1.getName() +
                " добавлен в базу данных");
        System.out.println("User с именем " + user2.getName() +
                " добавлен в базу данных");
        System.out.println("User с именем " + user3.getName() +
                " добавлен в базу данных");
        System.out.println("User с именем " + user4.getName() +
                " добавлен в базу данных");

        List<User> usersList = userDao.getAllUsers();
        usersList.forEach(System.out::println);

        userDao.removeUserById(3);
        List<User> newUsersList = userDao.getAllUsers();
        newUsersList.forEach(System.out::println);

//        userDao.cleanUsersTable();
//        userDao.dropUsersTable();


    }
}
