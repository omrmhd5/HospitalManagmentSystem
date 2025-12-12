package model;

import DesignPattern.IReadOnlyPrescription;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Patient implements Serializable {

    private int patientID;
    private String name;
    private String contactInfo;
    private String gender;
    private int age;
    private String medicalHistory;

    // Patient records
    private List<String> records = new ArrayList<>();

    //  Read-only interface ONLY
    private IReadOnlyPrescription readOnlyPrescription;

    public Patient() { }

    public Patient(int patientID, String name,
                   String contactInfo, String gender,
                   int age, String medicalHistory) {
        this.patientID = patientID;
        this.name = name;
        this.contactInfo = contactInfo;
        this.gender = gender;
        this.age = age;
        this.medicalHistory = medicalHistory;
    }

    //  UML METHODS 
    public void viewAvailableAppointment() {
        System.out.println("Viewing available appointments...");
    }

    public void manageAppointment() {
        System.out.println("Managing appointment...");
    }

    public void addRecord(String details) {
        records.add(details);
        System.out.println("Record added for patient " + patientID);
    }

    // PATIENT CAN ONLY VIEW
    public IReadOnlyPrescription viewPrescription() {
        return readOnlyPrescription;
    }

    // Doctor injects read-only view
    public void setReadOnlyPrescription(IReadOnlyPrescription prescription) {
        this.readOnlyPrescription = prescription;
    }

    // ===== GETTERS =====
    public int getPatientID() { return patientID; }
    public String getName() { return name; }
    public List<String> getRecords() { return records; }
}
