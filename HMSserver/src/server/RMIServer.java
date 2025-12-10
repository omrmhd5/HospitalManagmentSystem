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

public class RMIServer {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {

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
            public String requestMedicineRefill(int pharmacistID, String medicineName, int quantity)
                    throws RemoteException {
                return db.requestMedicineRefill(pharmacistID, medicineName, quantity);
            }
        };

        registry.bind("pharmacist", pharmacistInterface);

        System.out.println("The server is ready");
    }
}
