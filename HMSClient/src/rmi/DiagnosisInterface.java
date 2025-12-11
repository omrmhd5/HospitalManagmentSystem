package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DiagnosisInterface extends Remote {
    // Tasneem
    boolean recordDiagnosis(int diagnosisID, int appointmentID, String patientName, 
                            String doctorName, String clinicalNotes, String diagnosis) throws RemoteException;
}

