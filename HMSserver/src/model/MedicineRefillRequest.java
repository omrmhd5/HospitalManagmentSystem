package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MedicineRefillRequest implements Serializable {

    private int requestID;
    private int pharmacistID;
    private String medicineName;
    private int quantity;
    private String status;  // Pending / Approved / Rejected / Fulfilled
    private String requestDate;
    private String requestTime;
    
    private static int requestCounter = 2000; // Static counter for generating request IDs

    public MedicineRefillRequest() {}

    public MedicineRefillRequest(int requestID, int pharmacistID, String medicineName, int quantity) {
        this.requestID = requestID;
        this.pharmacistID = pharmacistID;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.status = "Pending";
        
        // Set current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.requestDate = now.format(dateFormatter);
        this.requestTime = now.format(timeFormatter);
    }

    // Static method to generate next request ID
    public static int getNextRequestID() {
        return requestCounter++;
    }

    // Getters
    public int getRequestID() { return requestID; }
    public int getPharmacistID() { return pharmacistID; }
    public String getMedicineName() { return medicineName; }
    public int getQuantity() { return quantity; }
    public String getStatus() { return status; }
    public String getRequestDate() { return requestDate; }
    public String getRequestTime() { return requestTime; }

    // Setters
    public void setRequestID(int requestID) { this.requestID = requestID; }
    public void setPharmacistID(int pharmacistID) { this.pharmacistID = pharmacistID; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setStatus(String status) { this.status = status; }
    public void setRequestDate(String requestDate) { this.requestDate = requestDate; }
    public void setRequestTime(String requestTime) { this.requestTime = requestTime; }

    // Status update methods
    public void approve() { this.status = "Approved"; }
    public void reject() { this.status = "Rejected"; }
    public void fulfill() { this.status = "Fulfilled"; }

    public String toReadableString() {
        return "Medicine Refill Request ID: " + requestID +
               "\nPharmacist ID: " + pharmacistID +
               "\nMedicine: " + medicineName +
               "\nQuantity: " + quantity +
               "\nStatus: " + status +
               "\nRequest Date: " + requestDate +
               "\nRequest Time: " + requestTime;
    }
}

