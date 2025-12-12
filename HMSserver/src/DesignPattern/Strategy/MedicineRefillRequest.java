package DesignPattern.Strategy;

import model.Doctor;
import rmi.PharmacyInterface;
import java.rmi.RemoteException;

public class MedicineRefillRequest implements DoctorRequestStrategy {

    private String prescriptionID;
    private String medicineName;
    private int pharmacistID;
    private int quantity;
    private PharmacyInterface pharmacyService;

    public MedicineRefillRequest(String prescriptionID, String medicineName, 
                                int pharmacistID, int quantity, PharmacyInterface pharmacyService) {
        this.prescriptionID = prescriptionID;
        this.medicineName = medicineName;
        this.pharmacistID = pharmacistID;
        this.quantity = quantity;
        this.pharmacyService = pharmacyService;
    }

    @Override
    public void executeRequest(Doctor doctor) {
        try {
            // Execute the actual medicine refill request
            pharmacyService.requestMedicineRefill(pharmacistID, medicineName, quantity);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
