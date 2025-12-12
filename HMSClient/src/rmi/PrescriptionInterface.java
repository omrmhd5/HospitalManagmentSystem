package rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface PrescriptionInterface extends Remote {
    // Mahmoud
    String recordPrescription(String patientName, String doctorName, String medicine, String dosage, String diagnosis) throws RemoteException;
}


