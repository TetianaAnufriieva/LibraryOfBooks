package model;

import java.util.Objects;

public class Book {

    private int id;
    private String title;
    private String author;
    private boolean isAvailable;
    private String userEmail;

    // Constructors, getters, and setters
    public Book(int id, String title, String author, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return id == book.id && isAvailable == book.isAvailable && Objects.equals(title, book.title)
                && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, isAvailable);
    }

    @Override
    public String toString() {
        return "Book {" +
                " id = " + id +
                "; title = '" + title + '\'' +
                "; author = '" + author + '\'' +
                "; isAvailable = " + isAvailable +
                '}';
    }

    public String getUserUse() {
        return userEmail;
    }
    public void setUserUse(String email) {
        this.userEmail = email;
    }
}
