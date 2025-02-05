package service;

import model.Book;
import model.Role;
import model.User;
import utils.MyList;

public interface LibraryService {


     // обновить статус
    boolean userStatusUpdate(String email, Role role);

     // обновить пароль
    boolean updatePassword(String email, String newPassword);

     // пользователь заблокирован
    boolean isUserBlocked(String email);

     // существует ли такой email
    boolean isEmailExist(String email);

     // взять книгу
    Book borrowBook(int bookId);

     // вернуть книгу
    Book returnBook(int bookId);

    // удалить пользователя
    boolean removeUser(String email);

     // залогиниться
    boolean loginUser (String email, String password);

     // вылогиниться
    void logoutUser();

     // удалить книгу
    boolean removeBook(int id);
//============================================================
    //Надо проверять, что пользователь залогинился как админ
    Book addBook(String title, String author);

    boolean registerUser(String email, String password);

    MyList<Book> searchBooksByTitle(String title);

    MyList<Book> searchBooksByAuthor(String author);

    //список доступных книг
    MyList<Book> listAvailableBooks();

    // список всех книг для меню администратора
    MyList<Book> listAllBooksAdmin();

     // получить активного пользователя
   User getActiveUser();

     // список всех пользователей
    MyList<User> userList();

     // редактировать (изменить) книги
    boolean bookUpdateById(int id,String title,String author);

    // список книг пользователя
    public MyList<Book> getBooksByUser(String email);

    //Поиск Пользователя у которого книга
    public String findUserByBookId(int id);

    // список занятых книг
    MyList<Book> listBusyBooks();
}
