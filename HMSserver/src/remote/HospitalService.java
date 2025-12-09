package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import model.Appointment;
import model.LabTest;
import model.Patient;
import model.Prescription;
import model.ICURoom;
import model.Report;

public interface HospitalService extends Remote {
    //Only methods the CLIENT is allowed to call.
    // Patient operations
    Patient getPatient(int id) throws RemoteException;
    void addPatient(Patient p) throws RemoteException;

    // Appointment operations
    Appointment bookAppointment(Appointment a) throws RemoteException;
    List<Appointment> getAppointmentsForDoctor(int doctorId) throws RemoteException;

    // Lab test
    void recordLabTestResult(LabTest test) throws RemoteException;

    // Prescriptions
    void addPrescription(Prescription p) throws RemoteException;

    // ICU management
    ICURoom requestICURoom(int roomID, int doctorID) throws RemoteException;

    // Admin reports
    Report generateReport(String type) throws RemoteException;
}
