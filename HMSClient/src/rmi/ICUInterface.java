package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ICUInterface extends Remote {
    // Rana
    boolean createICURequest(int requestID, String doctorName, String patientName, 
                             String date, String time, String urgency, 
                             String diagnosis, String expectedDuration) throws RemoteException;
    
    // Rana 
    String getICURequestsForPatient(int patientID) throws RemoteException;
  
    String getCurrentICUState() throws RemoteException;
    String getICURequestStatus(int requestID) throws RemoteException;

    
    // Chain of Responsibility - Get all pending ICU requests as formatted strings
    List<String> getPendingICURequests() throws RemoteException;
    
    // Chain of Responsibility - Update ICU request status
    boolean updateICURequestStatus(int requestID, String status) throws RemoteException;
    
    // Chain of Responsibility - Get ICU request details by ID as formatted string
    String getICURequestDetails(int requestID) throws RemoteException;
    
    // Chain of Responsibility - Process request through chain and return result
    String processRequestThroughChain(int requestID, String handlerRole) throws RemoteException;
    
    // Chain of Responsibility - Reject request (only if handler has authority)
    String rejectRequestThroughChain(int requestID, String handlerRole) throws RemoteException;
    
    // Chain of Responsibility - Check if handler can reject request (without actually rejecting)
    String canRejectRequest(int requestID, String handlerRole) throws RemoteException;
}
