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
                       String date, String time, DB db) throws RemoteException {
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
        Patient patient = new Patient(0, patientName, "", "", 0, patientName, "", 0, "", "", "", "", db);
        Doctor doctor = new Doctor(0, doctorName, "", "", 0, "", "");
        Appointment appointment = new Appointment(0, patient, doctor, date, time, db);
        
        db.insertAppointment(appointment);
        return "Appointment booked with " + doctorName + " on " + date + " at " + time;
    }

    // Getters / setters
    public int getAppointmentID() { return appointmentID; }
    public void setAppointmentID(int appointmentID) { this.appointmentID = appointmentID; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getStatus() { return status; }
    
    // Mahmoud - Stub implementations for AppointmentInterface methods
    @Override
    public Appointment getAppointmentByID(int appointmentID) throws RemoteException {
        return null;
    }
    
    @Override
    public boolean addAppointment(Appointment appointment) throws RemoteException {
        return false;
    }
    
    @Override
    public boolean updateAppointment(Appointment appointment) throws RemoteException {
        return false;
    }
    
    //Rana
    @Override
    public boolean cancelAppointment(int appointmentID) throws RemoteException {
        // Cannot cancel if already cancelled or completed
        if (this.status.equals("Cancelled") || this.status.equals("Completed")) {
            System.out.println("Appointment is already " + this.status);
            return false;
        }

        // Example rule: cannot cancel within 2 hours of appointment
        if (!isCancellationAllowed()) {
            System.out.println("Too late to cancel the appointment.");
            return false;
        }

        this.status = "Cancelled";
        return true;
    }

    //Rana
    @Override
    public boolean rescheduleAppointment(int appointmentID, String newDate, String newTime) throws RemoteException {
        //  cannot reschedule if already canceled or completed
        if (this.status.equals("Cancelled") || this.status.equals("Completed")) {
            System.out.println("Cannot reschedule. Appointment is " + this.status);
            return false;
        }

        // Example rule: cannot reschedule last minute 
        if (!isRescheduleAllowed()) {
            System.out.println("Rescheduling is not allowed this close to the appointment.");
            return false;
        }

        // Update date/time
        this.date = newDate;
        this.time = newTime;
        this.status = "Rescheduled";
        return true;
    }

    //Rana
    @Override
    public boolean manageAppointment(int appointmentID, String operation, String newDate, String newTime) throws RemoteException {
        if (operation.equalsIgnoreCase("cancel")) {
            return cancelAppointment(appointmentID);
        }
        else if (operation.equalsIgnoreCase("reschedule")) {
            return rescheduleAppointment(appointmentID, newDate, newTime);
        }
        else {
            System.out.println("Invalid operation.");
            return false;
        }
    }



    /** Dummy rule: always allow for now */
    private boolean isRescheduleAllowed() {
        return true;
    }

    /** Dummy rule: always allow for now */
    private boolean isCancellationAllowed() {
        return true;
    }
    
    public String toReadableString() {
    return  "Appointment ID: " + appointmentID +
            "\nPatient: " + (patient != null ? patient.getName() : "Unknown") +
            "\nDoctor: " + (doctor != null ? doctor.getName() : "Unknown") +
            "\nDate: " + date +
            "\nTime: " + time +
            "\nStatus: " + status;
}

    
}
