package model;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmi.PrescriptionInterface;
import server.DB;

public class Prescription extends UnicastRemoteObject implements PrescriptionInterface {
    private int prescriptionID;
    private Patient patient;
    private Doctor doctor;
    private String medicine;
    private String dosage;
    private String diagnosis;
    private final DB db;
    
    // Mahmoud
    public Prescription(DB db) throws RemoteException{
        this.db = db;
    }
    
    // Mahmoud
    public Prescription(int prescriptionID, Patient patient, Doctor doctor, 
                        String medicine, String dosage, String diagnosis, DB db) throws RemoteException{
        this.db = db;
        this.prescriptionID = prescriptionID;
        this.patient = patient;
        this.doctor = doctor;
        this.medicine = medicine;
        this.dosage = dosage;
        this.diagnosis = diagnosis;
    }

    // Logic that belongs to Prescription
    public void addMedicine(String m, String dosage) {
        this.medicine = m;
        this.dosage = dosage;
    }
    
    // Mahmoud
    @Override
    public String recordPrescription(String patientName, String doctorName, String medicine, String dosage, String diagnosis) throws RemoteException {
        // Mahmoud - Get actual patient from database
        Patient patient = db.getPatientByName(patientName);
        
        // Mahmoud - Get actual doctor from database
        Doctor doctor = db.getDoctorByName(doctorName);
        
        // Mahmoud - Create Prescription object with actual patient and doctor data
        Prescription prescription = new Prescription(0, patient, doctor, medicine, dosage, diagnosis, db);
        
        // Mahmoud
        db.insertPrescription(prescription);
        return "Prescription recorded for " + patientName + " by Dr. " + doctorName;
    }

    public int getPrescriptionID() { return prescriptionID; }
    public void setPrescriptionID(int prescriptionID) { this.prescriptionID = prescriptionID; }
    
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    
    public String getMedicine() { return medicine; }
    public void setMedicine(String medicine) { this.medicine = medicine; }
    
    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
}
