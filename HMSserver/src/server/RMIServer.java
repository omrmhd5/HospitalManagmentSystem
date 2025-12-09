package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import remote.HospitalService;
import remote.HospitalServiceImpl;

public class RMIServer {

    public static void main(String[] args) {
        try {
            // Create remote object
            HospitalService service = new HospitalServiceImpl();

            // Create RMI registry on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            // Bind remote object with name "hospital"
            registry.bind("hospital", service);

            System.out.println("Hospital RMI server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
