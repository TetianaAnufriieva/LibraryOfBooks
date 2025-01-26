import repository.BookRepository;
import repository.BookRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import service.LibraryService;
import service.LibraryServiceImpl;
import view.Menu;

public class BooksApp {

    public static void main(String[] args) {
        // Инициализация репозиториев
        BookRepository bookRepository = new BookRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();

        // Создание сервисного слоя
        LibraryService libraryService = new LibraryServiceImpl(bookRepository, userRepository);

        // Создание меню
        Menu menu = new Menu(libraryService);

        // Запуск приложения
        menu.start();
    }

}
