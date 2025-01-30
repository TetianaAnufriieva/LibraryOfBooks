package repository;

import model.Role;
import model.User;
import utils.MyList;


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

    // обновление статуса
    public void userStatusUpdate(String email, Role role);
}
