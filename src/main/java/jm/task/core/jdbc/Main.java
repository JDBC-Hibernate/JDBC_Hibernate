package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        User user1 = new User("John", "Lennon", (byte) 45);
        User user2 = new User("Buddy", "Holly", (byte) 50);
        User user3 = new User("Ringo", "Starr", (byte) 44);
        User user4 = new User("George", "Harrison", (byte) 35);

        // *** Hibernate ***
        UserService userService = new UserServiceImpl();

        // createUsersTable
        userService.createUsersTable();

        // saveUser
        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        // getAllUsers
        List<User> usersList = userService.getAllUsers();
        usersList.forEach(System.out::println);

        // removeUserById
        userService.removeUserById(2);

        // cleanUsersTable
        userService.cleanUsersTable();

        // dropUsersTable
        userService.dropUsersTable();

        Util.shutdownSessionFactory();

    }
}
