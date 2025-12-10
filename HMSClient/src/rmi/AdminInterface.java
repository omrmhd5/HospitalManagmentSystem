package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AdminInterface extends Remote {
    //Only methods the CLIENT is allowed to call.
    // Patient operations
//    Patient getPatient(int id) throws RemoteException;
    // Admin reports
//    Report generateReport(String type) throws RemoteException;
    void print() throws RemoteException;
}
