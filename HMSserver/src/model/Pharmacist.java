package model;

import java.rmi.RemoteException;
import rmi.PharmacyInterface;
import server.DB;

public class Pharmacist extends User implements PharmacyInterface {

    private int pharmacistID;
    private String[] inventoryList;
    private final DB db;

    // Tasneem
    public Pharmacist(DB db) throws RemoteException {
        this.db = db;
    }

    // Tasneem
    public Pharmacist(int userID, String name, String email, String password,
                      int pharmacistID, String[] inventoryList, DB db) throws RemoteException {
        super(userID, name, email, password, "Pharmacist");
        this.pharmacistID = pharmacistID;
        this.inventoryList = inventoryList;
        this.db = db;
    }

    // Tasneem
    public void manageDrugInventory() {
        System.out.println("Managing drug inventory...");
    }

    // Tasneem
    @Override
    public boolean addDrug(Drug d) throws RemoteException {
        db.addDrug(d);
        return true;
    }

    // Tasneem
    @Override
    public Drug getDrugByID(int id) throws RemoteException {
        return db.getDrugByID(id);
    }

    // Tasneem
    @Override
    public boolean receiveDrugStock(int drugID, int amount) throws RemoteException {
        return true;
    }

    // Tasneem
    @Override
    public boolean dispenseDrug(int drugID, int amount) throws RemoteException {
        return true;
    }

    // Tasneem
    @Override
    public boolean updateDrug(Drug d) throws RemoteException {
        return db.updateDrug(d);
    }

    // Ibrahim
    public String requestMedicineRefill(String medicineName, int quantity) {
        return "Refill request submitted successfully for " 
                + medicineName + " (Quantity: " + quantity + ")";
    }

    public int getPharmacistID() { return pharmacistID; }
    public void setPharmacistID(int pharmacistID) { this.pharmacistID = pharmacistID; }
    
    public String[] getInventoryList() { return inventoryList; }
    public void setInventoryList(String[] inventoryList) { this.inventoryList = inventoryList; }
}
