package model;

import utils.MyArrayList;
import utils.MyList;

import java.util.Objects;

public class User {
    private String email;
    private String password;
    private Role role;

    private MyList<Book> userBooks;


    // Constructors, getters, and setters
    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.userBooks = new MyArrayList<>();
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(password, user.password) && role == user.role && Objects.equals(userBooks, user.userBooks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, role, userBooks);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", userBooks=" + userBooks +
                '}';
    }
}
