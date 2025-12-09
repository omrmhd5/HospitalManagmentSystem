package model;

import java.io.Serializable;

public class ICURoom implements Serializable {

    private int roomID;
    private String roomNumber;
    private String state;      // e.g., "Available", "Requested", "Occupied";

    public ICURoom() { }

    public ICURoom(int roomID, String roomNumber) {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.state = "Available";
    }

    // Logic for ICU room
    public void handleRequest(int doctorID) {
        // later you can log doctorID etc.
        this.state = "Requested";
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getRoomID() { return roomID; }
    public String getState() { return state; }
}
