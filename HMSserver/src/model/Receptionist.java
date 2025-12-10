package model;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Receptionist extends User implements Serializable {

    private int receptionistID;
    private String department;

    public Receptionist() throws RemoteException{}

    public Receptionist(int userID, String name, String email, String password,
                         int receptionistID, String department) throws RemoteException {
        super(userID, name, email, password, "Receptionist");
        this.receptionistID = receptionistID;
        this.department = department;
    }

    // ---------- UML METHODS ----------

    public void addPatientRecord(Patient p) {
        System.out.println("Patient record added: " + p.getPatientID());
    }

    public Appointment bookAppointment(Appointment a) {
        a.confirmReservation();
        return a;
    }

    public void updateAppointment(Appointment appointment, String message) {
        appointment.updateStatus(message);
    }

    public int getReceptionistID() { return receptionistID; }
}
