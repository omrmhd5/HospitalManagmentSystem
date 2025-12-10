package model;

import java.io.Serializable;

public class Drug implements Serializable {

    private int drugID;
    private String name;
    private String category;
    private int quantity;
    private int reorderLevel;
    private String expiryDate;

    public Drug() {}

    public Drug(int drugID, String name, String category, int quantity,
                int reorderLevel, String expiryDate) {

        this.drugID = drugID;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.expiryDate = expiryDate;
    }

    public int getDrugID() { return drugID; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public int getReorderLevel() { return reorderLevel; }
    public String getExpiryDate() { return expiryDate; }

    public void receiveStock(int amount) {
        this.quantity += amount;
    }

    public boolean dispenseStock(int amount) {
        if (quantity >= amount) {
            this.quantity -= amount;
            return true;
        }
        return false;
    }

    public boolean needsReorder() {
        return quantity <= reorderLevel;
    }
}
