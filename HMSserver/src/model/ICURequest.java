package model;

import java.io.Serializable;

public class ICURequest implements Serializable {

    private int requestID;
    private Doctor doctor;
    private Patient patient;
    private String date;
    private String time;
    private String urgency;   // normal / high / emergency
    private String diagnosis;
    private String expectedDuration;
    private String status;    // pending / approved / rejected

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

    public String toReadableString() {
        return "ICU Request ID: " + requestID +
               "\nPatient: " + patient.getName() +
               "\nDoctor: " + doctor.getName() +
               "\nDate: " + date +
               "\nTime: " + time +
               "\nUrgency: " + urgency +
               "\nDiagnosis: " + diagnosis +
               "\nDuration: " + expectedDuration +
               "\nStatus: " + status;
    }
}
