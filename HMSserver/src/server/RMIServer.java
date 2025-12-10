package server;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Admin;
import model.Appointment;
import model.Patient;
import model.Prescription;
import rmi.AdminInterface;
import rmi.AppointmentInterface;
import rmi.PatientRecordInterface;
import rmi.PrescriptionInterface;
public class RMIServer {
    // Mahmoud
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
        DB db = new DB();
        
        AdminInterface admin = new Admin();
        AppointmentInterface appointmentService = new Appointment(db);
        PrescriptionInterface prescriptionService = new Prescription(db);
        PatientRecordInterface patientRecordService = new Patient(db);
        
        Registry registry = LocateRegistry.createRegistry(1099);
        
        registry.bind("admin", admin);
        registry.bind("appointment", appointmentService);
        registry.bind("prescription", prescriptionService);
        registry.bind("patientRecord", patientRecordService);
        
        System.out.println("The server is ready");
    }
}
