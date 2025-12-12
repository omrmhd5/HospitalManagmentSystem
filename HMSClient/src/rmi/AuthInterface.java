package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuthInterface extends Remote {
    
    // Mahmoud - Login user
    String login(String email, String password, String role) throws RemoteException;
    
    // Mahmoud - Register user (basic)
    boolean register(int userID, String name, String email, String password, String role) throws RemoteException;
    
    // Mahmoud - Register patient (extended)
    boolean registerPatient(int userID, String name, String email, String password, 
                           String gender, int age, String phone, String address) throws RemoteException;
    
    // Mahmoud - Register doctor (extended)
    boolean registerDoctor(int userID, String name, String email, String password,
                          String specialization, String schedule, String phone) throws RemoteException;
    
    // Mahmoud - Check if email exists
    boolean emailExists(String email) throws RemoteException;
}

