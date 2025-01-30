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
    public boolean removeUser(String email) {
        User user = findUserByEmail(email);
        if (user != null) {
            users.remove(user);
        }


        @Override
        public User findUserByEmail (String email){
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    return user;
                }
            }
            return null; // Пользователь не найден
        }

        @Override
        public MyList<User> getAllUsers () {
            return users;

        }

        @Override
        public boolean isEmailExist (String email){
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean updatePassword (String email, String newPassword){
            User user = findUserByEmail(email);
            if (user == null) {
                System.out.println("Пользователь не найден.");
                return false;
            }

            if (user.getRole() == Role.BLOCKED) {
                System.out.println("Blocked users cannot change passwords.");
                return false;
            }

            if (newPassword == null || newPassword.trim().isEmpty()) {
                System.out.println("New password cannot be empty or null.");
                return false;
            }

            user.setPassword(newPassword);
            System.out.println("Password updated successfully");
            return true;


        }
    }
}
