package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Admin;
import rmi.AdminInterface;
import rmi.PharmacistInterface;
import rmi.LabTechnicianInterface;

public class RMIServer {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {

        // Disable MongoDB logs
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        // Initialize DB
        DB db = new DB();

        // Create RMI registry
        Registry registry = LocateRegistry.createRegistry(1099);

        // -------------------- ADMIN BINDING --------------------
        AdminInterface admin = new Admin();
        registry.bind("admin", admin);

        // ----------------- PHARMACIST BINDING ------------------
        PharmacistInterface pharmacistInterface = new PharmacistInterface() {

            @Override
            public String requestMedicineRefill(int pharmacistID,
                                                String medicineName,
                                                int quantity)
                    throws RemoteException {
                return db.requestMedicineRefill(
                        pharmacistID, medicineName, quantity);
            }
        };
        registry.bind("pharmacist", pharmacistInterface);

        //  LAB TECHNICIAN BINDING 
        LabTechnicianInterface labTechInterface = new LabTechnicianInterface() {

            @Override
            public String recordLabTestResult(int testID,
                                              String result)
                    throws RemoteException {
                return db.recordLabTestResult(testID, result);
            }
        };
        registry.bind("labtech", labTechInterface);

        System.out.println("RMI Server is ready...");
    }
}
