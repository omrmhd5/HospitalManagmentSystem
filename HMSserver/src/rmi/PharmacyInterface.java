package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PharmacyInterface extends Remote {
    // Tasneem
    boolean addDrug(int drugID, String name, String category, int quantity, 
                    int reorderLevel, String expiryDate) throws RemoteException;
    
    // Tasneem 
    String getDrugByID(int id) throws RemoteException;
    
    // Tasneem
    boolean receiveDrugStock(int drugID, int amount) throws RemoteException;
    
    // Tasneem
    boolean dispenseDrug(int drugID, int amount) throws RemoteException;
    
    // Tasneem
    boolean updateDrug(int drugID, String name, String category, int quantity, 
                       int reorderLevel, String expiryDate) throws RemoteException;
    
   
    boolean deleteDrug(int drugID) throws RemoteException;
    
    
    java.util.List<String> getAllDrugs() throws RemoteException;
    
    
    java.util.List<String> getAllPharmacists() throws RemoteException;
    
    // Ibrahim
    String requestMedicineRefill(int pharmacistID, String medicineName, int quantity) throws RemoteException;
}
