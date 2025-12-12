package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICUInterface extends Remote {
    // Rana
    boolean createICURequest(int requestID, String doctorName, String patientName, 
                             String date, String time, String urgency, 
                             String diagnosis, String expectedDuration) throws RemoteException;
    
    // Rana - Returns formatted string of all requests
    String getICURequestsForPatient(int patientID) throws RemoteException;
    String getCurrentICUState() throws RemoteException;
}
