package model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public abstract class User extends UnicastRemoteObject {

    protected int userID;
    protected String name;
    protected String email;
    protected String password;
    protected String role;

    public User() throws RemoteException {}

    public User(int userID, String name, String email, String password, String role) throws RemoteException {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // ---------- UML Methods ----------

    public boolean login(String email, String password) throws RemoteException {
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
