package model;

import java.io.Serializable;

public class LabTechnician extends User implements Serializable {

    private int technicianID;
    private String labDepartment;

    public LabTechnician() {}

    public LabTechnician(int userID, String name, String email, String password,
                         int technicianID, String labDepartment) {
        super(userID, name, email, password, "LabTechnician");
        this.technicianID = technicianID;
        this.labDepartment = labDepartment;
    }

    // ---------- UML METHOD ----------
    public void recordTestResult(LabTest test) {
        test.recordTestResult();
    }

    public int getTechnicianID() { return technicianID; }
}
