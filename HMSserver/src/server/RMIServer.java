package server;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Admin;
import model.Appointment;
import model.Diagnosis;
import model.ICURoom;
import model.LabTechnician;
import model.LabTest;
import model.Patient;
import model.DoctorRequestService;
import model.Pharmacist;
import model.Prescription;
import model.UserImpl;
import rmi.AdminInterface;
import rmi.AppointmentInterface;
import rmi.DiagnosisInterface;
import rmi.DoctorRequestInterface;
import rmi.ICUInterface;
import rmi.LabTechnicianInterface;
import rmi.LabTestInterface;
import rmi.PatientInterface;
import rmi.PharmacyInterface;
import rmi.PrescriptionInterface;
import rmi.UserInterface;

public class RMIServer {
   
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {

        // Disable MongoDB logs
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
        DB db = new DB();
        
        // Create remote objects
        AdminInterface adminService = new Admin(db);
        AppointmentInterface appointmentService = new Appointment(db);
        PrescriptionInterface prescriptionService = new Prescription(db);
        PatientInterface patientService = new Patient(db);
        LabTestInterface labTestService = new LabTest(db);
        LabTechnicianInterface labTechService = new LabTechnician(db);
        PharmacyInterface pharmacyService = new Pharmacist(db);
        ICUInterface icuService = new ICURoom(db);
        DiagnosisInterface diagnosisService = new Diagnosis(db);
        UserInterface userService = new UserImpl(db);
        DoctorRequestInterface doctorRequestService = new DoctorRequestService(
            db, labTestService, pharmacyService, icuService
        );
        
        // Create registry
        Registry registry = LocateRegistry.createRegistry(1099);
        
        // Bind all services
        registry.bind("admin", adminService);
        registry.bind("appointment", appointmentService);
        registry.bind("prescription", prescriptionService);
        registry.bind("patient", patientService);
        registry.bind("labtest", labTestService);
        registry.bind("labtech", labTechService);
        registry.bind("pharmacy", pharmacyService);
        registry.bind("icu", icuService);
        registry.bind("diagnosis", diagnosisService);
        registry.bind("user", userService);
        registry.bind("doctorrequest", doctorRequestService);
        
        System.out.println("The server is ready");
    }
}
