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
                System.out.println("\nДобро пожаловать в меню библиотеки 'Знания Века'!");
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
                showMenuCase(choice);
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

    private void showBookMenu() {
        while (true) {
            System.out.println("Меню книг");
            System.out.println("1. Список всех книг");
            System.out.println("2. Список свободных книг");
            System.out.println("3. Список книг по автору");
            System.out.println("4. Список книг по названию");
            System.out.println("5. Поиск книги по автору");
            System.out.println("6. Поиск книги по названию");
            System.out.println("7. У какого пользователя находится книга");
            System.out.println("0. Выход из меню");
            System.out.println("\nСделайте выбор пункта меню");

            String input1 = scanner.nextLine();
            if (input1.equals("0")) break;

            showBookMenuCase(input1);
            if (exitBookMenu == true) break;
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
                int idBook = 0;
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
                int idBook1 = 0;
                if (idBookInt1 == null) {
                    System.out.println("Некорректно введен id книги.");
                    break;
                } else {
                    // из обертки Integer в int
                    idBook1 = idBookInt1;
                }
                Book returnBook = service.returnBook(idBook1);
                if (returnBook != null) {
                    System.out.printf("\nid: %d; title: %s; author: %s",
                            returnBook.getId(), returnBook.getTitle(), returnBook.getAuthor());
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

    //метод, проверка валидности введенния чисел в консоли
    private Integer checkInput(String input) {
        try {
            return Integer.valueOf(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void showAdminMenu() {
        exitAdminMenu = false;
        while (true) {
            System.out.println("Меню администратора");
            System.out.println("1. Регистрация нового пользователя");
            System.out.println("2. Изменить пароль пользователя");
            System.out.println("3. Изменить статус пользователя");
            System.out.println("4. Список всех пользователей");
            System.out.println("5. Удалить пользователя");
            System.out.println("6. Добавить книгу");
            System.out.println("7. Удалить книгу");
            System.out.println("8. Редактировать книгу");
            System.out.println("9. Список всех книг");
            System.out.println("0. Выход из меню");
            System.out.println("\nСделайте выбор пункта меню");

            String input = scanner.nextLine();
            if (input.equals("0")) break;

            showAdminMenuCase(input);
            if (exitAdminMenu == true) break;
            waitRead();
        }
    }

    private void showAdminMenuCase(String input) {
        String email;
        String password;
        String title;
        String author;
        boolean updatePassword;
        boolean isDeleteBook;
        boolean isUpdateBook;
        int idBook;

        switch (input) {
            case "1":
                // регистрация нового пользователя
                registrationUser();
                break;
            case "2":
                // изменить пароль пользователя
                System.out.println("Изменение Пароля пользователя:");
                System.out.print("Введите email пользователя:");
                email = scanner.nextLine();

                System.out.print("Введите новый пароль пользователя:");
                password = scanner.nextLine();

                updatePassword = service.updatePassword(email, password);
                if (updatePassword == true) {
                    System.out.println("Пароль пользователя успешно изменен");
                } else {
                    System.out.println("Пароль не изменен");
                }
                break;
            case "3":
                // изменить статус пользователя
                System.out.println("Изменение Статуса пользователя:");
                System.out.print("Введите email пользователя:");
                email = scanner.nextLine();

                System.out.print("Введите статус пользователя (1-User, 2- BLOCKED, 3-ADMIN)");
                String inputStatusStr = scanner.nextLine();

                Integer status = checkInput(inputStatusStr);

                int inputStatus = 0;

                if (status == null) {
                    System.out.println("Сделайте корректный выбор");
                    break;
                } else {
                    // из обертки Integer в int
                    inputStatus = status;
                }
                Role role = null;
                boolean isUpdate = false;

                if (inputStatus == 1) role = Role.USER;
                if (inputStatus == 2) role = Role.BLOCKED;
                if (inputStatus == 3) role = Role.ADMIN;
                if (role != null) {
                    isUpdate = service.userStatusUpdate(email, role);
                }
                if (isUpdate == true) {
                    System.out.println("Статус пользователя изменен на -" + role);
                } else {
                    System.out.println("Статус НЕ ИЗМЕНЕН!");
                }
                break;
            case "4":
                // список всех пользователей
                if (service.userList() != null) {
                    System.out.println("Список всех пользователей:");
                    for (User user : service.userList()) {
                        System.out.printf("\nemail: %s; role: %s", user.getEmail(), user.getRole());
                    }
                } else {
                    System.out.println("Список пользователей пуст!");
                }
                break;
            case "5":
                //TODO
                // удалить пользователя
                break;
            case "6":
                // добавить книгу
                System.out.println("Добавление новой книги.");
                System.out.print("Введите название книги:");
                title = scanner.nextLine();

                System.out.print("Введите автора книги:");
                author = scanner.nextLine();

                service.addBook(title, author);
                System.out.println("Книга успешно добавлена");
                break;
            case "7":
                //Удаление книги
                System.out.println("УДАЛЕНИЕ книги.");
                System.out.print("Введите ID книги:");
                String inputStr = scanner.nextLine();

                Integer idBookInt = checkInput(inputStr);
                if (idBookInt == null) {
                    System.out.println("Сделайте корректный выбор");
                    break;
                } else {
                    // из обертки Integer в int
                    idBook = idBookInt;
                }
                isDeleteBook = service.removeBook(idBook);

                if (isDeleteBook == true) {
                    System.out.println("Книга успешно УДАЛЕНА");
                } else {
                    System.out.println("Книга НЕ УДАЛЕНА !");
                }
                break;
            case "8":
                //Редактирование книги
                System.out.println("Редактирование книги.");
                System.out.print("Введите ID книги:");
                String inputStr1 = scanner.nextLine();

                Integer idBookInt1 = checkInput(inputStr1);

                if (idBookInt1 == null) {
                    System.out.println("Сделайте корректный выбор");
                    break;
                } else {
                    // из обертки Integer в int
                    idBook = idBookInt1;
                }
                System.out.print("Введите Новое название книги:");
                title = scanner.nextLine();

                System.out.print("Введите Нового автора книги:");
                author = scanner.nextLine();
                isUpdateBook = service.bookUpdateById(idBook, title, author);

                if (isUpdateBook == true) {
                    System.out.println("Книга успешно Изменена");
                } else {
                    System.out.println("Изменения НЕ выполнены !");
                }
                break;
            case "9":
                //Список всех книг
                if (service.listAllBooksAdmin() != null) {
                    System.out.println("Список всех книг:");
                    bookPrintToConsole(service.listAllBooksAdmin());
                } else {
                    System.out.println("В библиотеке нет книг");
                }
                break;
            case "0":
                exitAdminMenu = true;
                System.out.println("Вы вышли из Меню Администратора");
                waitRead();
                break;
            default:
                System.out.println("Сделайте корректный выбор");
        }
    }

    private void showBookMenuCase(String input1) {
        //TODO
    }

    private void bookPrintToConsole(MyList<Book> books) {

        for (Book book : books) {
            System.out.printf(" ID: %d; Title: %s; Author: %s\n", book.getId(), book.getTitle(), book.getAuthor(),
                    book.isAvailable() ? "Доступна" : "Не доступна");
        }
    }
}



