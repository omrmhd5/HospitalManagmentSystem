package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Admin;

import rmi.AdminInterface;

public class RMIServer {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
                   
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
        
        //Calling the class for the database 
        DB db = new DB();
        
        // Here we create our remote object
        AdminInterface admin = new Admin();
        
        // An RMI Registry initialized on port 1099
        Registry registry = LocateRegistry.createRegistry(1099);
        
        // Our remote object admin is binded to the name "admin"
        registry.bind("admin", admin);
        
        // Outputs that the server is ready
        System.out.println("The server is ready");
    }
}
