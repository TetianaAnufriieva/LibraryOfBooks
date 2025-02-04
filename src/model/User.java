package model;

import java.util.Objects;

public class User {
    private String email;
    private String password;
    private Role role;


    // Constructors, getters, and setters
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = Role.USER;
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
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(password, user.password) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, role);
    }

    @Override
    public String toString() {
        return "User { " +
                " email = '" + email + '\'' +
                "; password = '" + password + '\'' +
                "; role = " + role +
                '}';
    }
}
