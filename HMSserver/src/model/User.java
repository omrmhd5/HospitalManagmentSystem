package model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmi.UserInterface;
import server.DB;

public abstract class User extends UnicastRemoteObject implements UserInterface {

    protected int userID;
    protected String name;
    protected String email;
    protected String password;
    protected String role;
    protected DB db;

    public User() throws RemoteException {}

    public User(int userID, String name, String email, String password, String role) throws RemoteException {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Constructor for RMI service
    public User(DB db) throws RemoteException {
        this.db = db;
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

    // ---------- UserInterface Methods ----------
    
    // Mahmoud
    @Override
    public String login(String email, String password, String role) throws RemoteException {
        if (db == null) {
            return "Login failed: Database not initialized";
        }
        String userName = db.authenticateUser(email, password, role);
        
        if (userName == null) {
            return "Login failed: Invalid credentials";
        }
        
        return "Login successful! Welcome " + userName + " (" + role + ")";
    }
    
    // Mahmoud
    @Override
    public boolean register(int userID, String name, String email, String password, String role) throws RemoteException {
        if (db == null) {
            return false;
        }
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
        if (db == null) {
            return false;
        }
        if (db.emailExists(email)) {
            return false;
        }
        
        return db.registerPatientExtended(userID, name, email, password, gender, age, phone, address);
    }
    
    // Mahmoud
    @Override
    public boolean registerDoctor(int userID, String name, String email, String password,
                                 String specialization, String schedule, String phone) throws RemoteException {
        if (db == null) {
            return false;
        }
        if (db.emailExists(email)) {
            return false;
        }
        
        return db.registerDoctorExtended(userID, name, email, password, specialization, schedule, phone);
    }
    
    // Mahmoud
    @Override
    public boolean registerPharmacist(int userID, String name, String email, String password,
                                     String phone, String department) throws RemoteException {
        if (db == null) {
            return false;
        }
        if (db.emailExists(email)) {
            return false;
        }
        
        return db.registerPharmacistExtended(userID, name, email, password, phone, department);
    }
    
    // Mahmoud
    @Override
    public boolean registerLabTechnician(int userID, String name, String email, String password,
                                        String phone, String labDepartment) throws RemoteException {
        if (db == null) {
            return false;
        }
        if (db.emailExists(email)) {
            return false;
        }
        
        return db.registerLabTechnicianExtended(userID, name, email, password, phone, labDepartment);
    }
    
    // Mahmoud
    @Override
    public boolean registerReceptionist(int userID, String name, String email, String password,
                                        String phone, String department) throws RemoteException {
        if (db == null) {
            return false;
        }
        if (db.emailExists(email)) {
            return false;
        }
        
        return db.registerReceptionistExtended(userID, name, email, password, phone, department);
    }
    
    // Mahmoud
    @Override
    public boolean registerAdmin(int userID, String name, String email, String password,
                                String phone, String accessLevel) throws RemoteException {
        if (db == null) {
            return false;
        }
        if (db.emailExists(email)) {
            return false;
        }
        
        return db.registerAdminExtended(userID, name, email, password, phone, accessLevel);
    }
    
    // Mahmoud
    @Override
    public boolean emailExists(String email) throws RemoteException {
        if (db == null) {
            return false;
        }
        return db.emailExists(email);
    }

    // ---------- Getters ----------
    public int getUserID() { return userID; }
    public String getName() { return name; }
    public String getRole() { return role; }
}
