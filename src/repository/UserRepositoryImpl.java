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


        User user = new User(email, password);
        users.add(user);
        return user;

    }
    @Override
    public boolean removeUser(String email) {
        User userToRemove = findUserByEmail(email);
        users.remove(userToRemove);
        return true;
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
        return users;

    }

    @Override
    public boolean isEmailExist(String email) {
 
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
 
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        User user = findUserByEmail(email);
        user.setPassword(newPassword);
        return true;
    }

    @Override
    public void userStatusUpdate(String email, Role role) {
        User user = findUserByEmail(email);
        user.setRole(role);
    }
}

