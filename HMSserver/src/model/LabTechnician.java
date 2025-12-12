package model;

import java.io.Serializable;
import java.rmi.RemoteException;

public class LabTechnician extends User implements Serializable {

    private int technicianID;
    private String labDepartment;

    public LabTechnician() throws RemoteException {}

    public String recordTestResult(int testID, String result) {

        if (result == null || result.isBlank()) {
            return "Test result cannot be empty.";
        }

        return "Test result recorded successfully for Test ID: " + testID;
    }
}
