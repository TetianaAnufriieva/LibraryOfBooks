package service;

import model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.BookRepository;
import repository.BookRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import utils.MyList;

import static org.junit.jupiter.api.Assertions.*;

class LibraryServiceImplTest {

    private static LibraryService libraryService;

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
}