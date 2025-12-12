package model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import rmi.ICUInterface;
import server.DB;

public class ICURoom extends UnicastRemoteObject implements ICUInterface {

    private int roomID;
    private String roomNumber;
    private String state;
    private final DB db;

    // Rana
    public ICURoom(DB db) throws RemoteException {
        this.db = db;
        this.state = "Available";
    }

    // Rana
    public ICURoom(int roomID, String roomNumber, DB db) throws RemoteException {
        this.db = db;
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.state = "Available";
    }

    // Rana
    public void handleRequest(int doctorID) {
        this.state = "Requested";
    }

    // Rana
    @Override
    public boolean createICURequest(int requestID, String doctorName, String patientName, 
                                    String date, String time, String urgency, 
                                    String diagnosis, String expectedDuration) throws RemoteException {
        Patient patient = db.getPatientByName(patientName);
        Doctor doctor = db.getDoctorByName(doctorName);
        
        // If doctor not found in database, create a basic doctor object (for cases like "Dr. ICU")
        if (doctor == null) {
            doctor = new Doctor(0, doctorName, "", "", 0, "", "");
        }
        
        // If patient not found, this is an error - should not happen
        if (patient == null) {
            throw new RemoteException("Patient not found: " + patientName);
        }
        
        ICURequest req = new ICURequest(requestID, doctor, patient, date, time, urgency, diagnosis, expectedDuration);
        db.addICURequest(req);
        return true;
    }

    // Rana
    @Override
    public String getICURequestsForPatient(int patientID) throws RemoteException {
        List<ICURequest> requests = db.getRequestsForPatient(patientID);
        
        if (requests == null || requests.isEmpty()) {
            return "No ICU requests found for this patient";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("ICU Requests\n");
        result.append("====================\n");
        
        for (ICURequest req : requests) {
            result.append("\n").append(req.toReadableString()).append("\n");
            result.append("--------------------\n");
        }
        
        return result.toString();
    }

    public int getRoomID() { return roomID; }
    public void setRoomID(int roomID) { this.roomID = roomID; }
    
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
}
