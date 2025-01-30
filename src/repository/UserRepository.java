package repository;

import model.User;
import utils.MyList;

import java.util.List;

public interface UserRepository {

    // CRUD

    //добавление пользователя
    User addUser(String email, String password);

    //удаление
    boolean removeUser(String email);

    User findUserByEmail(String email);

    // список всех пользователей
    MyList<User> getAllUsers();


    // существует ли такой email
    boolean isEmailExist(String email);

    // обновление пароля
    boolean updatePassword(String email, String newPassword);

}
