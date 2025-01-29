package view;

import model.Book;
import model.Role;
import model.User;
import service.LibraryService;
import utils.MyList;
import utils.PersonValidation;

import java.util.Scanner;

public class Menu {
    private final LibraryService service;
    private final Scanner scanner = new Scanner(System.in);
    private boolean exitAdminMenu;
    private boolean exitUserMenu;
    private boolean exitBookMenu;

    public Menu(LibraryService service) {
        this.service = service;
    }

    public void start() {
        showMenu();
    }

    private void showMenu() {
      boolean isAutorized = false;
      boolean isRegistration = false;

      int input = inputUser();
      if (input == 1) {
          isAutorized = authorizationUser();
      }
      if (input == 2) {
          isRegistration = registrationUser();
      }
      if (isAutorized == true || isRegistration == true) {
          while (true) {
              System.out.println("Добро пожаловать в меню библиотеки 'Знания Века'!");
              System.out.println("1. Меню книг");
              System.out.println("2. Меню пользователей");
              System.out.println("3. Меню администратора");
              System.out.println("0. Выход из системы");
              System.out.println("\n Сделайте выбор: ");

              //String использую (если кто то введет вместо цифры - букву)
              String choice = scanner.nextLine();

              if (choice.equals("0")) {
                  System.out.println("До свидания!");
                  System.exit(0);
              }
              showMenuCase();
          }
      } else {
          System.out.println("Ошибка авторизации");
      }
    }

    private void showMenuCase(String choice) {

        switch (choice) {
            case "1":
                showBookMenu();
                break;
            case "2":
                showUserMenu();
                break;
            case "3":
                if (service.getActiveUser().getRole() == Role.ADMIN) {
                showAdminMenu();
                } else {
                    System.out.println("Вы не администратор!");
                }
                break;
            default:
                System.out.println("Сделайте корректный выбор");
                waitRead();
        }
    }

    private void waitRead() {
        System.out.println("\nДля продолжения нажмите Enter");
        scanner.nextLine();
    }

    private int inputUser() {
        while (true) {
            System.out.println("1. Авторизация");
            System.out.println("2. Регистрация нового пользователя");
            System.out.println("0. Выход из системы");
            System.out.println("\nСделайте выбор пункта меню");

            String input = scanner.nextLine();
            if (input.equals("0")) {
                System.out.println("До свидания!");
                System.exit(0);
            }
            if (input.equals("1") == true) return 1;
            if (input.equals("2") == true) return 2;
            System.out.println("Сделайте корректный выбор");
            waitRead();
        }
    }

    private boolean authorizationUser() {
        String email;
        String password;
        System.out.print("Введите email: ");
        email = scanner.nextLine();
        if (email.length() != 0 && email != null && service.isEmailExist(email) == true) {
            if (service.isUserBlocked(email) == true) {
                System.out.println("Ваша учётная запись заблокирована.");
                return false;
            }
            System.out.print("Введите пароль: ");
            password = scanner.nextLine();

            boolean login = service.loginUser(email, password);
            if (login == true) {
                return true;
            }
        } else {
                System.out.println("Неверное имя пользователя или пароль.");
        }
        return false;
    }

    private boolean registrationUser() {
        String email;
        String password;
        boolean registration;
        System.out.println("Регистрация нового пользователя");
        System.out.print("Введите email: ");
        email = scanner.nextLine();
        if (PersonValidation.isEmailValid(email) == false) {
            System.out.println("Некорректно введен email.");
            return false;
        }
        System.out.println("Введите пароль: ");
        password = scanner.nextLine();
        registration = service.registerUser(email, password);
        if (registration == true) {
            System.out.println("Пользователь успешно зарегистрирован.");
            return true;
        } else {
            System.out.println("Регистрация не удалась.");
        }
        return false;
    }

    private void showUserMenu() {
        exitUserMenu = false;
        while (true) {
            System.out.println("Меню пользователей");
            System.out.println("1. Список книг у пользователя");
            System.out.println("2. Взять книгу");
            System.out.println("3. Вернуть книгу");
            System.out.println("0. Выход из меню");
            System.out.println("\nСделайте выбор пункта меню");

            String input = scanner.nextLine();
            showUserMenuCase(input);
            if (exitUserMenu == true) break;
            waitRead();
        }
    }

    private void showUserMenuCase(String input) {
        exitUserMenu = false;
        switch (input) {
            case "1":
                //TODO
                //список книг у пользователя
                break;
            case "2":
                System.out.println("Выдача книги");
                System.out.print("Введите id книги: ");

                String inputStr = scanner.nextLine();
                Integer idBookInt = checkInput(inputStr);
                int idBook =0;
                if (idBookInt == null) {
                    System.out.println("Некорректно введен id книги.");
                    break;
                } else {
                    // из обертки Integer в int
                    idBook = idBookInt;
                }
                Book take = service.borrowBook(idBook);
                if (take != null) {
                    System.out.printf("\nid: %d; title: %s; author: %s", take.getId(), take.getTitle(), take.getAuthor());
                }
                break;
            case "3":
                System.out.println("Возврат книги");
                System.out.print("Введите id книги: ");
                String inputStr1 = scanner.nextLine();
                Integer idBookInt1 = checkInput(inputStr1);
                int idBook1 =0;
                if (idBookInt1 == null) {
                    System.out.println("Некорректно введен id книги.");
                    break;
                } else {
                    // из обертки Integer в int
                    idBook1 = idBookInt1;
                }
                Book returnBook = service.returnBook(idBook1);
                if (returnBook != null) {
                    System.out.printf("\nid: %d; title: %s; author: %s", returnBook.getId(), returnBook.getTitle(), returnBook.getAuthor());
                }
                break;
            case "0":
                exitUserMenu = true;
                System.out.println("Выход из меню пользователя");
                break;
            default:
                System.out.println("Сделайте корректный выбор");
                break;
        }
    }














//    private void showMenu() {
//        while (true) {
//            System.out.println("Добро пожаловать в библиотечную систему 'Знания Века'");
//            System.out.println("1. Меню книг");
//            System.out.println("2. Меню пользователей");
//            System.out.println("0. Выход из системы");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//
//            if (choice == 0) {
//                System.out.println("До свидания!");
//                System.exit(0);
//            }
//
//            showSubMenu(choice);
//        }
//    }
//
//    private void showSubMenu(int choice) {
//        switch (choice) {
//            case 1:
//                showBookMenu();
//                break;
//            case 2:
//                showUserMenu();
//                break;
//            default:
//                System.out.println("Сделайте корректный выбор");
//                waitRead();
//        }
//    }
//
//    private void showBookMenu() {
//        while (true) {
//            System.out.println("Меню книг");
//            System.out.println("1. Добавить книгу");
//            System.out.println("2. Поиск книг по названию");
//            System.out.println("3. Поиск книг по автору");
//            System.out.println("4. Список доступных книг");
//            System.out.println("0. Вернуться в предыдущее меню");
//
//            System.out.println("\nСделайте выбор пункта меню");
//            int input = scanner.nextInt();
//            scanner.nextLine();
//
//            if (input == 0) break;
//
//            handleBookMenu(input);
//        }
//    }
//
//    private void handleBookMenu(int input) {
//        switch (input) {
//            case 1:
//                System.out.println("Добавление книги");
//                System.out.print("Введите название: ");
//                String title = scanner.nextLine();
//
//                System.out.print("Введите автора: ");
//                String author = scanner.nextLine();
//
//                Book book = new Book(0, title, author, true);
//                service.addBook(title, author);
//                System.out.println("Книга успешно добавлена.");
//                break;
//
//            case 2:
//                System.out.print("Введите название для поиска: ");
//                String searchTitle = scanner.nextLine();
//                MyList<Book> booksByTitle = service.searchBooksByTitle(searchTitle);
//                showBooksList(booksByTitle);
//                break;
//
//            case 3:
//                System.out.print("Введите автора для поиска: ");
//                String searchAuthor = scanner.nextLine();
//                MyList<Book> booksByAuthor = service.searchBooksByAuthor(searchAuthor);
//                showBooksList(booksByAuthor);
//                break;
//
//            case 4:
//                MyList<Book> availableBooks = service.listAvailableBooks();
//                showBooksList(availableBooks);
//                break;
//
//            default:
//                System.out.println("Сделайте корректный выбор");
//        }
//        waitRead();
//    }
//
//    private void showUserMenu() {
//        while (true) {
//            System.out.println("Меню пользователей");
//            System.out.println("1. Регистрация пользователя");
//            System.out.println("2. Вход в систему");
//            System.out.println("3. Взять книгу");
//            System.out.println("4. Вернуть книгу");
//            System.out.println("5. Выход из системы");
//            System.out.println("0. Вернуться в предыдущее меню");
//
//            System.out.println("\nСделайте выбор пункта меню");
//            int input = scanner.nextInt();
//            scanner.nextLine();
//
//            if (input == 0) break;
//
//            handleUserMenu(input);
//        }
//    }
//
//    private void handleUserMenu(int input) {
//        switch (input) {
//            case 1:
//                System.out.print("Введите email: ");
//                String email = scanner.nextLine();
//                System.out.print("Введите пароль: ");
//                String password = scanner.nextLine();
//
//                User user = new User(email, password,Role.USER);
//                service.registerUser(email, password);
//
//                if (user == null) {
//                    System.out.println("Регистрация провалена!");
//                } else {
//                    System.out.println("Пользователь успешно зарегистрирован.");
//                }
//
//                break;
//
//            case 2:
//                System.out.println("Авторизация пользователя [LOGIN]");
//                System.out.println("Введите email:");
//                String email1 = scanner.nextLine();
//
//                System.out.println("Введите пароль: ");
//                String password1 = scanner.nextLine();
//
//                boolean isLogin = service.loginUser(email1, password1);
//
//                if (isLogin) {
//                    System.out.println("Вы успешно вошли в систему.");
//                } else {
//                    System.out.println("Выполнить вход не удалось.");
//                }
//
//                waitRead();
//                break;
//
//            case 3:
//                System.out.print("Введите ID книги: ");
//                int bookId = scanner.nextInt();
//                System.out.print("Введите ID пользователя: ");
//                int userId = scanner.nextInt();
//                service.borrowBook(bookId);
//                System.out.println("Книга успешно взята.");
//                break;
//
//            case 4:
//                System.out.print("Введите ID книги: ");
//                int returnBookId = scanner.nextInt();
//                System.out.print("Введите ID пользователя: ");
//                int returnUserId = scanner.nextInt();
//                service.returnBook(returnBookId);
//                System.out.println("Книга успешно возвращена.");
//                break;
//
//            case 5:
//                System.out.println("Выход из системы [LOGOUT]");
//                service.logoutUser();
//                System.out.println("Вы вышли из системы");
//                waitRead();
//
//                break;
//
//            default:
//                System.out.println("Сделайте корректный выбор");
//        }
//        waitRead();
//    }
//
//    private void waitRead() {
//        System.out.println("\nДля продолжения нажмите Enter...");
//        scanner.nextLine();
//    }
//
//    private void showBooksList(MyList<Book> list) {
//        for (Book book : list) {
//            System.out.printf("%d. %s (%d) - %s\n", book.getId(), book.getTitle(), book.getAuthor());
//        }
//    }
//}
