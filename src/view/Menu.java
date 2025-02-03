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

    public Menu(LibraryService service) {
        this.service = service;
    }


    public void start() {
        inputUser();
    }

    private void showMenu() {

        while (true) {
            System.out.println("\n ДОБРО ПОЖАЛОВАТЬ В МЕНЮ БИБЛИОТЕКИ 'ЗНАНИЯ ВЕКА'!");
            System.out.println("1. Меню книг");
            System.out.println("2. Меню пользователей");
            System.out.println("3. Меню администратора");
            System.out.println("0. Logout");
            System.out.println();
            System.out.printf(service.getActiveUser().getRole() == Role.ADMIN ?"ВЫ АДМИНИСТРАТОР" : "ВЫ ПОЛЬЗОВАТЕЛЬ");
            System.out.print("\n Сделайте выбор: ");

            String choice = scanner.nextLine();

            if (choice.equals("0")) {
                service.logoutUser();
                break;
            }
            showMenuCase(choice);
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
                    System.out.println("\nВы не администратор!");
                }
                break;
            default:
                System.out.println("\nСделайте корректный выбор.");
                waitRead();
                break;
        }
    }

    private void showBookMenu() {
        while (true) {
            System.out.println("\nМЕНЮ КНИГ!");
            System.out.println("\n1. Список всех книг.");
            System.out.println("2. Список свободных книг.");
            System.out.println("3. Список книг, отсортированный по автору.");
            System.out.println("4. Список книг, отсортированный по названию.");
            System.out.println("5. Поиск книги по автору.");
            System.out.println("6. Поиск книги по названию.");
            System.out.println("7. У какого пользователя находится книга.");
            System.out.println("0. Выход из меню.");
            System.out.println();
            System.out.printf(service.getActiveUser().getRole() == Role.ADMIN ?"ВЫ АДМИНИСТРАТОР" : "ВЫ ПОЛЬЗОВАТЕЛЬ");
            System.out.println("\nСделайте выбор пункта меню.");

            String input1 = scanner.nextLine();
            if (input1.equals("0")) break;

            showBookMenuCase(input1);
            waitRead();
        }
    }

    private void waitRead() {
        System.out.println("\nДля продолжения нажмите Enter.");
        scanner.nextLine();
    }

    private int inputUser() {
        boolean isAutorized;
        boolean isRegistration;
        while (true) {
            System.out.println("\n");
            System.out.println("1. Авторизация");
            System.out.println("2. Регистрация нового пользователя");
            System.out.println("0. Выход из системы");
            System.out.print("\nСделайте выбор пункта меню:");

            String input = scanner.nextLine();
            switch (input) {
                case "0":
                    System.out.println("До свидания!");
                    System.exit(0);
                    break;
                case "1":
                    isAutorized = authorizationUser();
                    if (isAutorized == true ) {
                        showMenu();
                    } else {
                        System.out.println("Ошибка авторизации !");
                    }
                    break;
                case "2":
                    isRegistration = registrationUser();
                    if (isRegistration == true ) {
                        showMenu();
                    } else {
                        System.out.println("Ошибка регистрации !");
                    }
                    break;
                default:
                    System.out.println("Сделайте корректный выбор");
                    break;
            }
        }
    }
    private boolean authorizationUser() {
        String email;
        String password;
        System.out.print("Введите email: ");
        email = scanner.nextLine();

        if (email.length() != 0 && email != null && service.isEmailExist(email) == true) {
            if (service.isUserBlocked(email) == true) {
                System.out.println("\nВаша учётная запись заблокирована.");
                return false;
            }
            System.out.print("Введите пароль: ");
            password = scanner.nextLine();

            boolean login = service.loginUser(email, password);
            if (login == true) {
                return true;
            }
        } else {
            System.out.println("\nНеверный email или пароль.");
        }
        return false;
    }

    private boolean registrationUser() {
        String email;
        String password;
        boolean registration;
        System.out.println("\nРегистрация нового пользователя!");
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
            System.out.println("Пользователь - " + email + " успешно зарегистрирован.");
            return true;
        } else {
            System.out.println("Регистрация не удалась.");
        }
        return false;
    }

    private void showUserMenu() {
        while (true) {
            System.out.println("\nМЕНЮ ПОЛЬЗОВАТЕЛЯ !");
            System.out.println("\n1. Список книг у пользователя.");
            System.out.println("2. Взять книгу.");
            System.out.println("3. Вернуть книгу.");
            System.out.println("0. Выход из меню.");
            System.out.println("\nСделайте выбор пункта меню.");

            String input = scanner.nextLine();
            if (input.equals("0")) break;
            showUserMenuCase(input);

            waitRead();
        }
    }

    private void showUserMenuCase(String input) {
        switch (input) {
            case "1":
                //список книг у пользователя
                String activeUserEmail = service.getActiveUser().getEmail();
                MyList<Book> isActiveUserBooksList = service.getBooksByUser(activeUserEmail);

                if(isActiveUserBooksList.size() > 0) {
                    System.out.println("Список книг у пользователя - "+ service.getActiveUser().getEmail()+" :");
                    bookPrintToConsole(isActiveUserBooksList);
                } else {
                    System.out.println("У Вас нет используемых книг.");
                }
                break;
            case "2":
                System.out.println("Выдача книги.");
                System.out.println("Введите id книги: ");

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
                    System.out.printf("\nId: %d; Title: %s; Author: %s",
                            take.getId(), take.getTitle(), take.getAuthor());
                }
                break;
            case "3":
                System.out.println("Возврат книги.");
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
                    System.out.printf("\nId: %d; Title: %s; Author: %s",
                            returnBook.getId(), returnBook.getTitle(), returnBook.getAuthor());
                }
                break;
            default:
                System.out.println("\nСделайте корректный выбор.");
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
        while (true) {
            System.out.println("\nМЕНЮ АДМИНИСТРАТОРА!");
            System.out.println("\n1. Регистрация нового пользователя.");
            System.out.println("2. Изменить пароль пользователя.");
            System.out.println("3. Изменить статус пользователя.");
            System.out.println("4. Список всех пользователей.");
            System.out.println("5. Удалить пользователя.");
            System.out.println("6. Добавить книгу.");
            System.out.println("7. Удалить книгу.");
            System.out.println("8. Редактировать книгу.");
            System.out.println("9. Список всех книг.");
            System.out.println("0. Выход из меню.");
            System.out.println("\nСделайте выбор пункта меню.");

            String input = scanner.nextLine();
            if (input.equals("0")) break;

            showAdminMenuCase(input);
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
                registrationUser();
                break;
            case "2":
                System.out.println("Измененить пароль пользователя.");
                System.out.print("Введите email пользователя: ");
                email = scanner.nextLine();

                System.out.print("Введите новый пароль пользователя: ");
                password = scanner.nextLine();

                updatePassword = service.updatePassword(email, password);
                if (updatePassword == true) {
                    System.out.println("Пароль пользователя - " + email + " успешно изменен.");
                } else {
                    System.out.println("Пароль не изменен!");
                }
                break;
            case "3":
                // изменить статус (role) пользователя
                System.out.println("Измененить статус пользователя.");
                System.out.print("Введите email пользователя: ");
                email = scanner.nextLine();

                System.out.print("Для изменения введите новый статус пользователя (1-User, 2- BLOCKED, 3-ADMIN).");
                String inputStatusStr = scanner.nextLine();

                Integer status = checkInput(inputStatusStr);

                int inputStatus = 0;

                if (status == null) {
                    System.out.println("\nСделайте корректный выбор.");
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
                    System.out.println("Статус пользователя " + email + " изменен на - " + role);
                } else {
                    System.out.println("Статус пользователя не изменен!");
                }
                break;
            case "4":
                if (service.userList() != null) {
                    System.out.println("Список всех пользователей.");
                    for (User user : service.userList()) {
                        System.out.printf("\nEmail: %s; Role: %s", user.getEmail(), user.getRole());
                    }
                } else {
                    System.out.println("Список пользователей пуст!");
                }
                break;
            case "5":
                System.out.println("Удалить пользователя.");
                System.out.print("Введите email пользователя: ");
                email= scanner.nextLine();

                boolean isDelete = service.removeUser(email);
                if (isDelete == true) {
                    System.out.println("Пользователь - " + email + " успешно удален.");
                } else {
                    System.out.println("Пользователь - " + email + " не удален!");
                }
                break;
            case "6":
                System.out.println("Добавить новую книгу.");
                System.out.print("Введите название книги: ");
                title = scanner.nextLine();

                System.out.print("Введите автора книги: ");
                author = scanner.nextLine();

                Book book = service.addBook(title, author);
                if (book != null) {
                System.out.println("Книга успешно добавлена.");
                } else {
                    System.out.println("Книга не добавлена!");
                }
                break;
            case "7":
                System.out.println("Удалить книгу.");
                System.out.print("Введите ID книги: ");
                String inputStr = scanner.nextLine();

                Integer idBookInt = checkInput(inputStr);
                if (idBookInt == null) {
                    System.out.println("\nСделайте корректный выбор.");
                    break;
                } else {
                    // из обертки Integer в int
                    idBook = idBookInt;
                }
                isDeleteBook = service.removeBook(idBook);

                if (isDeleteBook == true) {
                    System.out.println("Книга успешно удалена.");
                } else {
                    System.out.println("Книга не удалена!");
                }
                break;
            case "8":
                System.out.println("Редактировать книгу.");
                System.out.print("Введите ID книги: ");
                String inputStr1 = scanner.nextLine();

                Integer idBookInt1 = checkInput(inputStr1);

                if (idBookInt1 == null) {
                    System.out.println("\nСделайте корректный выбор.");
                    break;
                } else {
                    // из обертки Integer в int
                    idBook = idBookInt1;
                }
                System.out.print("Введите новое название книги: ");
                title = scanner.nextLine();

                System.out.print("Введите нового автора книги: ");
                author = scanner.nextLine();
                isUpdateBook = service.bookUpdateById(idBook, title, author);

                if (isUpdateBook == true) {
                    System.out.println("Книга успешно изменена.");
                } else {
                    System.out.println("Изменения не выполнены!");
                }
                break;
            case "9":
                if (service.listAllBooksAdmin() != null) {
                    System.out.println("Список всех книг: ");
                    bookPrintToConsole(service.listAllBooksAdmin());
                } else {
                    System.out.println("В библиотеке нет книг.");
                }
                break;
            default:
                System.out.println("\nСделайте корректный выбор.");
                break;
        }
    }

    private void showBookMenuCase(String input1) {
        switch (input1) {
            case "1":
                if(service.listAllBooksAdmin()!=null) {
                    System.out.println("Список всех книг: ");
                    bookPrintToConsole(service.listAllBooksAdmin());
                } else {
                    System.out.println("В библиотеке нет книг.");
                }
                break;

            case "2":
                if(service.listAvailableBooks()!=null) {
                    System.out.println("Список свободных книг: ");
                    bookPrintToConsole(service.listAvailableBooks());
                } else {
                    System.out.println("В библиотеке нет свободных книг.");
                }
                break;

            case "3":
                //TODO нет метода сортировки getBooksSortByAuthor
//                Список всех книг,отсортированный по автору
//                if(service.getBooksSortByAuthor() != null) {
//                    System.out.println("Список всех книг, отсортированный по автору: ");
//                    bookPrintToConsole(service.getBooksSortByAuthor());
//                } else {
//                    System.out.println("В библиотеке нет книг.");
//                }
                break;

            case "4":
                //TODO нет метода сортировки getBooksSortByTitle
                //Список всех книг, отсортированный по названию книги
//                if(service.getBooksSortByTitle()!=null) {
//                    System.out.println("Список всех книг,отсортированный по названию книги: ");
//                    bookPrintToConsole(service.getBooksSortByTitle());
//                } else {
//                    System.out.println("В библиотеке нет книг.");
//                }
                break;

            case "5":
                System.out.println("Список книг по автору: ");
                System.out.print("Введите автора книги: ");
                String inputAuthor= scanner.nextLine();

                if(service.searchBooksByAuthor(inputAuthor) != null) {
                    System.out.println("Список книг по автору - " + inputAuthor + " :");
                    bookPrintToConsole(service.searchBooksByAuthor(inputAuthor));
                } else {
                    System.out.println("В библиотеке нет книг с автором - " + inputAuthor);
                }
                break;

            case "6":
                System.out.println("Список книг по названию: ");
                System.out.print("Введите название книги: ");
                String inputName = scanner.nextLine();

                if(service.searchBooksByTitle(inputName) != null) {
                    System.out.println("Список книг по названию - " + inputName + " :");
                    bookPrintToConsole(service.searchBooksByTitle(inputName));

                } else {
                    System.out.println("В библиотеке нет книг с названием - "+ inputName);
                }
                break;

            case "7":
                System.out.println("У какого пользователя находится книга: ");
                System.out.print("Введите id книги: ");

                int idBook = scanner.nextInt();
                scanner.nextLine();

                if(service.findUserByBookId(idBook)!= null) {
                    System.out.print("Книга с id - " + idBook + " за пользователем : "
                            + service.findUserByBookId(idBook));
                } else {
                    System.out.print("Книга с id - " + idBook + " - свободна.");
                }
                break;
            default:
                System.out.println("\nСделайте корректный выбор.");
                break;
        }
    }

    private void bookPrintToConsole(MyList<Book> books) {

        for (Book book : books) {
            System.out.printf("\nID: %d; Title: %s; Author: %s: Available: %s\n",
                    book.getId(), book.getTitle(), book.getAuthor(),
                    book.isAvailable() ? "Доступна" : "Не доступна.");
        }
    }
}



