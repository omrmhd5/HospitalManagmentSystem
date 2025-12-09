package model;

import DesignPattern.DoctorRequestStrategy;
import java.io.Serializable;
import java.util.ArrayList;

public class Doctor extends User implements Serializable {

    private int doctorID;
    private String specialization;
    private String availabilitySchedule;

    private DoctorRequestStrategy requestStrategy;  // FIXED

    private ArrayList<Appointment> appointments = new ArrayList<>();

    public Doctor() {}

    public Doctor(int userID, String name, String email, String password,
                  int doctorID, String specialization, String availabilitySchedule) {
        super(userID, name, email, password, "Doctor");
        this.doctorID = doctorID;
        this.specialization = specialization;
        this.availabilitySchedule = availabilitySchedule;
    }

    public ArrayList<Appointment> viewAppointment() {
        return appointments;
    }

    public void setRequestStrategy(DoctorRequestStrategy strategy) {
        this.requestStrategy = strategy;
    }

    public void executeRequest(Appointment appointment) {
        if (requestStrategy != null) {
            requestStrategy.executeRequest(this, appointment);
        } else {
            System.out.println("No request strategy assigned.");
        }
    }

    public void updateAppointment(Appointment appointment, String message) {
        appointment.updateStatus(message);
    }

    public int getDoctorID() { return doctorID; }
}
