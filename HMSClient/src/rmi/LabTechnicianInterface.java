package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LabTechnicianInterface extends Remote {
    // Ibrahim
    String recordLabTestResult(int testID, String result) throws RemoteException;
}
