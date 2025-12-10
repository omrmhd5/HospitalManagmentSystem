package model;

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

    // Patient records //ibrahim
    private List<String> records = new ArrayList<>();

    // Read-only prescription view (UML)
    private Prescription readOnly;

    public Patient() { }

    public Patient(int patientID, String name, String contactInfo, String gender, int age, String medicalHistory) {
        this.patientID = patientID;
        this.name = name;
        this.contactInfo = contactInfo;
        this.gender = gender;
        this.age = age;
        this.medicalHistory = medicalHistory;
    }

    // ---------- Domain Logic From UML ----------

    public void viewAvailableAppointment() {
        System.out.println("Viewing available appointments...");
    }

    public void manageAppointment() {
        System.out.println("Managing appointment...");
    }

    public Appointment bookAppointment(Appointment a) {
        a.confirmReservation();
        return a;
    }

    public Prescription viewPrescription() {
        return readOnly;
    }

    public void updateAppointment(Appointment appointment, String message) {
        appointment.updateStatus(message);
    }

   //ibrahim
    public void addRecord(String details) {
        records.add(details);
        System.out.println("Record added for patient " + this.patientID + ": " + details);
    }

    public List<String> getRecords() {
        return records;
    }

    // ---------- Getters & Setters ----------

    public int getPatientID() { return patientID; }
    public void setPatientID(int patientID) { this.patientID = patientID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }

    public Prescription getReadOnlyPrescription() { return readOnly; }
    public void setReadOnlyPrescription(Prescription readOnly) { this.readOnly = readOnly; }
}
