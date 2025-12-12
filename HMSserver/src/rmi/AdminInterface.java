package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AdminInterface extends Remote {

    void print() throws RemoteException;
}
