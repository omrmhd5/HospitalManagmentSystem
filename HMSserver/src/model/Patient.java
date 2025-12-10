package model;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmi.PatientRecordInterface;
import server.DB;

public class Patient extends UnicastRemoteObject implements PatientRecordInterface {
    private int patientID;
    private String contactInfo;
    private String gender;
    private int age;
    private String medicalHistory;
    private final DB db;

    // Read-only prescription view (UML)
    private Prescription readOnly;

    // Mahmoud
    public Patient(DB db) throws RemoteException {
        this.db = db;
    }

    // Mahmoud
    public Patient(int patientID, String contactInfo, String gender, int age, String medicalHistory, DB db) throws RemoteException {
        this.db = db;
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
