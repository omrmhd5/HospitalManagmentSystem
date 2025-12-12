package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LabTestInterface extends Remote {
    // Submit a new lab test request
    boolean submitLabTestRequest(String doctorName, String doctorEmail, String doctorPhone,
                                 String testType, String patientName, int patientAge,
                                 String patientGender, String patientDateOfBirth) throws RemoteException;
    
    // Get available test types
    String[] getAvailableTestTypes() throws RemoteException;
    
    // Get test request status by ID
    String getTestStatus(int requestID) throws RemoteException;
}

