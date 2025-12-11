package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuthInterface extends Remote {
    
    // Mahmoud - Login user
    String login(String email, String password, String role) throws RemoteException;
    
    // Mahmoud - Register user
    boolean register(int userID, String name, String email, String password, String role) throws RemoteException;
    
    // Mahmoud - Check if email exists
    boolean emailExists(String email) throws RemoteException;
}

