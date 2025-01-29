package repository;

import model.Role;
import model.User;
import utils.MyArrayList;
import utils.MyList;

public class UserRepositoryImpl implements UserRepository {

    private final MyList<User> users;

    public UserRepositoryImpl() {
        users = new MyArrayList<>();
        addUsers();
    }

    private void addUsers() {
        User admin = new User("1", "1");
        admin.setRole(Role.ADMIN);

        User blocked = new User("3", "3");
        blocked.setRole(Role.BLOCKED);

        users.addAll(
                admin,
                blocked,
                new User("2", "2")
        );
    }


    @Override
    public User addUser(String email, String password) {
        // Проверяем, существует ли уже пользователь с таким email
        if (isEmailExist(email)) {
            return null; // или выбросить исключение
        }

        User user = new User(email, password);
        users.add(user);
        return user;

    }

    @Override
    public void removeUser(String email) {
        users.removeIf(user -> user.getEmail().equals(email));
    }

    @Override
    public User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null; // Пользователь не найден
    }

    @Override
    public MyList<User> getAllUsers() {
        // Предполагаем, что MyList - это обертка над ArrayList
        MyList<User> myList = new MyList<>();
        myList.addAll((User) users);
        return myList;
    }

    @Override
    public boolean isEmailExist(String email) {
        return users.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        User user = findUserByEmail(email);
        if (user != null) {
            user.setPassword(newPassword);
            return true;
        }
        return false;
    }

}
