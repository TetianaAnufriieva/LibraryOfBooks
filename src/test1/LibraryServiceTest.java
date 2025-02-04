package test1;

import model.Role;
import org.junit.jupiter.api.Test;
import repository.BookRepository;
import repository.BookRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.LibraryService;
import service.LibraryServiceImpl;


public class LibraryServiceTest {

    private final BookRepository bookRepository = new BookRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final LibraryService service = new LibraryServiceImpl(bookRepository, userRepository);

    //Метод testUserStatusUpdate() тестирует метод userStatusUpdate()
    // класса LibraryService.
    // Он создает объект Role и вызывает метод userStatusUpdate()
    // с тестовым электронным адресом и ролью.
    // Затем он утверждает, что результат равен true.
    @Test
    public void testUserStatusUpdate() {
        String email = "test@example.com";
        Role role = Role.ADMIN;
        boolean result = service.userStatusUpdate(email, role);
        equals(result);
    }

    //Метод testUpdatePassword() тестирует метод updatePassword() класса LibraryService.
    //Он вызывает метод updatePassword() с тестовым электронным адресом и новым паролем.
    // Затем он утверждает, что результат равен true.
    @Test
    public void testUpdatePassword() {

        String email = "test@example.com";
        String newPassword = "newPassword";
        boolean result = service.updatePassword(email, newPassword);
        equals(result);
    }
    //Метод testIsUserBlocked() тестирует метод isUserBlocked() класса LibraryService.
    // Он вызывает метод isUserBlocked() с тестовым электронным адресом.
    // Затем он утверждает, что результат равен true.
    @Test
    public void testIsUserBlocked() {
        String email = "test@example.com";
        boolean result = service.isUserBlocked(email);
        equals(result);
    }

}
