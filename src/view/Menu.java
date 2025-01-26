package view;

import model.Book;
import model.Role;
import model.User;
import service.LibraryService;
import utils.MyList;

import java.util.Scanner;

public class Menu {
    private final LibraryService service;
    private final Scanner scanner = new Scanner(System.in);

    public Menu(LibraryService service) {
        this.service = service;
    }

    public void start() {
        showMenu();
    }

    private void showMenu() {
        while (true) {
            System.out.println("Добро пожаловать в библиотечную систему 'Знания Века'");
            System.out.println("1. Меню книг");
            System.out.println("2. Меню пользователей");
            System.out.println("0. Выход из системы");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("До свидания!");
                System.exit(0);
            }

            showSubMenu(choice);
        }
    }

    private void showSubMenu(int choice) {
        switch (choice) {
            case 1:
                showBookMenu();
                break;
            case 2:
                showUserMenu();
                break;
            default:
                System.out.println("Сделайте корректный выбор");
                waitRead();
        }
    }

    private void showBookMenu() {
        while (true) {
            System.out.println("Меню книг");
            System.out.println("1. Добавить книгу");
            System.out.println("2. Поиск книг по названию");
            System.out.println("3. Поиск книг по автору");
            System.out.println("4. Список доступных книг");
            System.out.println("0. Вернуться в предыдущее меню");

            System.out.println("\nСделайте выбор пункта меню");
            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) break;

            handleBookMenu(input);
        }
    }

    private void handleBookMenu(int input) {
        switch (input) {
            case 1:
                System.out.println("Добавление книги");
                System.out.print("Введите название: ");
                String title = scanner.nextLine();

                System.out.print("Введите автора: ");
                String author = scanner.nextLine();

                Book book = new Book(0, title, author, true);
                service.addBook(title, author);
                System.out.println("Книга успешно добавлена.");
                break;

            case 2:
                System.out.print("Введите название для поиска: ");
                String searchTitle = scanner.nextLine();
                MyList<Book> booksByTitle = service.searchBooksByTitle(searchTitle);
                showBooksList(booksByTitle);
                break;

            case 3:
                System.out.print("Введите автора для поиска: ");
                String searchAuthor = scanner.nextLine();
                MyList<Book> booksByAuthor = service.searchBooksByAuthor(searchAuthor);
                showBooksList(booksByAuthor);
                break;

            case 4:
                MyList<Book> availableBooks = service.listAvailableBooks();
                showBooksList(availableBooks);
                break;

            default:
                System.out.println("Сделайте корректный выбор");
        }
        waitRead();
    }

    private void showUserMenu() {
        while (true) {
            System.out.println("Меню пользователей");
            System.out.println("1. Регистрация пользователя");
            System.out.println("2. Вход в систему");
            System.out.println("3. Взять книгу");
            System.out.println("4. Вернуть книгу");
            System.out.println("5. Выход из системы");
            System.out.println("0. Вернуться в предыдущее меню");

            System.out.println("\nСделайте выбор пункта меню");
            int input = scanner.nextInt();
            scanner.nextLine();

            if (input == 0) break;

            handleUserMenu(input);
        }
    }

    private void handleUserMenu(int input) {
        switch (input) {
            case 1:
                System.out.print("Введите email: ");
                String email = scanner.nextLine();
                System.out.print("Введите пароль: ");
                String password = scanner.nextLine();

                User user = new User(email, password,Role.USER);
                service.registerUser(email, password);

                if (user == null) {
                    System.out.println("Регистрация провалена!");
                } else {
                    System.out.println("Пользователь успешно зарегистрирован.");
                }

                break;

            case 2:
                System.out.println("Авторизация пользователя [LOGIN]");
                System.out.println("Введите email:");
                String email1 = scanner.nextLine();

                System.out.println("Введите пароль: ");
                String password1 = scanner.nextLine();

                boolean isLogin = service.loginUser(email1, password1);

                if (isLogin) {
                    System.out.println("Вы успешно вошли в систему.");
                } else {
                    System.out.println("Выполнить вход не удалось.");
                }

                waitRead();
                break;

            case 3:
                System.out.print("Введите ID книги: ");
                int bookId = scanner.nextInt();
                System.out.print("Введите ID пользователя: ");
                int userId = scanner.nextInt();
                service.borrowBook(bookId, userId);
                System.out.println("Книга успешно взята.");
                break;

            case 4:
                System.out.print("Введите ID книги: ");
                int returnBookId = scanner.nextInt();
                System.out.print("Введите ID пользователя: ");
                int returnUserId = scanner.nextInt();
                service.returnBook(returnBookId, returnUserId);
                System.out.println("Книга успешно возвращена.");
                break;

            case 5:
                System.out.println("Выход из системы [LOGOUT]");
                service.logoutUser();
                System.out.println("Вы вышли из системы");
                waitRead();

                break;

            default:
                System.out.println("Сделайте корректный выбор");
        }
        waitRead();
    }

    private void waitRead() {
        System.out.println("\nДля продолжения нажмите Enter...");
        scanner.nextLine();
    }

    private void showBooksList(MyList<Book> list) {
        for (Book book : list) {
            System.out.printf("%d. %s (%d) - %s\n", book.getId(), book.getTitle(), book.getAuthor());
        }
    }
}
