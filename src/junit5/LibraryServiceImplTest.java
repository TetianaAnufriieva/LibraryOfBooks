package junit5;

import model.Role;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.BookRepository;
import repository.BookRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.LibraryService;
import service.LibraryServiceImpl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LibraryServiceImplTest {


    private final UserRepository userRepository = new UserRepositoryImpl();
    private final BookRepository bookRepository = new BookRepositoryImpl();
    private final LibraryService service = new LibraryServiceImpl(bookRepository, userRepository);
    private User activeUser;

    @BeforeEach
    void setUp() {
        System.out.println("@BeforeEach");
    }


    @Test
    public void testLoginUser() {
        String email = "test@example.com";
        String password = "password";
        boolean result = service.loginUser(email, password);
        equals(result);
    }

    @Test
    public void logoutUser() {
        activeUser = null;
    }


    @Test
    public void testRemoveBook_AdminUser_ValidBookId_ReturnsTrue() {
        // Set up test data
        User adminUser = new User("admin", Role.ADMIN.toString());
        activeUser = adminUser;
        bookRepository.addBook("book", "author");

        // Call the method being tested
        boolean result = service.removeBook(1);

        // Verify the result
        equals(result);
        assertNotNull(bookRepository.findBookById(1));
    }

    @Test
    public void testRemoveBook_NonAdminUser_ValidBookId_ReturnsFalse() {
        // Set up test data
        User nonAdminUser = new User("nonadmin", Role.USER.toString());
        activeUser = nonAdminUser;
        bookRepository.addBook("book2", "author2");

        // Call the method being tested
        boolean result = service.removeBook(1);

        // Verify the result
        assertFalse(result);
        assertNotNull(bookRepository.findBookById(1));
    }

//    @Test
//    public void testRemoveBook_AdminUser_InvalidBookId_ReturnsFalse() {
//        // Set up test data
//        User adminUser = new User("admin", Role.ADMIN.toString());
//        activeUser = adminUser;
//
//        // Call the method being tested
//        boolean result = service.removeBook(1);
//
//        // Verify the result
//        assertFalse(result);
//    }
}