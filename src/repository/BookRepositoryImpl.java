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

    @Override
    public void updateBookStatus(int id, boolean newStatus) {

    }

    @Override
    public void removeBook(int id) {

    }

    @Override
    public Book addBook(String title, String author, boolean isAvailable) {
        return null;
    }

    @Override
    public Book findBookById(int id) {
        return null;
    }

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
