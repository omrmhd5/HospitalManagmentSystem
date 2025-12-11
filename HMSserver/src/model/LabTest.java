package model;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import rmi.LabTestInterface;

public class LabTest extends User implements LabTestInterface {

    private int testID;
    private String type;
    private String result;
    private String date;
    private String status;
    
    // Requester (Doctor) Information
    private String doctorName;
    private String doctorEmail;
    private String doctorPhone;
    
    // Patient Information
    private String patientName;
    private int patientAge;
    private String patientGender;
    private String patientDateOfBirth;
    
    private static int testCounter = 1000; // Static counter for generating test IDs

    // Salma
    public LabTest() throws RemoteException { 
        super();
    }

    // Salma
    public LabTest(int testID, String type, String date) throws RemoteException {
        super();
        this.testID = testID;
        this.type = type;
        this.date = date;
        this.status = "Pending";
        this.result = "";
    }

    // ---------- RMI Interface Methods ----------
    // Salma
    
    @Override
    public boolean submitLabTestRequest(String doctorName, String doctorEmail, String doctorPhone,
                                        String testType, String patientName, int patientAge,
                                        String patientGender, String patientDateOfBirth) throws RemoteException {
        try {
            // Set requester information
            this.doctorName = doctorName;
            this.doctorEmail = doctorEmail;
            this.doctorPhone = doctorPhone;
            
            // Set test information
            this.type = testType;
            this.testID = ++testCounter;
            this.date = getCurrentDateTime();
            this.status = "Pending";
            
            // Set patient information
            this.patientName = patientName;
            this.patientAge = patientAge;
            this.patientGender = patientGender;
            this.patientDateOfBirth = patientDateOfBirth;
            
            // Log the request
            System.out.println("=== Lab Test Request Submitted ===");
            System.out.println("Test ID: " + this.testID);
            System.out.println("Doctor: " + this.doctorName);
            System.out.println("Test Type: " + this.type);
            System.out.println("Patient: " + this.patientName);
            System.out.println("Date: " + this.date);
            System.out.println("================================");
            
            return true;
            
        } catch (Exception e) {
            System.err.println("Error submitting lab test request: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public String[] getAvailableTestTypes() throws RemoteException {
        return new String[]{
            "Blood Test (CBC)",
            "Urine Test",
            "X-Ray",
            "CT Scan",
            "MRI Scan",
            "Ultrasound",
            "ECG",
            "Blood Sugar Test",
            "Liver Function Test",
            "Kidney Function Test"
        };
    }
    
    @Override
    public String getTestStatus(int requestID) throws RemoteException {
        if (requestID == this.testID) {
            return this.status;
        }
        return "Not Found";
    }

    // ---------- Domain Logic ----------
    
    // Salma
    public void recordTestResult(String result) {
        this.result = result;
        this.status = "Completed";
        System.out.println("Test result recorded: " + result);
    }
    
    // Salma
    private String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    // ---------- Getters & Setters ----------
    
    public int getTestID() { return testID; }
    public void setTestID(int testID) { this.testID = testID; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    
    public String getDoctorEmail() { return doctorEmail; }
    public void setDoctorEmail(String doctorEmail) { this.doctorEmail = doctorEmail; }
    
    public String getDoctorPhone() { return doctorPhone; }
    public void setDoctorPhone(String doctorPhone) { this.doctorPhone = doctorPhone; }
    
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    
    public int getPatientAge() { return patientAge; }
    public void setPatientAge(int patientAge) { this.patientAge = patientAge; }
    
    public String getPatientGender() { return patientGender; }
    public void setPatientGender(String patientGender) { this.patientGender = patientGender; }
    
    public String getPatientDateOfBirth() { return patientDateOfBirth; }
    public void setPatientDateOfBirth(String patientDateOfBirth) { this.patientDateOfBirth = patientDateOfBirth; }
}
