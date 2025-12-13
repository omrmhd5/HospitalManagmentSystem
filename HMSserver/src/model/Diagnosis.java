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

   
    public Diagnosis(DB db) throws RemoteException {
        this.db = db;
    }

  
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

  
    @Override
    public boolean recordDiagnosis(
            int diagnosisID,
            int appointmentID,
            String clinicalNotes,
            String diagnosisText
    ) throws RemoteException {

       
        Appointment appointment = db.getAppointmentByID(appointmentID);

        if (appointment == null) {
            return false;
        }

        
        Patient patient = appointment.getPatient();
        Doctor doctor   = appointment.getDoctor();

        if (patient == null || doctor == null) {
            return false;
        }

      
        Diagnosis diag = new Diagnosis(
                diagnosisID,
                appointmentID,
                patient,
                doctor,
                clinicalNotes,
                diagnosisText,
                db
        );

       
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
