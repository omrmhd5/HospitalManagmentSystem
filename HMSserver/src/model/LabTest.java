package model;

import java.io.Serializable;

public class LabTest implements Serializable {

    private int testID;
    private String type;
    private String result;
    private String date;
    private String status;

    public LabTest() { }

    public LabTest(int testID, String type, String date) {
        this.testID = testID;
        this.type = type;
        this.date = date;
        this.status = "Pending";
    }

    // Logic that belongs to LabTest
    public void recordTestResult() {
        this.status = "Completed";
    }

    // Getters / setters
    public int getTestID() { return testID; }
    public String getStatus() { return status; }
}
