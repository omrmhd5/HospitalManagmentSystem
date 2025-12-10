package client;

import controllers.LoginController;
import gui.Login;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        
        // We create an object from the GUI window
        Login l = new Login();

        // We connect to the RMI Registry
        Registry registry = LocateRegistry.getRegistry(1099);
        
        // we create a new object from the controller and we pass it the
        // gui object along with the registry object
        LoginController lc = new LoginController(l, registry);
        
        // Our remote object g is binded to the name "grade"
//        AdminInterface admin = (AdminInterface) registry.lookup("admin");
//        admin.print();
    }
}
