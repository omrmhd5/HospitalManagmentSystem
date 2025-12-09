package model;

import java.io.Serializable;

public class Patient implements Serializable {

    private int patientID;
    private String contactInfo;
    private String gender;
    private int age;
    private String medicalHistory;

    // Read-only prescription view (UML)
    private Prescription readOnly;

    public Patient() { }

    public Patient(int patientID, String contactInfo, String gender, int age, String medicalHistory) {
        this.patientID = patientID;
        this.contactInfo = contactInfo;
        this.gender = gender;
        this.age = age;
        this.medicalHistory = medicalHistory;
    }

    // ---------- Domain Logic From UML ----------

    public void viewAvailableAppointment() {
        // logic handled on server, placeholder for now
        System.out.println("Viewing available appointments...");
    }

    public void manageAppointment() {
        // placeholder logic
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

    // ---------- Getters & Setters ----------

    public int getPatientID() { return patientID; }
    public void setPatientID(int patientID) { this.patientID = patientID; }

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
