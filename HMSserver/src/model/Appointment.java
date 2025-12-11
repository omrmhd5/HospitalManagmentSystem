package model;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
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
    public void setStatus(String status) { this.status = status; }
    
    // Rana
    @Override
    public String getAppointmentByID(int appointmentID) throws RemoteException {
        Appointment appointment = db.getAppointmentByID(appointmentID);
        
        if (appointment == null) {
            return "Appointment not found";
        }
        
        String result = "Appointment Details\n" +
                        "====================\n" +
                        "Appointment ID: " + appointment.getAppointmentID() + "\n" +
                        "Patient: " + appointment.getPatient().getName() + "\n" +
                        "Doctor: " + appointment.getDoctor().getName() + "\n" +
                        "Date: " + appointment.getDate() + "\n" +
                        "Time: " + appointment.getTime() + "\n" +
                        "Status: " + appointment.getStatus();
        
        return result;
    }
    
    // Rana
    @Override
    public boolean updateAppointment(int appointmentID, String patientName, String doctorName, 
                                      String date, String time, String status) throws RemoteException {
        Appointment appointment = db.getAppointmentByID(appointmentID);
        
        if (appointment == null) {
            return false;
        }
        
        Patient patient = new Patient(0, patientName, "", "", 0, patientName, "", 0, "", "", "", "", db);
        Doctor doctor = new Doctor(0, doctorName, "", "", 0, "", "");
        
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setStatus(status);
        
        db.updateAppointment(appointment);
        return true;
    }
    
    //Rana
    @Override
    public boolean cancelAppointment(int appointmentID) throws RemoteException {
        Appointment appointment = db.getAppointmentByID(appointmentID);
        
        if (appointment == null) {
            return false;
        }
        
        // Cannot cancel if already cancelled or completed
        if (appointment.getStatus().equals("Cancelled") || appointment.getStatus().equals("Completed")) {
            System.out.println("Appointment is already " + appointment.getStatus());
            return false;
        }

        // Example rule: cannot cancel within 2 hours of appointment
        if (!isCancellationAllowed()) {
            System.out.println("Too late to cancel the appointment.");
            return false;
        }

        appointment.setStatus("Cancelled");
        db.updateAppointment(appointment);
        return true;
    }

    //Rana
    @Override
    public boolean rescheduleAppointment(int appointmentID, String newDate, String newTime) throws RemoteException {
        Appointment appointment = db.getAppointmentByID(appointmentID);
        
        if (appointment == null) {
            return false;
        }
        
        //  cannot reschedule if already canceled or completed
        if (appointment.getStatus().equals("Cancelled") || appointment.getStatus().equals("Completed")) {
            System.out.println("Cannot reschedule. Appointment is " + appointment.getStatus());
            return false;
        }

        // Example rule: cannot reschedule last minute 
        if (!isRescheduleAllowed()) {
            System.out.println("Rescheduling is not allowed this close to the appointment.");
            return false;
        }

        // Update date/time
        appointment.setDate(newDate);
        appointment.setTime(newTime);
        appointment.setStatus("Rescheduled");
        db.updateAppointment(appointment);
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

    //Rana
    @Override
    public String getAppointmentsForDoctor(int doctorID) throws RemoteException {
        List<Appointment> list = db.getAppointmentsForDoctor(doctorID);
        
        if (list == null || list.isEmpty()) {
            return "No appointments found for this doctor";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("Doctor's Appointments\n");
        result.append("====================\n\n");
        
        for (Appointment a : list) {
            result.append(a.toReadableString()).append("\n");
            result.append("-----------------\n");
        }
        
        return result.toString();
    }

    //Rana
    @Override
    public String getAvailableReservations(String doctorName, String specialty, String date) throws RemoteException {
        List<AvailableReservation> list = db.getAvailableReservations(doctorName, specialty, date);
        
        if (list == null || list.isEmpty()) {
            return "No available reservations found";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("Available Reservations\n");
        result.append("====================\n\n");
        
        for (AvailableReservation a : list) {
            result.append(a.toReadableString()).append("\n");
            result.append("----------------------\n");
        }
        
        return result.toString();
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

    // Mahmoud
    @Override
    public List<String> getAllDoctorNames() throws RemoteException {
        return db.getAllDoctorNames();
    }
    
}
