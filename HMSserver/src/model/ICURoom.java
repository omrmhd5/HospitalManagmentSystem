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
    public boolean createICURequest(ICURequest req) throws RemoteException {
        db.addICURequest(req);
        return true;
    }

    // Rana
    @Override
    public List<ICURequest> getICURequestsForPatient(int patientID) throws RemoteException {
        return db.getRequestsForPatient(patientID);
    }

    public int getRoomID() { return roomID; }
    public void setRoomID(int roomID) { this.roomID = roomID; }
    
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
}
