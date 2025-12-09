package model;

import java.io.Serializable;

public abstract class User implements Serializable {

    protected int userID;
    protected String name;
    protected String email;
    protected String password;
    protected String role;

    public User() {}

    public User(int userID, String name, String email, String password, String role) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // ---------- UML Methods ----------

    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public void register() {
        System.out.println(role + " registered: " + name);
    }

    public void viewProfile() {
        System.out.println("User Profile:");
        System.out.println("Name: " + name);
        System.out.println("Role: " + role);
    }

    // ---------- Getters ----------
    public int getUserID() { return userID; }
    public String getName() { return name; }
    public String getRole() { return role; }
}
