package model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmi.AuthInterface;
import server.DB;

public class AuthService extends UnicastRemoteObject implements AuthInterface {
    
    private final DB db;
    
    // Mahmoud
    public AuthService(DB db) throws RemoteException {
        this.db = db;
    }
    
    // Mahmoud
    @Override
    public String login(String email, String password, String role) throws RemoteException {
        String userName = db.authenticateUser(email, password, role);
        
        if (userName == null) {
            return "Login failed: Invalid credentials";
        }
        
        return "Login successful! Welcome " + userName + " (" + role + ")";
    }
    
    // Mahmoud
    @Override
    public boolean register(int userID, String name, String email, String password, String role) throws RemoteException {
        if (db.emailExists(email)) {
            return false;
        }
        
        return db.registerUser(userID, name, email, password, role);
    }
    
    // Mahmoud
    @Override
    public boolean emailExists(String email) throws RemoteException {
        return db.emailExists(email);
    }
}

