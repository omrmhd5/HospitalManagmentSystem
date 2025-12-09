package model;

import java.io.Serializable;

public class Prescription implements Serializable {

    private int prescriptionID;
    private Patient patient;
    private Doctor doctor;
    private String medicine;
    private String dosage;
    private String diagnosis;

    public Prescription() { }

    public Prescription(int prescriptionID, Patient patient, Doctor doctor) {
        this.prescriptionID = prescriptionID;
        this.patient = patient;
        this.doctor = doctor;
    }

    // Logic that belongs to Prescription
    public void addMedicine(String m, String dosage) {
        this.medicine = m;
        this.dosage = dosage;
    }

    public String getMedicine() { return medicine; }
    public String getDosage() { return dosage; }
}
