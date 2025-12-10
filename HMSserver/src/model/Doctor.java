package model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Doctor extends User implements Serializable {

    private int doctorID;
    private String specialization;
    private String availabilitySchedule;

    private Object requestStrategy;     // UML: DoctorRequestStrategy
    private Object requestChain;        // UML: ICURequestHandler

    private ArrayList<Appointment> appointments = new ArrayList<>();

    public Doctor() throws RemoteException{}

    public Doctor(int userID, String name, String email, String password,
                  int doctorID, String specialization, String availabilitySchedule) throws RemoteException {
        super(userID, name, email, password, "Doctor");
        this.doctorID = doctorID;
        this.specialization = specialization;
        this.availabilitySchedule = availabilitySchedule;
    }

    // ---------- UML METHODS ----------

    public ArrayList<Appointment> viewAppointment() {
        return appointments;
    }

    public void recordDiagnosis(String diagnosis) {
        System.out.println("Diagnosis recorded: " + diagnosis);
    }

    public Prescription recordPrescription(Patient p, String medicine, String dosage) {
        Prescription pres = new Prescription(0, p, this);
        pres.addMedicine(medicine, dosage);
        return pres;
    }

    public LabTest requestLabTest(Patient p, String type) {
        return new LabTest(0, type, "Today");
    }

    public void requestMedicineRefill() {
        System.out.println("Medicine refill requested.");
    }

    public ICURoom requestICURoom(ICURoom room) {
        room.handleRequest(this.doctorID);
        return room;
    }

    public void viewPatientRecord(Patient p) {
        System.out.println("Viewing patient record: " + p.getPatientID());
    }

    public void setRequestStrategy(Object strategy) {
        this.requestStrategy = strategy;
    }

    public void executeRequest(Appointment appointment) {
        System.out.println("Executing request for appointment: " + appointment.getAppointmentID());
    }

    public void updateAppointment(Appointment appointment, String message) {
        appointment.updateStatus(message);
    }

    public int getDoctorID() { return doctorID; }
}
