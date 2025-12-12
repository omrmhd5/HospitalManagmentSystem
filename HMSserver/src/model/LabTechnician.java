package model;

import java.rmi.RemoteException;
import rmi.LabTechnicianInterface;
import server.DB;

public class LabTechnician extends User implements LabTechnicianInterface {

    private int technicianID;
    private String labDepartment;
    private final DB db;

    // Ibrahim
    public LabTechnician(DB db) throws RemoteException {
        this.db = db;
    }

    // Ibrahim
    public LabTechnician(int userID, String name, String email, String password,
                         int technicianID, String labDepartment, DB db) throws RemoteException {
        super(userID, name, email, password, "LabTechnician");
        this.technicianID = technicianID;
        this.labDepartment = labDepartment;
        this.db = db;
    }

    // Ibrahim
    @Override
    public String recordLabTestResult(int testID, String result) throws RemoteException {
        return db.recordLabTestResult(testID, result);
    }
    
    // Get pending lab tests
    @Override
    public java.util.List<String> getPendingLabTests() throws RemoteException {
        java.util.List<org.bson.Document> pendingTests = db.getPendingLabTests();
        java.util.List<String> testList = new java.util.ArrayList<>();
        for (org.bson.Document doc : pendingTests) {
            Integer testID = doc.getInteger("testID");
            String type = doc.getString("type");
            String patientName = doc.getString("patientName");
            if (testID != null) {
                String display = testID + " - " + 
                    (type != null ? type : "N/A") + " - " + 
                    (patientName != null ? patientName : "N/A");
                testList.add(display);
            }
        }
        return testList;
    }
    
    // Get lab test details
    @Override
    public String getLabTestDetails(int testID) throws RemoteException {
        org.bson.Document doc = db.getLabTestByID(testID);
        if (doc == null) {
            return "Lab test not found";
        }
        
        StringBuilder details = new StringBuilder();
        details.append("Lab Test Details\n");
        details.append("====================\n");
        details.append("Test ID: ").append(doc.getInteger("testID")).append("\n");
        details.append("Type: ").append(doc.getString("type")).append("\n");
        details.append("Patient: ").append(doc.getString("patientName")).append("\n");
        details.append("Patient Age: ").append(doc.getInteger("patientAge")).append("\n");
        details.append("Patient Gender: ").append(doc.getString("patientGender")).append("\n");
        details.append("Date: ").append(doc.getString("date")).append("\n");
        details.append("Status: ").append(doc.getString("status")).append("\n");
        details.append("Doctor: ").append(doc.getString("doctorName")).append("\n");
        String result = doc.getString("result");
        if (result != null && !result.isEmpty()) {
            details.append("Result: ").append(result).append("\n");
        }
        
        return details.toString();
    }

    public int getTechnicianID() { return technicianID; }
    public void setTechnicianID(int technicianID) { this.technicianID = technicianID; }
    
    public String getLabDepartment() { return labDepartment; }
    public void setLabDepartment(String labDepartment) { this.labDepartment = labDepartment; }
}
