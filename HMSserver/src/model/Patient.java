package model;
import java.rmi.RemoteException;
import rmi.PatientInterface;
import server.DB;

import java.util.ArrayList;
import java.util.List;
import DesignPattern.Observer.AppointmentObserver;

public class Patient extends User implements PatientInterface, AppointmentObserver {

    private int patientID;
    private String name;
    private String contactInfo;
    private String gender;
    private int age;
    private String medicalHistory;
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

    // Mahmoud
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

//    public void updateAppointment(Appointment appointment, String message) {
//        appointment.updateStatus(message);
//    }
    //Tasnim
    @Override
    public void update(String appointmentInfo, String message) {
        System.out.println("Patient " + getName() + " notified: " + message);
        System.out.println("Appointment: " + appointmentInfo);
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
                        "Date of Birth: " + (patient.getDateOfBirth() != null ? patient.getDateOfBirth() : "") + "\n" +
                        "Medical History: " + patient.getMedicalHistory() + "\n";
        
        return record;
    }
    
    
    // Salma - Get patient profile by email
    @Override
    public String getProfileNameByEmail(String email) throws RemoteException {
        Patient p = db.getPatientByEmail(email);
        if (p == null) {
            return "";
        }
        return p.getName() != null ? p.getName() : "";
    }
    
    @Override
    public String getProfileDateOfBirthByEmail(String email) throws RemoteException {
        Patient p = db.getPatientByEmail(email);
        if (p == null) {
            return "";
        }
        return p.getDateOfBirth() != null ? p.getDateOfBirth() : "";
    }
    
    @Override
    public String getProfileGenderByEmail(String email) throws RemoteException {
        Patient p = db.getPatientByEmail(email);
        if (p == null) {
            return "";
        }
        return p.getGender() != null ? p.getGender() : "";
    }
    
    @Override
    public String getProfileAddressByEmail(String email) throws RemoteException {
        Patient p = db.getPatientByEmail(email);
        if (p == null) {
            return "";
        }
        return p.getAddress() != null ? p.getAddress() : "";
    }
    
    @Override
    public String getProfilePhoneNumberByEmail(String email) throws RemoteException {
        Patient p = db.getPatientByEmail(email);
        if (p == null) {
            return "";
        }
        return p.getPhoneNumber() != null ? p.getPhoneNumber() : "";
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
    
    // Salma - Update patient profile by email and save to database
    @Override
    public boolean updateProfileByEmail(String email, String name, String dateOfBirth, 
                                      String gender, String address, String phoneNumber) throws RemoteException {
        boolean success = db.updatePatientByEmail(email, name, dateOfBirth, gender, address, phoneNumber);
        if (success) {
            System.out.println("Profile updated in database for: " + email);
        } else {
            System.out.println("Failed to update profile for: " + email);
        }
        return success;
    }

   //ibrahim
    public void addRecord(String details) {
        records.add(details);
        System.out.println("Record added for patient " + this.patientID + ": " + details);
    }

    public List<String> getRecords() {
        return records;
    }

    
    // Mahmoud
    @Override
    public boolean addPatient(int patientID, String name, String contactInfo, String gender, 
                              int age, String medicalHistory) throws RemoteException {
        Patient patient = new Patient(0, name, "", "", patientID, contactInfo, gender, age, 
                                      medicalHistory, "", "", "", db);
        db.insertPatient(patient);
        return true;
    }
    
    // Mahmoud
    @Override
    public String getPatientByID(int patientID) throws RemoteException {
        Patient patient = db.getPatientByID(patientID);
        
        if (patient == null) {
            return "Patient not found";
        }
        
        String result = "Patient Details\n" +
                        "====================\n" +
                        "Patient ID: " + patient.getPatientID() + "\n" +
                        "Name: " + patient.getName() + "\n" +
                        "Contact: " + patient.getContactInfo() + "\n" +
                        "Gender: " + patient.getGender() + "\n" +
                        "Age: " + patient.getAge() + "\n" +
                        "Medical History: " + patient.getMedicalHistory();
        
        return result;
    }
    
    // Ibrahim
    @Override
    public boolean addPatientRecord(int patientID, String record) throws RemoteException {
        return db.addPatientRecord(patientID, record);
    }
    
    // Ibrahim
    @Override
    public String getAllRecords(int patientID) throws RemoteException {
        List<String> records = db.getRecordsForPatient(patientID);
        return String.join("\n", records);
    }
    

    // ---------- Getters & Setters ----------
    public int getPatientID() { return patientID; }
    public void setPatientID(int patientID) { this.patientID = patientID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
    
    // Mahmoud
    @Override
    public List<String> getAllPatientNames() throws RemoteException {
        return db.getAllPatientNames();
    }
}
