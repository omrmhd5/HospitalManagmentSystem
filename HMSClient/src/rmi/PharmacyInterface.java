package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.Drug;

public interface PharmacyInterface extends Remote {
    // Tasneem
    boolean addDrug(Drug d) throws RemoteException;
    
    // Tasneem
    Drug getDrugByID(int id) throws RemoteException;
    
    // Tasneem
    boolean receiveDrugStock(int drugID, int amount) throws RemoteException;
    
    // Tasneem
    boolean dispenseDrug(int drugID, int amount) throws RemoteException;
    
    // Tasneem
    boolean updateDrug(Drug d) throws RemoteException;
}
