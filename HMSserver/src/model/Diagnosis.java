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

    // Constructor for RMI binding
    public Diagnosis(DB db) throws RemoteException {
        this.db = db;
    }

    // Internal constructor
    public Diagnosis(int diagnosisID, int appointmentID,
                     Patient patient, Doctor doctor,
                     String clinicalNotes, String diagnosis, DB db)
            throws RemoteException {

        this.db = db;
        this.diagnosisID = diagnosisID;
        this.appointmentID = appointmentID;
        this.patient = patient;
        this.doctor = doctor;
        this.clinicalNotes = clinicalNotes;
        this.diagnosis = diagnosis;
    }

    // ✅ OPTION 1 IMPLEMENTATION
    @Override
    public boolean recordDiagnosis(
            int diagnosisID,
            int appointmentID,
            String clinicalNotes,
            String diagnosisText
    ) throws RemoteException {

        // 1️⃣ Load appointment
        Appointment appointment = db.getAppointmentByID(appointmentID);

        if (appointment == null) {
            return false;
        }

        // 2️⃣ Extract patient & doctor
        Patient patient = appointment.getPatient();
        Doctor doctor   = appointment.getDoctor();

        if (patient == null || doctor == null) {
            return false;
        }

        // 3️⃣ Create diagnosis
        Diagnosis diag = new Diagnosis(
                diagnosisID,
                appointmentID,
                patient,
                doctor,
                clinicalNotes,
                diagnosisText,
                db
        );

        // 4️⃣ Save
        db.addDiagnosis(diag);
        return true;
    }

    // Getters
    public int getDiagnosisID() { return diagnosisID; }
    public int getAppointmentID() { return appointmentID; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public String getClinicalNotes() { return clinicalNotes; }
    public String getDiagnosis() { return diagnosis; }
}
