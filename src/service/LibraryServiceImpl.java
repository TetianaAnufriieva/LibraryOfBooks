package service;

import model.Book;
import model.User;
import repository.BookRepository;
import repository.UserRepository;
import utils.MyList;

public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private User activeUser;

    public LibraryServiceImpl(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean loginUser(String email, String password) {
        return false;
    }

    @Override
    public void logoutUser() {

    }

    @Override
    public Book addBook(String title, String author) {
        return null;
    }


    @Override
    public void borrowBook(int bookId, int userId) {

    }

    @Override
    public void returnBook(int bookId, int userId) {

    }

    @Override
    public User registerUser(String email, String password) {
        return null;
    }


    @Override
    public MyList<Book> searchBooksByTitle(String title) {
        return null;
    }

    @Override
    public MyList<Book> searchBooksByAuthor(String author) {
        return null;
    }

    @Override
    public MyList<Book> listAvailableBooks() {
        return null;
    }

    @Override
    public MyList<Book> listAllBooksAdmin() {
        return null;
    }
}
