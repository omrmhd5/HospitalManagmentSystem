package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Admin;
import model.Patient;
import model.LabTest;

import rmi.AdminInterface;
import rmi.PatientInterface;
import rmi.LabTestInterface;

public class RMIServer {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
                   
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
        
        //Calling the class for the database 
        DB db = new DB();
        
        // Here we create our remote objects
        AdminInterface admin = new Admin();
        
        PatientInterface patient = new Patient();
        
        // Create lab test service
        LabTestInterface labTest = new LabTest();
        
        // An RMI Registry initialized on port 1099
        Registry registry = LocateRegistry.createRegistry(1099);
        
        // Our remote object admin is binded to the name "admin"
        registry.bind("admin", admin);
        
        // Our remote object patient is binded to the name "patient"
        registry.bind("patient", patient);
        
        // Our remote object labtest is binded to the name "labtest"
        registry.bind("labtest", labTest);
        
        // Outputs that the server is ready
        System.out.println("The server is ready");
    }
}
