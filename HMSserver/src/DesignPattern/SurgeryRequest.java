package DesignPattern;

import model.Appointment;
import model.Doctor;

public class SurgeryRequest implements DoctorRequestStrategy {

    private String surgeryID;
    private String surgeryType;
    private java.util.Date scheduledDate;

    public SurgeryRequest(String surgeryID, String surgeryType, java.util.Date scheduledDate) {
        this.surgeryID = surgeryID;
        this.surgeryType = surgeryType;
        this.scheduledDate = scheduledDate;
    }

    @Override
    public void executeRequest(Doctor doctor, Appointment appointment) {
        System.out.println("Executing Surgery Request:");
        System.out.println("Doctor: " + doctor.getDoctorID());
        System.out.println("Surgery Type: " + surgeryType);
    }
}
