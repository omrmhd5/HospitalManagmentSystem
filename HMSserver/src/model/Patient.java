package model;

import java.io.Serializable;
import java.rmi.RemoteException;
import rmi.PatientInterface;

public class Patient extends User implements PatientInterface, Serializable {

    private int patientID;
    private String contactInfo;
    private String gender;
    private int age;
    private String medicalHistory;
    private String dateOfBirth;
    private String address;
    private String phoneNumber;

    // Read-only prescription view (UML)
    private Prescription readOnly;

    public Patient() throws RemoteException { }

    public Patient(int userID, String name, String email, String password,
                   int patientID, String contactInfo, String gender, int age, 
                   String medicalHistory, String dateOfBirth, String address, String phoneNumber) 
                   throws RemoteException {
        super(userID, name, email, password, "Patient");
        this.patientID = patientID;
        this.contactInfo = contactInfo;
        this.gender = gender;
        this.age = age;
        this.medicalHistory = medicalHistory;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
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

    // RMI Interface Methods
    // Salma
    
    @Override
    public String getProfileName() throws RemoteException {
        return this.name;
    }
    
    @Override
    public String getProfileDateOfBirth() throws RemoteException {
        return this.dateOfBirth;
    }
    
    @Override
    public String getProfileGender() throws RemoteException {
        return this.gender;
    }
    
    @Override
    public String getProfileAddress() throws RemoteException {
        return this.address;
    }
    
    @Override
    public String getProfilePhoneNumber() throws RemoteException {
        return this.phoneNumber;
    }
    
    @Override
    public void updateProfile(String name, String dateOfBirth, String gender, 
                             String address, String phoneNumber) throws RemoteException {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        System.out.println("Profile updated for: " + this.name);
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
    
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
