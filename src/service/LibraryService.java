package service;

import model.Book;
import model.User;
import utils.MyList;

public interface LibraryService {

    /**
     * @Lena
     * пользователь заблокирован
     * @param email
     * @return
     */
    boolean isUserBlocked(String email);

    /**
     * @Lena
     * существует ли такой email
     * @param email
     * @return User
     */
    boolean isEmailExist(String email);

    /**
     * @Lena
     * взять книгу
     * @param bookId
     * @return Book
     */
    Book borrowBook(int bookId);

    /**
     * @Lena
     * вернуть книгу
     * @param bookId
     * @return Book
     */
    Book returnBook(int bookId);

    /**
     * @Lena
     * зарегистрировать пользователя
     * @param email
     * @param password
     * @return User
     */
    boolean registerUser(String email, String password);

    /**
     * @Lena
     * залогиниться
     * @param email
     * @param password
     * @return boolean
     */
    boolean loginUser (String email, String password);

    /**
     * @Lena
     * вылогиниться
     */
    void logoutUser();

    /**
     * @Lena
     * удалить книгу
     * @param id
     * @return boolean
     */
    boolean removeBook(int id);
//============================================================
    //todo не void. Надо проверять, что пользователь залогинился как админ
    Book addBook(String title, String author);

    // todo contains or equals
    MyList<Book> searchBooksByTitle(String title);

    // todo contains
    MyList<Book> searchBooksByAuthor(String author);

    //список доступных книг
    MyList<Book> listAvailableBooks();

    // todo список всех книг для меню администратора
    MyList<Book> listAllBooksAdmin();

    /**
     * @Lena
     * получить активного пользователя
     * @return
     */
   User getActiveUser();
}
