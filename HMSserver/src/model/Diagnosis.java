package model;

import java.io.Serializable;

public class Diagnosis implements Serializable {

    private int diagnosisID;
    private int appointmentID;
    private Patient patient;
    private Doctor doctor;
    private String clinicalNotes;
    private String diagnosis;

    public Diagnosis() {}

    public Diagnosis(int diagnosisID, int appointmentID,
                     Patient patient, Doctor doctor,
                     String clinicalNotes, String diagnosis) {

        this.diagnosisID = diagnosisID;
        this.appointmentID = appointmentID;
        this.patient = patient;
        this.doctor = doctor;
        this.clinicalNotes = clinicalNotes;
        this.diagnosis = diagnosis;
    }

    public int getDiagnosisID() { return diagnosisID; }
    public int getAppointmentID() { return appointmentID; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public String getClinicalNotes() { return clinicalNotes; }
    public String getDiagnosis() { return diagnosis; }
}
