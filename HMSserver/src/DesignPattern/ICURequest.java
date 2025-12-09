package DesignPattern;

import model.Appointment;
import model.Doctor;

public class ICURequest implements DoctorRequestStrategy {

    private String icuID;
    private String priorityLevel;
    private String requestStatus;

    public ICURequest(String icuID, String priorityLevel, String requestStatus) {
        this.icuID = icuID;
        this.priorityLevel = priorityLevel;
        this.requestStatus = requestStatus;
    }

    @Override
    public void executeRequest(Doctor doctor, Appointment appointment) {
        System.out.println("Executing ICU Room Request:");
        System.out.println("Doctor: " + doctor.getDoctorID());
        System.out.println("ICU ID: " + icuID + ", Priority: " + priorityLevel);
    }
}
