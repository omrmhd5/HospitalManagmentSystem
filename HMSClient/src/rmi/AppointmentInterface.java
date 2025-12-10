package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.Appointment;

public interface AppointmentInterface extends Remote {
    // Mahmoud
    String bookAppointment(String patientName, String doctorName, String date, String time) throws RemoteException;

    // Get appointment by ID
    Appointment getAppointmentByID(int appointmentID) throws RemoteException;

    // Add new appointment
    boolean addAppointment(Appointment appointment) throws RemoteException;

    // Update (used by cancel/reschedule/other)
    boolean updateAppointment(Appointment appointment) throws RemoteException;

    // Cancel appointment
    boolean cancelAppointment(int appointmentID) throws RemoteException;

    // Reschedule appointment
    boolean rescheduleAppointment(int appointmentID, String newDate, String newTime) throws RemoteException;

    // Main manage function (cancel or reschedule)
    boolean manageAppointment(int appointmentID, String operation, String newDate, String newTime) throws RemoteException;

}
