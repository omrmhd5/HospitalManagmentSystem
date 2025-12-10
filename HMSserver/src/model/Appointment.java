package model;

import java.io.Serializable;

public class Appointment implements Serializable {

    private int appointmentID;
    private Patient patient;
    private Doctor doctor;
    private String date;
    private String time;
    private String status;
    

    public Appointment() { }

    public Appointment(int appointmentID, Patient patient, Doctor doctor,
                       String date, String time) {
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
    
    //Rana
    public boolean rescheduleAppointment(String newDate, String newTime) {

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

    public boolean cancelAppointment() {

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


    public boolean manageAppointment(String operation, String newDate, String newTime) {
        if (operation.equalsIgnoreCase("cancel")) {
            
            return cancelAppointment();
        }
        else if (operation.equalsIgnoreCase("reschedule")) {
            return rescheduleAppointment(newDate, newTime);
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
    
}
