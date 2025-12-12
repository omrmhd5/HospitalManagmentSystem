package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LabTechnicianInterface extends Remote {
    // Ibrahim
    String recordLabTestResult(int testID, String result) throws RemoteException;
    
    // Get all pending lab tests - returns list of formatted strings "ID - Type - Patient"
    java.util.List<String> getPendingLabTests() throws RemoteException;
    
    // Get lab test details by ID
    String getLabTestDetails(int testID) throws RemoteException;
}
