package DesignPattern.Strategy;

import model.Doctor;
import rmi.LabTestInterface;
import java.rmi.RemoteException;

public class LabTestRequest implements DoctorRequestStrategy {

    private String labTestID;
    private String testType;
    private String patientName;
    private int patientAge;
    private String patientGender;
    private String patientDateOfBirth;
    private String doctorEmail;
    private String doctorPhone;
    private LabTestInterface labTestService;

    public LabTestRequest(String testType, String patientName, int patientAge,
                         String patientGender, String patientDateOfBirth,
                         String doctorEmail, String doctorPhone, LabTestInterface labTestService) {
        this.testType = testType;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientGender = patientGender;
        this.patientDateOfBirth = patientDateOfBirth;
        this.doctorEmail = doctorEmail;
        this.doctorPhone = doctorPhone;
        this.labTestService = labTestService;
    }

    @Override
    public void executeRequest(Doctor doctor) {
        try {
            // Execute the actual lab test request
            labTestService.submitLabTestRequest(
                doctor.getName(),
                doctorEmail,
                doctorPhone,
                testType,
                patientName,
                patientAge,
                patientGender,
                patientDateOfBirth
            );
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
