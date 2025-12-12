package model;

import DesignPattern.Strategy.DoctorRequestStrategy;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import ObserverDesignPattern.AppointmentObserver;

public class Doctor extends User implements Serializable, AppointmentObserver  {

    private int doctorID;
    private String specialization;
    private String availabilitySchedule;

    private DoctorRequestStrategy requestStrategy;  // FIXED

    private ArrayList<Appointment> appointments = new ArrayList<>();

    public Doctor() throws RemoteException{}

    public Doctor(int userID, String name, String email, String password,
                  int doctorID, String specialization, String availabilitySchedule) throws RemoteException {
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

    public void executeRequest() {
        if (requestStrategy != null) {
            requestStrategy.executeRequest(this);
        } else {
            System.out.println("No request strategy assigned.");
        }
    }

//    public void updateAppointment(Appointment appointment, String message) {
//        appointment.updateStatus(message);
//    }
        @Override
    public void update(Appointment appointment, String message) {
        System.out.println(
            "Doctor " + getName() +
            " notified about Appointment ID " +
            appointment.getAppointmentID() +
            ": " + message
        );
    }

    public int getDoctorID() { return doctorID; }
    public String getSpecialization() { return specialization; }
    public String getAvailabilitySchedule() { return availabilitySchedule; }
}
