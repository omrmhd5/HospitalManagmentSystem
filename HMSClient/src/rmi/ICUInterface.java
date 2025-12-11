package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.ICURequest;

public interface ICUInterface extends Remote {
    // Rana
    boolean createICURequest(ICURequest req) throws RemoteException;
    
    // Rana
    List<ICURequest> getICURequestsForPatient(int patientID) throws RemoteException;
}
