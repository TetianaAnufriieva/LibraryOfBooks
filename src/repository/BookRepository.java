package repository;

import model.Book;
import utils.MyList;


public interface BookRepository {

/*
CRUD - операция
- create добавление новых данных (сущность)
- read получение или чтение данных
- update изменение существующих данных
- delete удаление сущности (данных)
 */

    // добавление книги
    Book addBook(String title, String author);

    // поиск книг по id
    Book findBookById(int id);

    // поиск книг по названию
    MyList<Book> findBooksByTitle(String title);

    // поиск книг по автору
    MyList<Book> findBooksByAuthor(String author);

    // получение всех книг
    MyList<Book> getAllBooks();

    // получение всех доступных книг
    MyList<Book> getAvailableBooks();

    // изменение статуса
    // доступна книга или нет
    void updateBookStatus (int id, boolean newStatus);

     //редактирование (изменить) книги

    public void bookUpdateById(int id,String title, String author);
    // удаление
    boolean removeBook(int id);

    //список книг за пользователем
    MyList<Book> getBooksByUser(String email);

    //список занятых книг
    MyList<Book> listBusyBooks();
}
