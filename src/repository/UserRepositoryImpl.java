package repository;

import model.Role;
import model.User;
import utils.MyArrayList;
import utils.MyList;

import java.util.Objects;

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
    return null;
    }

    @Override
    public void removeUser(String email) {

    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public MyList<User> getAllUsers() {
        return null;
    }

    @Override
    public boolean isEmailExist(String email) {
        return false;
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        return false;
    }


}
