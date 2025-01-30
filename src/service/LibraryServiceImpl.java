package service;

import model.Book;
import model.Role;
import model.User;
import repository.BookRepository;
import repository.BookRepositoryImpl;
import repository.UserRepository;
import utils.MyArrayList;
import utils.MyList;
import utils.PersonValidation;

public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private User activeUser;

    public LibraryServiceImpl(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    /**
     * @param email
     * @param password
     * @return
     * @Lena войти в систему
     * авторизация пользователя
     */
    @Override
    public boolean loginUser(String email, String password) {
        if (email == null || password == null) return false;

        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            System.out.println("email введен неверно.");
            return false;
        }

        if (!user.getPassword().equals(password)) {
            System.out.println("password введен неверно.");
            return false;
        }
        if (user.getRole() == Role.BLOCKED) {
            System.out.println("Ваша учётная запись заблокирована.");
            return false;
        }
        activeUser = user;

        return true;
    }

    /**
     * @Lena выйти из системы
     * вылогиниться
     */
    @Override
    public void logoutUser() {
        activeUser = null;
    }


    @Override
    public boolean removeBook(int id) {

        if (activeUser == null || activeUser.getRole() != Role.ADMIN) {
            System.out.println("Удалять книги может только администратор.");
            return false;
        }

        Book book = bookRepository.findBookById(id);
        if (book == null) {
            System.out.println("Книга не найдена.");
            return false;
        }

        bookRepository.removeBook(book.getId());
        System.out.println("Книга с ID " + id + " успешно удалена.");
        return true;
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
        for (Book book : existingBooks) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Book with the same title and author has already existed in the system: " + book);
                return null;
            }
        }

        // добавляем книгу
        Book newBook = bookRepository.addBook(title, author);
        System.out.println("Book was added successfully: " + newBook);
        return newBook;

    }


    /**
     * @param bookId
     * @return
     * @Lena взять книгу
     */
    @Override
    public Book borrowBook(int bookId) {
        Book book = bookRepository.findBookById(bookId);
        if (book != null) {
            if (book.isAvailable() == true) {
                book.setAvailable(false);
                return book;
            }
        }
        System.out.println("Такая книга не существует.");

        return null;
    }

    /**
     * @param bookId
     * @return
     * @Lena вернуть книгу
     */
    @Override
    public Book returnBook(int bookId) {
        Book book = bookRepository.findBookById(bookId);

        if (book != null) {
            if (book.isAvailable() == false) {
                book.setAvailable(true);
                return book;
            }
        }
        System.out.println("Такая книга не существует.");

        return null;
    }

    /**
     * @param email
     * @param password
     * @return
     * @Lena зарегистрировать пользователя
     */
    @Override
    public User registerUser(String email, String password) {
        if (!PersonValidation.isEmailValid(email)) {
            System.out.println("Некорректно введен email.");
            return null;
        }
        if (!PersonValidation.isPasswordValid(password)) {
            System.out.println("Некорректно введен пароль.");
            return null;
        }
        if (userRepository.isEmailExist(email)) {
            System.out.println("Пользователь с таким email уже существует.");
            return null;
        }

        User user = userRepository.addUser(email, password);

        return user;
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
        if ((activeUser == null) || (activeUser.getRole() != Role.ADMIN)) {
            System.out.println("Access denied for user. Only ADMIN has right to add a new book!");
            return null;
        }
        return bookRepository.getAllBooks();
    }
}
