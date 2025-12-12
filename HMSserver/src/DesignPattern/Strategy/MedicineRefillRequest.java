package DesignPattern.Strategy;

import model.Appointment;
import model.Doctor;

public class MedicineRefillRequest implements DoctorRequestStrategy {

    private String prescriptionID;
    private String medicineName;

    public MedicineRefillRequest(String prescriptionID, String medicineName) {
        this.prescriptionID = prescriptionID;
        this.medicineName = medicineName;
    }

    @Override
    public void executeRequest(Doctor doctor, Appointment appointment) {
        System.out.println("Executing Medicine Refill Request:");
        System.out.println("Doctor: " + doctor.getDoctorID());
        System.out.println("Medicine: " + medicineName);
    }
}
