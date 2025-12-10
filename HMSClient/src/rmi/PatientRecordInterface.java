package rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface PatientRecordInterface extends Remote {
    // Mahmoud
    String viewPatientRecord(String patientName) throws RemoteException;
}

