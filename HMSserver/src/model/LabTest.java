package model;

import java.io.Serializable;
//ibrahim
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
        this.result = "";   // initially empty
    }

    // ---------- UML Logic ----------
    public void recordTestResult(String resultValue) {
        this.result = resultValue;
        this.status = "Completed";
    }

    // ---------- Getters & Setters ----------
    public int getTestID() { 
        return testID; 
    }

    public void setTestID(int testID) { 
        this.testID = testID; 
    }

    public String getType() { 
        return type; 
    }

    public void setType(String type) { 
        this.type = type; 
    }

    public String getResult() { 
        return result; 
    }

    public void setResult(String result) { 
        this.result = result; 
    }

    public String getDate() { 
        return date; 
    }

    public void setDate(String date) { 
        this.date = date; 
    }

    public String getStatus() { 
        return status; 
    }

    public void setStatus(String status) { 
        this.status = status; 
    }
}
