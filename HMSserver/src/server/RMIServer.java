package server;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Admin;
import model.Appointment;
import rmi.AdminInterface;
import rmi.AppointmentInterface;
public class RMIServer {
    // Mahmoud
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
        DB db = new DB();
        
        AdminInterface admin = new Admin();
        AppointmentInterface appointmentService = new Appointment(db);
        
        Registry registry = LocateRegistry.createRegistry(1099);
        
        registry.bind("admin", admin);
        registry.bind("appointment", appointmentService);
        
        System.out.println("The server is ready");
    }
}
