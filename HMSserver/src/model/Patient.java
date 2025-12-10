package model;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmi.PatientRecordInterface;
import rmi.PatientInterface;
import rmi.AppointmentInterface;
import rmi.PrescriptionInterface;
import server.DB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Patient extends User implements PatientRecordInterface, PatientInterface, AppointmentInterface, PrescriptionInterface {

    private int patientID;
    private String name;
    private String contactInfo;
    private String gender;
    private int age;
    private String medicalHistory;
    private String name;
    private String dateOfBirth;
    private String address;
    private String phoneNumber;
    private final DB db;

    // Patient records //ibrahim
    private List<String> records = new ArrayList<>();

    // Read-only prescription view (UML)
    private Prescription readOnly;

    // Mahmoud
    public Patient(DB db) throws RemoteException {
        this.db = db;
    }

    public Patient(int userID, String name, String email, String password,
                   int patientID, String contactInfo, String gender, int age, 
                   String medicalHistory, String dateOfBirth, String address, String phoneNumber, DB db) 
                   throws RemoteException {
        super(userID, name, email, password, "Patient");
        this.db = db;
        this.patientID = patientID;
        this.name = name;
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
    
    // Mahmoud
    @Override
    public String viewPatientRecord(String patientName) throws RemoteException {
        if (patientName == null || patientName.isEmpty()) {
            return "Patient name is required";
        }
        
        Patient patient = db.getPatientByName(patientName);
        
        if (patient == null) {
            return "Patient not found: " + patientName;
        }
        
        String record = "Patient Record\n" +
                        "====================\n" +
                        "Name: " + patientName + "\n" +
                        "Contact: " + patient.getContactInfo() + "\n" +
                        "Gender: " + patient.getGender() + "\n" +
                        "Age: " + patient.getAge() + "\n" +
                        "Medical History: " + patient.getMedicalHistory() + "\n";
        
        return record;
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
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    

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
