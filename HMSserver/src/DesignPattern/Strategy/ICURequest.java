package DesignPattern.Strategy;

import model.Doctor;
import model.Patient;
import rmi.ICUInterface;
import java.io.Serializable;
import java.rmi.RemoteException;

public class ICURequest implements DoctorRequestStrategy, Serializable {

    private int requestID;
    private Doctor doctor;
    private Patient patient;
    private String date;
    private String time;
    private String urgency;   // normal / high / emergency
    private String diagnosis;
    private String expectedDuration;
    private String status;    // pending / approved / rejected
    private ICUInterface icuService;
    private String patientName; // For strategy pattern usage

    public ICURequest() {}

    public ICURequest(int requestID, Doctor doctor, Patient patient, 
                      String date, String time, String urgency, 
                      String diagnosis, String expectedDuration) {
        this.requestID = requestID;
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.time = time;
        this.urgency = urgency;
        this.diagnosis = diagnosis;
        this.expectedDuration = expectedDuration;
        this.status = "Pending";
        if (patient != null) {
            this.patientName = patient.getName();
        }
    }
    
    // Constructor for strategy pattern usage
    public ICURequest(String patientName, String date, String time, String urgency,
                     String diagnosis, String expectedDuration, 
                     ICUInterface icuService) {
        this.patientName = patientName;
        this.date = date;
        this.time = time;
        this.urgency = urgency;
        this.diagnosis = diagnosis;
        this.expectedDuration = expectedDuration;
        this.icuService = icuService;
        this.status = "Pending";
    }

    // Getters
    public int getRequestID() { return requestID; }
    public Doctor getDoctor() { return doctor; }
    public Patient getPatient() { return patient; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getUrgency() { return urgency; }
    public String getDiagnosis() { return diagnosis; }
    public String getExpectedDuration() { return expectedDuration; }
    public String getStatus() { return status; }

    // Status update
    public void approve() { this.status = "Approved"; }
    public void reject() { this.status = "Rejected"; }
    
    // Setter for requestID
    public void setRequestID(int requestID) { this.requestID = requestID; }

    @Override
    public void executeRequest(Doctor doctor) {
        try {
            // Generate request ID
            int requestID = (int) (Math.random() * 100000);
            
            // Execute the actual ICU request
            icuService.createICURequest(
                requestID,
                doctor.getName(),
                patientName,
                date,
                time,
                urgency,
                diagnosis,
                expectedDuration
            );
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String toReadableString() {
        return "ICU Request ID: " + requestID +
               "\nPatient: " + (patient != null ? patient.getName() : patientName) +
               "\nDoctor: " + (doctor != null ? doctor.getName() : "N/A") +
               "\nDate: " + date +
               "\nTime: " + time +
               "\nUrgency: " + urgency +
               "\nDiagnosis: " + diagnosis +
               "\nDuration: " + expectedDuration +
               "\nStatus: " + status;
    }
}
