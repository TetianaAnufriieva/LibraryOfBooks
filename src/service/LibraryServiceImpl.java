package service;

import model.Book;
import model.Role;
import model.User;
import repository.BookRepository;
import repository.UserRepository;
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

     // получить активного пользователя
    public User getActiveUser() {
        return activeUser;
    }
 

     // получить список всех пользователей
    @Override
    public MyList<User> userList() {
        if (activeUser == null || activeUser.getRole() != Role.ADMIN) {
            System.out.println("Получить список пользователей может только администратор.");
            return null;
        }
            return userRepository.getAllUsers();
    }

 // редактировать (изменить) книги
    @Override
    public boolean bookUpdateById(int id, String title, String author) {

        if (activeUser == null || activeUser.getRole() != Role.ADMIN) {
            System.out.println("Редактировать книги может только администратор.");
            return false;
        }
        Book book = bookRepository.findBookById(id);
        if (book == null) {
            System.out.println("Такая книга не существует.");
            return false;
        }
        if (book.isAvailable() == false) {
            System.out.println("Книга занята.");
            return false;
        }
        bookRepository.bookUpdateById(id, title, author);
        return true;

    }


    // войти в систему авторизация пользователя
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
                System.out.println("Вы заблокированы! Обратитесь к администратору.");
                return false;
            }
        }
        System.out.println("Пароль не верный!");
        return false;
    }
 
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
        if (book.isAvailable() == false) {
            System.out.println("Книга занята.");
            return false;
        }

        bookRepository.removeBook(book.getId());
        System.out.println("Книга с ID " + id + "  Автор: '"+book.getAuthor()
                + "'  Название:'"+book.getTitle() + "' успешно удалена.");
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
        for (Book book : bookRepository.getAllBooks()) {
            if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author)) {
                System.out.println("Book with the same title and author has already existed in the system: " + book);
                return null;
            }
        }
        // добавляем книгу
        Book newBook = bookRepository.addBook(title, author);
        System.out.println("Книга: " + newBook);
        return newBook;

    }


 
      //обновить статус пользователя
    @Override
    public boolean userStatusUpdate(String email, Role role) {

        if ((activeUser == null) || (activeUser.getRole() != Role.ADMIN)) {
            System.out.println("Изменить статус пользователя может только администратор!");
            return false;
        }
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            userRepository.userStatusUpdate(email, role);
            return true;
        }
        return false;
    }

      // обновить пароль
    @Override
    public boolean updatePassword(String email, String newPassword) {

        if ((activeUser == null) || (activeUser.getRole() != Role.ADMIN)) {
            System.out.println("Изменить пароль пользователя может только администратор!");
            return false;
        }

        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            if (PersonValidation.isPasswordValid(newPassword)) {
                userRepository.updatePassword(email, newPassword);
                return true;
            } else {
                System.out.println("Пароль - "+newPassword+" не корректный.");
            }
        } else {
            System.out.println("Пользователя с Email - "+email+" не существует!");
        }
        return false;
    }

      //заблокировать пользователя
    @Override
    public boolean isUserBlocked(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user != null && user.getRole() == Role.BLOCKED) {
            return true;
        } else {
            return false;
        }
    }

      // существует ли такой email
    @Override
    public boolean isEmailExist(String email) {
        return userRepository.isEmailExist(email);
    }

 
    @Override
    public Book borrowBook(int bookId) {
        Book book = bookRepository.findBookById(bookId);
        if (book != null) {
            if (book.isAvailable() == true) {
                book.setAvailable(false);
                book.setUserUse(activeUser.getEmail());
                return book;
            } else {
                System.out.println("Книга с id: " + bookId + " занята");
            }
        } else {
            System.out.println("Книга с id: " + bookId + " не существует");
        }
        return null;
    }
 
 
    @Override
    public Book returnBook(int bookId) {
        Book book = bookRepository.findBookById(bookId);

        if (book != null) {
            if (book.isAvailable() == false
                    && book.getUserUse().equals(activeUser.getEmail())==true) {
                book.setAvailable(true);
                book.setUserUse(null);
                return book;
            }   else {
                System.out.println("Вы не брали книгу Название: " + book.getTitle() +
                        "  Автор: " + book.getAuthor() +"  ID: " + book.getId());
            }
        } else {
            System.out.println("Книга с id: " + bookId + " не существует");
        }

        return null;
    }
    @Override
    public boolean removeUser(String email) {
        if (activeUser == null || activeUser.getRole() != Role.ADMIN) {
            System.out.println("Удалять пользователей может только администратор.");
            return false;
        }
        boolean isDeletedUser;
        boolean isExist = isEmailExist(email);
        if(isExist == true) {
            if (activeUser.getEmail().equals(email) != true) {
                isDeletedUser = userRepository.removeUser(email);
                if (isDeletedUser == true) {
                    return true;
                }
            }
        } else {
            System.out.println("Пользователь - " + email + " не существует.");
        }
        return false;
    }


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
        return bookRepository.getAllBooks();
    }

    @Override
    public MyList<Book> getBooksByUser(String email) {
        MyList<Book> list = bookRepository.getBooksByUser(email);
        if (list != null) {
            return list;
        }
        return null;
    }

    @Override
    public String findUserByBookId(int id) {
        for (Book book : bookRepository.getAllBooks()) {
            if (book.getId() == id) {
                return book.getUserUse();
            }
        }
        System.out.println("Вы ввели не правильный id книги");
        return null;
    }

    @Override
    public MyList<Book> listBusyBooks() {
        return bookRepository.listBusyBooks();
    }
}
