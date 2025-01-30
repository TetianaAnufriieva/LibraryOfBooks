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
     * @return
     * @Lena получить активного пользователя
     */
    public User getActiveUser() {
        return activeUser;
    }

    /**
     * @return
     * @Lena получить список всех пользователей
     */
    @Override
    public MyList<User> userList() {
        MyList<User> list = userRepository.getAllUsers();
        if (list != null) {
            return list;
        }
        return null;
    }

    /**
     * @param id
     * @param title
     * @param author
     * @return
     * @Lena редактировать (изменить) книги
     */
    @Override
    public boolean bookUpdateById(int id, String title, String author) {
        Book book = bookRepository.findBookById(id);
        if (book != null) {
            bookRepository.bookUpdateById(id, title, author);
            return true;
        } else {
            System.out.println("Такая книга не существует.");
        }
        return false;
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
        // Проверим есть ли такой пользователь
        if (!userRepository.isEmailExist(email)) {
            System.out.println("Пользователя с Email - " + email + " нет ! Сначала зарегистрируйтесь");
            return false;
        }

        User user = userRepository.findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            if (user.getRole() == Role.ADMIN) {
                activeUser = user;
                activeUser.setRole(Role.ADMIN);
                return true;
            }
            if (user.getRole() == Role.USER) {
                activeUser = user;
                activeUser.setRole(Role.USER);
                return true;
            }
            if (user.getRole() == Role.BLOCKED) {
                System.out.println("Вы ЗАБЛОКИРОВАНЫ ! Обратитесь к администратору");
                return false;
            }
        }
        System.out.println("Пароль не верный");
        return false;
    }
//        if (email == null || password == null) return false;
//
//        User user = userRepository.findUserByEmail(email);
//
//        if (user == null) {
//            System.out.println("email введен неверно.");
//            return false;
//        }
//
//        if (!user.getPassword().equals(password)) {
//            System.out.println("password введен неверно.");
//            return false;
//        }
//        if (user.getRole() == Role.BLOCKED) {
//            System.out.println("Ваша учётная запись заблокирована.");
//            return false;
//        }
//        activeUser = user;
//
//        return true;
//    }

    /**
     * @Lena выйти из системы
     * вылогиниться
     */
    @Override
    public void logoutUser() {
        activeUser = null;
    }

    /**
     * @param id
     * @return
     * @Lena удалить книгу
     */
    @Override
    public boolean removeBook(int id) {
        int i = -1;
        for (Book book : bookRepository.getAllBooks()) {
            i++;
            if (book.getId() == id) {
                bookRepository.removeBook(i);
                return true;
            }
        }
        System.out.println("Такая книга не существует.");
        return false;
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
     * @param email
     * @param role
     * @return
     * @Lena обновить статус пользователя
     */
    @Override
    public boolean userStatusUpdate(String email, Role role) {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            userRepository.userStatusUpdate(email, role);
            return true;
        }
        return false;
    }

    /**
     * @param email
     * @param newPassword
     * @return
     * @Lena обновить пароль
     */
    @Override
    public boolean updatePassword(String email, String newPassword) {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            if (PersonValidation.isPasswordValid(newPassword)) {
                userRepository.updatePassword(email, newPassword);
                return true;
            } else {
                System.out.println("Некорректно введен пароль.");
            }
        }

        return false;
    }

    /**
     * @param email
     * @return
     * @Lena заблокировать пользователя
     */
    @Override
    public boolean isUserBlocked(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user.getRole() == Role.BLOCKED) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param email
     * @return
     * @Lena существует ли такой email
     */
    @Override
    public boolean isEmailExist(String email) {
        return userRepository.isEmailExist(email);
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
            } else {
                System.out.println("Книга уже взята.");
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
            } else {
                System.out.println("Книга уже возвращена.");
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
    public boolean registerUser(String email, String password) {
        if (!PersonValidation.isEmailValid(email)) {
            System.out.println("Некорректно введен email.");
            return false;
        }
        if (!PersonValidation.isPasswordValid(password)) {
            System.out.println("Некорректно введен пароль.");
            return false;
        }
        if (userRepository.isEmailExist(email)) {
            System.out.println("Пользователь с таким email уже существует.");
            return false;
        }

        User user = userRepository.addUser(email, password);
        if (activeUser == null) {
            activeUser = user;
        }

        return true;
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
