package service;

import model.Book;
import model.User;
import utils.MyList;

public interface LibraryService {
    //взять книгу
    void borrowBook(int bookId, int userId);
    //вернуть книгу
    void returnBook(int bookId, int userId);
    //зарегистрировать пользователя
    User registerUser(String email, String password);
    //залогиниться
    boolean loginUser (String email, String password);
    //вылогиниться
    void logoutUser();

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



}
