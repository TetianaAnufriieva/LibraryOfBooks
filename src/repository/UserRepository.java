package repository;

import model.Role;
import model.User;
import utils.MyList;

import java.util.List;

public interface UserRepository {

    // CRUD

    //добавление пользователя
    User addUser(String email, String password);

    //удаление
    void removeUser(String email);

    User findUserByEmail(String email);

    // список всех пользователей
    MyList<User> getAllUsers();


    // существует ли такой email
    boolean isEmailExist(String email);

    // обновление пароля
    boolean updatePassword(String email, String newPassword);

    /**
     * @Lena
     * обновление статуса
     * @param email
     * @param role
     */
    public void userStatusUpdate(String email, Role role);
}
