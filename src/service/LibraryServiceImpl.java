package service;

import model.Book;
import model.Role;
import model.User;
import repository.BookRepository;
import repository.BookRepositoryImpl;
import repository.UserRepository;
import utils.MyArrayList;
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

        // проверяем права доступа для пользователей
        if ((activeUser == null) || (activeUser.getRole() != Role.ADMIN)) {
            System.out.println("Access denied for user. Only ADMIN has right to add a new book!");
            return null;
        }

        // проверяем, что название книги и автор валидны
        if ((title == null) || (title.trim().isEmpty())) {
            System.out.println("Title cannot be null or empty");
            return null;
        }

        if ((author == null) || (author.trim().isEmpty())) {
            System.out.println("Author cannot be null or empty");
            return null;
        }

        // проверяем, что нет такой же книги в системе
        MyList<Book> existingBooks = bookRepository.findBooksByAuthor(author); // получаем книги из репозитория
        for (Book book: existingBooks){
            if (book.getTitle().equalsIgnoreCase(title)){
                System.out.println("Book with the same title and author has already existed in the system: " + book);
                return null;
            }
        }

        // добавляем книгу
        Book newBook = bookRepository.addBook(title, author);
        System.out.println("Book was added successfully: " + newBook);
        return newBook;

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
        // проверяем валидность названия книги
        if ((title == null) || (title.trim().isEmpty())) {
            System.out.println("Title cannot be null or empty");
            return null;
        }
        return bookRepository.findBooksByTitle(title);
    }

    @Override
    public MyList<Book> searchBooksByAuthor(String author) {
        // проверяем валидность имени автора
        if ((author == null) || (author.trim().isEmpty())) {
            System.out.println("Author cannot be null or empty");
            return null;
        }
        return bookRepository.findBooksByAuthor(author);
    }

    @Override
    public MyList<Book> listAvailableBooks() {
        return bookRepository.getAvailableBooks();
    }

    @Override
    public MyList<Book> listAllBooksAdmin() {
        if ((activeUser == null) || (activeUser.getRole() != Role.ADMIN)){
            System.out.println("Access denied for user. Only ADMIN has right to add a new book!");
            return null;
        }
        return bookRepository.getAllBooks();
    }
}
