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
    public boolean addDrug(int drugID, String name, String category, int quantity, 
                           int reorderLevel, String expiryDate) throws RemoteException {
        Drug drug = new Drug(drugID, name, category, quantity, reorderLevel, expiryDate);
        db.addDrug(drug);
        return true;
    }

    // Tasneem
    @Override
    public String getDrugByID(int id) throws RemoteException {
        Drug drug = db.getDrugByID(id);
        
        if (drug == null) {
            return "Drug not found";
        }
        
        String result = "Drug Details\n" +
                        "====================\n" +
                        "Drug ID: " + drug.getDrugID() + "\n" +
                        "Name: " + drug.getName() + "\n" +
                        "Category: " + drug.getCategory() + "\n" +
                        "Quantity: " + drug.getQuantity() + "\n" +
                        "Reorder Level: " + drug.getReorderLevel() + "\n" +
                        "Expiry Date: " + drug.getExpiryDate();
        
        return result;
    }

    // Tasneem
    @Override
    public boolean receiveDrugStock(int drugID, int amount) throws RemoteException {
        Drug drug = db.getDrugByID(drugID);
        if (drug != null) {
            drug.receiveStock(amount);
            db.updateDrug(drug);
            return true;
        }
        return false;
    }

    // Tasneem
    @Override
    public boolean dispenseDrug(int drugID, int amount) throws RemoteException {
        Drug drug = db.getDrugByID(drugID);
        if (drug != null) {
            boolean success = drug.dispenseStock(amount);
            if (success) {
                db.updateDrug(drug);
            }
            return success;
        }
        return false;
    }

    // Tasneem
    @Override
    public boolean updateDrug(int drugID, String name, String category, int quantity, 
                              int reorderLevel, String expiryDate) throws RemoteException {
        Drug drug = new Drug(drugID, name, category, quantity, reorderLevel, expiryDate);
        return db.updateDrug(drug);
    }

    // Ibrahim
    @Override
    public String requestMedicineRefill(int pharmacistID, String medicineName, int quantity) throws RemoteException {
        return db.requestMedicineRefill(pharmacistID, medicineName, quantity);
    }

    public int getPharmacistID() { return pharmacistID; }
    public void setPharmacistID(int pharmacistID) { this.pharmacistID = pharmacistID; }
    
    public String[] getInventoryList() { return inventoryList; }
    public void setInventoryList(String[] inventoryList) { this.inventoryList = inventoryList; }
}
