package model;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmi.AppointmentInterface;
import server.DB;

public class Appointment extends UnicastRemoteObject implements AppointmentInterface {
    private int appointmentID;
    private Patient patient;
    private Doctor doctor;
    private String date;
    private String time;
    private String status;
    private final DB db;
    
    // Mahmoud
    public Appointment(DB db) throws RemoteException{
        this.db = db;
    }
    
    // Mahmoud
    public Appointment(int appointmentID, Patient patient, Doctor doctor,
                       String date, String time, DB db) throws RemoteException{
        this.db = db;
        this.appointmentID = appointmentID;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.status = "Pending";
    }
    
    // Logic that belongs to Appointment
    public void confirmReservation() {
        this.status = "Confirmed";
    }


    public void updateStatus(String status) {
        this.status = status;
    }
    
    // Mahmoud
    @Override
    public String bookAppointment(String patientName, String doctorName, String date, String time) throws RemoteException {
        if (doctorName == null || doctorName.isEmpty() || date == null || date.isEmpty() || time == null || time.isEmpty()) {
            return "Missing appointment details";
        }
        Patient patient = new Patient(0, patientName, "", 0, "");
        Doctor doctor = new Doctor(0, doctorName, "", "", 0, "", "");
        Appointment appointment = new Appointment(0, patient, doctor, date, time, db);
        
        db.insertAppointment(appointment);
        return "Appointment booked with " + doctorName + " on " + date + " at " + time;
    }
    
    public int getAppointmentID() {
        return appointmentID;
    }
}