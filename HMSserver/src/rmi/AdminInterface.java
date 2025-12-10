package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.Patient;
import model.Report;
import java.util.List;

public interface AdminInterface extends Remote {
    //Only methods the CLIENT is allowed to call.
    
    // User Management Operations
    // Returns list of Object arrays: [userID, fullName, email, role]
    List<Object[]> getAllUsers() throws RemoteException;
    
    // Add user: parameters (fullName, email, role)
    boolean addUser(String fullName, String email, String role) throws RemoteException;
    
    // Delete user by ID
    boolean deleteUser(int userID) throws RemoteException;
    
    // Update user: parameters (userID, fullName, email, role)
    boolean updateUser(int userID, String fullName, String email, String role) throws RemoteException;
    
    // Legacy method
    void print() throws RemoteException;
}
