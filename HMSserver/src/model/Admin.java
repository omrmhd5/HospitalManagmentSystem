package model;

import java.rmi.RemoteException;
import java.util.List;
import org.bson.Document;
import rmi.AdminInterface;
import server.DB;

public class Admin extends User implements AdminInterface {

    private int adminID;
    private String accessLevel;
    private final DB db;

    public Admin() throws RemoteException {
        this.db = null;
    }
    
    public Admin(DB db) throws RemoteException {
        this.db = db;
    }

    public Admin(int userID, String name, String email, String password,
                 int adminID, String accessLevel) throws RemoteException {
        super(userID, name, email, password, "Admin");
        this.adminID = adminID;
        this.accessLevel = accessLevel;
        this.db = null;
    }
    
    public Admin(int userID, String name, String email, String password,
                 int adminID, String accessLevel, DB db) throws RemoteException {
        super(userID, name, email, password, "Admin");
        this.adminID = adminID;
        this.accessLevel = accessLevel;
        this.db = db;
    }

    // ---------- RMI INTERFACE METHODS - User Management ----------
    // Salma
    
    @Override
    public List<Object[]> getAllUsers() throws RemoteException {
        if (db == null) {
            throw new RemoteException("Database connection not available");
        }
        return db.getAllUsers();
    }
    
    @Override
    public boolean addUser(String fullName, String email, String role) throws RemoteException {
        if (db == null) {
            throw new RemoteException("Database connection not available");
        }
        try {
            // Mahmoud
            int userID = db.getNextUserID();
            // Mahmoud - Use registerUserWithRole to add to both user collection and role-specific collection
            // Default password is "password" - admin can change it later if needed
            boolean success = db.registerUserWithRole(userID, fullName, email, "password", role);
            if (success) {
                System.out.println("Admin: User added to database - " + fullName + " (ID: " + userID + ", Role: " + role + ")");
            }
            return success;
        } catch (Exception e) {
            System.err.println("Error adding user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteUser(int userID) throws RemoteException {
        if (db == null) {
            throw new RemoteException("Database connection not available");
        }
        try {
            db.deleteUserById(userID);
            System.out.println("Admin: User deleted from database with ID: " + userID);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean updateUser(int userID, String fullName, String email, String role) throws RemoteException {
        if (db == null) {
            throw new RemoteException("Database connection not available");
        }
        try {
            // Mahmoud - Get old user document to preserve password
            Document oldUserDoc = db.getUserById(userID);
            String password = "password"; // Default password
            if (oldUserDoc != null && oldUserDoc.getString("password") != null) {
                password = oldUserDoc.getString("password");
            }
            
            // Mahmoud - Delete old user (this will also delete from role-specific collection)
            db.deleteUserById(userID);
            
            // Mahmoud - Use registerUserWithRole to add to both user collection and role-specific collection
            boolean success = db.registerUserWithRole(userID, fullName, email, password, role);
            if (success) {
                System.out.println("Admin: User updated in database - " + fullName + " (ID: " + userID + ", Role: " + role + ")");
            }
            return success;
        } catch (Exception e) {
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ---------- UML METHODS ----------
    public void manageUser(User u) {
        System.out.println("Managing user: " + u.getName());
    }

    public Report generateReport(String type) {
        Report r = new Report(type);
        r.generateReport();
        return r;
    }

    public int getAdminID() { return adminID; }

    @Override
    public void print() throws RemoteException {
        System.out.println("Hello World");   
    }

}
