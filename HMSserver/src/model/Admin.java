package model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import rmi.AdminInterface;

public class Admin extends User implements AdminInterface {

    private int adminID;
    private String accessLevel;
    
    // In-memory user storage using Object arrays: [userID, fullName, email, role]
    private static List<Object[]> userDatabase = new ArrayList<>();
    private static int userIDCounter = 1;
    
    // Initialize with sample data
    // Salma
    static {
        userDatabase.add(new Object[]{userIDCounter++, "Salma Mehrez", "sal@gmail.com", "Admin"});
        userDatabase.add(new Object[]{userIDCounter++, "Tasnim Hatem", "tas@gmail.com", "Receptionist"});
        userDatabase.add(new Object[]{userIDCounter++, "Rana Hatem", "ran@gmail.com", "Pharmacist"});
        userDatabase.add(new Object[]{userIDCounter++, "Omar Mohamed", "oma@gmail.com", "Lab Technician"});
        userDatabase.add(new Object[]{userIDCounter++, "Omar Mahmoud", "omr@gmail.com", "Doctor"});
    }

    public Admin() throws RemoteException {}

    public Admin(int userID, String name, String email, String password,
                 int adminID, String accessLevel) throws RemoteException {
        super(userID, name, email, password, "Admin");
        this.adminID = adminID;
        this.accessLevel = accessLevel;
    }

    // ---------- RMI INTERFACE METHODS - User Management ----------
    // Salma
    
    @Override
    public List<Object[]> getAllUsers() throws RemoteException {
        System.out.println("Admin: Retrieving all users (" + userDatabase.size() + " users)");
        // Return a copy of the list
        List<Object[]> copy = new ArrayList<>();
        for (Object[] user : userDatabase) {
            copy.add(user.clone());
        }
        return copy;
    }
    
    @Override
    public boolean addUser(String fullName, String email, String role) throws RemoteException {
        try {
            // Create user array: [userID, fullName, email, role]
            Object[] newUser = new Object[]{userIDCounter++, fullName, email, role};
            userDatabase.add(newUser);
            System.out.println("Admin: User added - " + fullName + " (ID: " + newUser[0] + ")");
            return true;
        } catch (Exception e) {
            System.err.println("Error adding user: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean deleteUser(int userID) throws RemoteException {
        try {
            boolean removed = userDatabase.removeIf(user -> (int)user[0] == userID);
            if (removed) {
                System.out.println("Admin: User deleted with ID: " + userID);
            } else {
                System.out.println("Admin: User not found with ID: " + userID);
            }
            return removed;
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean updateUser(int userID, String fullName, String email, String role) throws RemoteException {
        try {
            for (int i = 0; i < userDatabase.size(); i++) {
                if ((int)userDatabase.get(i)[0] == userID) {
                    userDatabase.set(i, new Object[]{userID, fullName, email, role});
                    System.out.println("Admin: User updated - " + fullName);
                    return true;
                }
            }
            System.out.println("Admin: User not found for update with ID: " + userID);
            return false;
        } catch (Exception e) {
            System.err.println("Error updating user: " + e.getMessage());
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
