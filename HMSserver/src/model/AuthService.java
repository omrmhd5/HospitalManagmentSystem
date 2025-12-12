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
        
        // Mahmoud - Register in both user collection and role-specific collection
        return db.registerUserWithRole(userID, name, email, password, role);
    }
    
    // Mahmoud
    @Override
    public boolean registerPatient(int userID, String name, String email, String password, 
                                   String gender, int age, String phone, String address) throws RemoteException {
        if (db.emailExists(email)) {
            return false;
        }
        
        return db.registerPatientExtended(userID, name, email, password, gender, age, phone, address);
    }
    
    // Mahmoud
    @Override
    public boolean registerDoctor(int userID, String name, String email, String password,
                                 String specialization, String schedule, String phone) throws RemoteException {
        if (db.emailExists(email)) {
            return false;
        }
        
        return db.registerDoctorExtended(userID, name, email, password, specialization, schedule, phone);
    }
    
    // Mahmoud
    @Override
    public boolean emailExists(String email) throws RemoteException {
        return db.emailExists(email);
    }
}

