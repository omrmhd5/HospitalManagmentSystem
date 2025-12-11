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

    public int getTechnicianID() { return technicianID; }
    public void setTechnicianID(int technicianID) { this.technicianID = technicianID; }
    
    public String getLabDepartment() { return labDepartment; }
    public void setLabDepartment(String labDepartment) { this.labDepartment = labDepartment; }
}
