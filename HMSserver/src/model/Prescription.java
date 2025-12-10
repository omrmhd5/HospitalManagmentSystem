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
        if (patientName == null || patientName.isEmpty() || doctorName == null || doctorName.isEmpty()) {
            return "Missing prescription details";
        }
        Patient patient = new Patient(0, patientName, "", 0, "");
        Doctor doctor = new Doctor(0, doctorName, "", "", 0, "", "");
        Prescription prescription = new Prescription(0, patient, doctor, medicine, dosage, diagnosis, db);
        
        db.insertPrescription(prescription);
        return "Prescription recorded for " + patientName + " by Dr. " + doctorName;
    }

    public String getMedicine() { return medicine; }
    public String getDosage() { return dosage; }
    public int getPrescriptionID() { return prescriptionID; }
}
