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
import model.LabTest;

import model.Admin;
import rmi.AdminInterface;
import rmi.AppointmentInterface;
import rmi.PatientRecordInterface;
import rmi.PrescriptionInterface;import rmi.PatientInterface;
import rmi.LabTestInterface;

public class RMIServer {
    // Mahmoud
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
        DB db = new DB();
        
        // Here we create our remote objects
        AdminInterface admin = new Admin();
        AppointmentInterface appointmentService = new Appointment(db);
        PrescriptionInterface prescriptionService = new Prescription(db);
        PatientRecordInterface patientRecordService = new Patient(db);
        
        PatientInterface patient = new Patient(db);
        
        // Create lab test service
        LabTestInterface labTest = new LabTest(db);
        
        Registry registry = LocateRegistry.createRegistry(1099);
        
        registry.bind("admin", admin);
        registry.bind("appointment", appointmentService);
        registry.bind("prescription", prescriptionService);
        registry.bind("patientRecord", patientRecordService);
        
        // Our remote object patient is binded to the name "patient"
        registry.bind("patient", patient);
        
        // Our remote object labtest is binded to the name "labtest"
        registry.bind("labtest", labTest);
      
        PharmacistInterface pharmacistInterface = new PharmacistInterface() {

            @Override
            public String requestMedicineRefill(int pharmacistID, String medicineName, int quantity)
                    throws RemoteException {
                return db.requestMedicineRefill(pharmacistID, medicineName, quantity);
            }
        };

        registry.bind("pharmacist", pharmacistInterface);

       
        LabTechnicianInterface labTechInterface = new LabTechnicianInterface() {

            @Override
            public String recordLabTestResult(int testID, String result) throws RemoteException {
                return db.recordLabTestResult(testID, result);
            }
        };

        registry.bind("labtech", labTechInterface);
        
        System.out.println("The server is ready");
    }
}
