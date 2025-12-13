package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DoctorRequestInterface extends Remote {
    
    // Execute lab test request using strategy pattern
    boolean executeLabTestRequest(String doctorEmail, String testType,
                                 String patientName, int patientAge, String patientGender,
                                 String patientDateOfBirth, String doctorPhone) throws RemoteException;
    
    // Execute medicine refill request using strategy pattern
    boolean executeMedicineRefillRequest(String doctorEmail, 
                                        String prescriptionID, String medicineName,
                                        int pharmacistID, int quantity) throws RemoteException;
    
    // Execute ICU request using strategy pattern
    boolean executeICURequest(String doctorEmail, String patientName,
                             String date, String time, String urgency, String diagnosis,
                             String expectedDuration) throws RemoteException;
    
    //Rana
    int getLastICURequestID() throws RemoteException;
}

