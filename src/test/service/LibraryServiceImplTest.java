package service;

import model.Book;
import model.Role;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.BookRepository;
import repository.BookRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import utils.MyList;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryServiceImplTest {

    private static LibraryService libraryService;
    private BookRepositoryImpl bookRepository;
    private UserRepositoryImpl userRepository;
    private Role activeUser;




    @BeforeAll
    static void setup() {
        BookRepository bookRepository = new BookRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();

        libraryService = new LibraryServiceImpl(bookRepository, userRepository);
    }

    @AfterEach
    void logout() {
        libraryService.logoutUser();
    }

    @Test
    void testAddBookWithoutActiveUser() {
        Book book = libraryService.addBook(null, null);
        assertNull(book);
    }

    @Test
    void testAddBookForNotAdmin() {
        libraryService.loginUser("3", "3");
        Book book = libraryService.addBook(null, null);
        assertNull(book);
    }

    @Test
    void testAddBookWithEmptyTitle() {
        libraryService.loginUser("1", "1");
        Book book = libraryService.addBook(null, null);
        assertNull(book);
    }

    @Test
    void testAddBookWithEmptyAuthor() {
        libraryService.loginUser("1", "1");
        Book book = libraryService.addBook("Test Title", null);
        assertNull(book);
    }

    @Test
    void testAddDuplicateBook() {
        libraryService.loginUser("1", "1");
        Book book = libraryService.addBook("1984", "George Orwell");
        assertNull(book);
    }

    @Test
    void testAddNewBook() {
        libraryService.loginUser("1", "1");
        Book book = libraryService.addBook("Test", "Tanja");
        assertNotNull(book);
    }

    @Test
    void registerUserWithInvalidEmail() {
        boolean result = libraryService.registerUser(".test@gmail.com", null);
        assertFalse(result);
    }


    @Test
    void registerUserWithInvalidPassword() {
        boolean result = libraryService.registerUser("test123@gmail.com", "111");
        assertFalse(result);
    }

    @Test
    void registerUserWithDuplicateEmail() {
        boolean result1 = libraryService.registerUser("test123@gmail.com", "123qweQWE!@#");
        assertTrue(result1);

        boolean result2 = libraryService.registerUser("test123@gmail.com", "123qweQWE!@#");
        assertFalse(result2);
    }

    @Test
    void registerNewUser() {
        boolean result = libraryService.registerUser("tanja@gmail.com", "Qwerty!123");
        assertTrue(result);

        // User activeUser = libraryService.getActiveUser();
        // assertEquals("tanja@gmail.com", activeUser.getEmail());
    }

    @Test
    void searchBooksByEmptyTitle() {
        MyList<Book> result = libraryService.searchBooksByTitle(null);
        assertNull(result);
    }

    @Test
    void searchBooksByInvalidTitle() {
        MyList<Book> result = libraryService.searchBooksByTitle("invalid");
        assertEquals(0, result.size());
    }

    @Test
    void searchBooksByValidTitle() {
        MyList<Book> result = libraryService.searchBooksByTitle("Pride");
        assertEquals(1, result.size());
        System.out.println(result.get(0));
    }

    //================HERMAN================

    //Метод testUserStatusUpdate() тестирует метод userStatusUpdate()
    // класса LibraryService.
    // Он создает объект Role и вызывает метод userStatusUpdate()
    // с тестовым электронным адресом и ролью.
    // Затем он утверждает, что результат равен true.
    @Test
    public void testUserStatusUpdate() {
        String email = "test@example.com";
        Role role = Role.ADMIN;
        boolean result = libraryService.userStatusUpdate(email, role);
        equals(result);
    }

    //Метод testUpdatePassword() тестирует метод updatePassword() класса LibraryService.
    //Он вызывает метод updatePassword() с тестовым электронным адресом и новым паролем.
    // Затем он утверждает, что результат равен true.
    @Test
    public void testUpdatePassword() {

        String email = "test@example.com";
        String newPassword = "newPassword";
        boolean result = libraryService.updatePassword(email, newPassword);
        equals(result);
    }
    //Метод testIsUserBlocked() тестирует метод isUserBlocked() класса LibraryService.
    // Он вызывает метод isUserBlocked() с тестовым электронным адресом.
    // Затем он утверждает, что результат равен true.
    @Test
    public void testIsUserBlocked() {
        String email = "test@example.com";
        boolean result = libraryService.isUserBlocked(email);
        equals(result);
    }

    //============================================

    @Test
    public void testLoginUser() {
        String email = "test@example.com";
        String password = "password";
        boolean result = libraryService.loginUser(email, password);
        equals(result);
    }

    @Test
    public void logoutUser() {
        activeUser = null;
    }


//    @Test
//    public void testRemoveBook_AdminUser_ValidBookId_ReturnsTrue() {
//        // Set up test data
//        User adminUser = new User("admin", Role.ADMIN.toString());
//        activeUser = adminUser;
//        bookRepository.addBook("book", "author");
//
//        // Call the method being tested
//        boolean result = libraryService.removeBook(1);
//
//        // Verify the result
//        equals(result);
//        assertNotNull(bookRepository.findBookById(1));
//    }
//
//    @Test
//    public void testRemoveBook_NonAdminUser_ValidBookId_ReturnsFalse() {
//        // Set up test data
//        User nonAdminUser = new User("nonadmin", Role.USER.toString());
//        activeUser = nonAdminUser;
//        bookRepository.addBook("book2", "author2");
//
//        // Call the method being tested
//        boolean result = libraryService.removeBook(1);
//
//        // Verify the result
//        assertFalse(result);
//        assertNotNull(bookRepository.findBookById(1));
//    }


    @Test
    void isEmailExist() {
        String isEmailExist = "test@gmail.com";
        //user.setEmail(isEmailExist);
        // boolean user = libraryService.isEmailExist("notest@gmail.com");

        assertFalse(libraryService.isEmailExist("testgmail.com"));
        equals(libraryService.isEmailExist("test@gmail.com"));


    }

//         @Test
//        void borrowBook() {
//
//
//            Book book = libraryService.borrowBook(1);
//            assertNotNull(book,"книга не null");
//
//          // assertFalse(book.isAvailable(),"книга не доступна");
//        }
//
//        @Test
//        void returnBook() {
//
//            Book book = libraryService.returnBook(1);
//            assertNull(book,"книга не null");
//            assertTrue(book.isAvailable(),"книга доступна");


 // }


    @BeforeEach
    public void init() {
        this.bookRepository = new BookRepositoryImpl();
        this.userRepository = new UserRepositoryImpl();
        this.libraryService = new LibraryServiceImpl(bookRepository, userRepository);
        this.activeUser = Role.USER;
    }




    @Test
    void testSearchBooksByAuthorWithExistingAuthor() {
        MyList<Book> booksByAuthorA = libraryService.searchBooksByAuthor("George Orwell");
        assertNotNull(booksByAuthorA, "Результат не должен быть null");
        assertEquals(1, booksByAuthorA.size(), "Должно быть 1 книги от 'George Orwell'");
    }

    @Test
    void testSearchBooksByAuthorWithNonExistingAuthor() {
        MyList<Book> booksByUnknown = libraryService.searchBooksByAuthor(" J.R.R. Tolkien");
        assertNotNull(booksByUnknown, "Результат не должен быть null");
        assertTrue(booksByUnknown.isEmpty(), "Список должен быть пуст");
    }

    @Test
    void testIsAvailableBooksWhenBooksAreAvailable() {
        MyList<Book> availableBooks = libraryService.listAvailableBooks();
        assertNotNull(availableBooks, "Результат не должен быть null");
        assertEquals(5, availableBooks.size(), "Должно быть 5 доступные книги");
    }

    @Test
    void testIsAvailableBooksWhenNoBooksAreAvailable() {
        libraryService = new LibraryServiceImpl(bookRepository, userRepository);
        libraryService.addBook(" dddddd", "ddddddd");
        MyList<Book> availableBooks = libraryService.listAvailableBooks();
        assertNotNull(availableBooks, "Результат не должен быть null");
    }

    @Test
    void testListAllBooksWhenLibraryHasBooks() {
        MyList<Book> allBooks = libraryService.listAllBooksAdmin();
        assertNotNull(allBooks, "Результат не должен быть null");
        assertEquals(5, allBooks.size(), "Должно быть 5 книги в библиотеке");
    }



}