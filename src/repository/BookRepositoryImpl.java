package repository;

import model.Book;
import utils.MyArrayList;
import utils.MyList;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class BookRepositoryImpl implements BookRepository {


    // Все книги будут хранится в памяти нашего приложения
    private final MyList<Book> books;


    // Объект, отвечающий за генерацию уникальных id
    private final AtomicInteger currentId = new AtomicInteger(1);

    public BookRepositoryImpl() {
        this.books = new MyArrayList<>();
        addStartBooks();
    }

    private void addStartBooks() {
        books.addAll(

                new Book(currentId.getAndIncrement(), "1984", "George Orwell", true),
                new Book(currentId.getAndIncrement(), "To Kill a Mockingbird", "Harper Lee",  true),
                new Book(currentId.getAndIncrement(), "The Lord of the Rings", "J.R.R. Tolkien",  true),
                new Book(currentId.getAndIncrement(), "Pride and Prejudice", "Jane Austen",  true),
                new Book(currentId.getAndIncrement(), "The Adventures of Huckleberry Finn", "Mark Twain", true)
        );
    }

    //===============================================

    // изменение статуса доступности книги
    @Override
    public void updateBookStatus(int id, boolean newStatus) {
        Book book = findBookById(id);
        if (book != null) {
            book.setAvailable(newStatus);
        }
    }

    // удаление книги по id
    @Override
    public boolean removeBook(int id) {
        Book book = findBookById(id);
        if (book == null) return false;
        books.remove(book);
        return true;
    }

    // добавление книги по названию и автору
    @Override
    public Book addBook(String title, String author) {
        Book newBook = new Book(currentId.getAndIncrement(), title, author, true);
        books.add(newBook);
        return newBook;
    }

    // поиск книги по id (перебирает список книг и возвращает ее)
    @Override
    public Book findBookById(int id) {
        for(Book book : books) {
            if(book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    //===============================================



    @Override
    public MyList<Book> findBooksByTitle(String title) {
        return null;
    }

    @Override
    public MyList<Book> findBooksByAuthor(String author) {
        return null;
    }

    @Override
    public MyList<Book> getAllBooks() {
        return null;
    }

    @Override
    public MyList<Book> getAvailableBooks() {
        return null;
    }

}
