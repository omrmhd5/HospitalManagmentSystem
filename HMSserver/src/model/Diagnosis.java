package model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmi.DiagnosisInterface;
import server.DB;

public class Diagnosis extends UnicastRemoteObject implements DiagnosisInterface {

    private int diagnosisID;
    private int appointmentID;
    private Patient patient;
    private Doctor doctor;
    private String clinicalNotes;
    private String diagnosis;
    private final DB db;

    // Tasneem
    public Diagnosis(DB db) throws RemoteException {
        this.db = db;
    }

    // Tasneem
    public Diagnosis(int diagnosisID, int appointmentID,
                     Patient patient, Doctor doctor,
                     String clinicalNotes, String diagnosis, DB db) throws RemoteException {
        this.db = db;
        this.diagnosisID = diagnosisID;
        this.appointmentID = appointmentID;
        this.patient = patient;
        this.doctor = doctor;
        this.clinicalNotes = clinicalNotes;
        this.diagnosis = diagnosis;
    }

    // Tasneem
    @Override
    public boolean recordDiagnosis(int diagnosisID, int appointmentID, String patientName, 
                                    String doctorName, String clinicalNotes, String diagnosis) throws RemoteException {
        Patient patient = new Patient(0, patientName, "", "", 0, patientName, "", 0, "", "", "", "", db);
        Doctor doctor = new Doctor(0, doctorName, "", "", 0, "", "");
        Diagnosis diag = new Diagnosis(diagnosisID, appointmentID, patient, doctor, clinicalNotes, diagnosis, db);
        db.addDiagnosis(diag);
        return true;
    }

    public int getDiagnosisID() { return diagnosisID; }
    public int getAppointmentID() { return appointmentID; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public String getClinicalNotes() { return clinicalNotes; }
    public String getDiagnosis() { return diagnosis; }
}
