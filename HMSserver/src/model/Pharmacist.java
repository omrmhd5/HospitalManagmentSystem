package model;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Pharmacist extends User implements Serializable {

    private int pharmacistID;
    private String[] inventoryList;

    public Pharmacist()throws RemoteException {}

    public Pharmacist(int userID, String name, String email, String password,
                      int pharmacistID, String[] inventoryList) throws RemoteException {
        super(userID, name, email, password, "Pharmacist");
        this.pharmacistID = pharmacistID;
        this.inventoryList = inventoryList;
    }

    // ---------- UML METHOD ----------
    public void manageDrugInventory() {
        System.out.println("Managing drug inventory...");
    }
//ibrahim
    public int getPharmacistID() { return pharmacistID; }
    
    public String requestMedicineRefill(String medicineName, int quantity) {

    // Basic validation rules
    if (medicineName == null || medicineName.isBlank()) {
        return "Medicine name cannot be empty.";
    }

    if (quantity <= 0) {
        return "Quantity must be greater than zero.";
    }

    // Example pharmacist-side processing (UML level)
    return "Refill request submitted successfully for " 
            + medicineName + " (Quantity: " + quantity + ")";
}
}
