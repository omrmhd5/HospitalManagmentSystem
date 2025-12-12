package DesignPattern.Strategy;

import model.Appointment;
import model.Doctor;

public class LabTestRequest implements DoctorRequestStrategy {

    private String labTestID;
    private String testType;

    public LabTestRequest(String labTestID, String testType) {
        this.labTestID = labTestID;
        this.testType = testType;
    }

    @Override
    public void executeRequest(Doctor doctor, Appointment appointment) {
        System.out.println("Executing Lab Test Request:");
        System.out.println("Doctor: " + doctor.getDoctorID());
        System.out.println("Appointment: " + appointment.getAppointmentID());
        System.out.println("Test Type: " + testType);
    }
}
