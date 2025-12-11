package client;

import controllers.LoginController;
import gui.Login;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        
        // Mahmoud
        Registry registry = LocateRegistry.getRegistry(1099);
        
        // Mahmoud
        Login l = new Login();
        LoginController lc = new LoginController(l, registry);
    }
}
