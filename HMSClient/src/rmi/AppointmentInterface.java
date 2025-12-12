package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AppointmentInterface extends Remote {
    // Mahmoud - Book appointment
    String bookAppointment(String patientName, String doctorName, String date, String time) throws RemoteException;

    // Rana - Get appointment by ID (returns formatted string)
    String getAppointmentByID(int appointmentID) throws RemoteException;

    // Rana - Update appointment
    boolean updateAppointment(int appointmentID, String patientName, String doctorName, String date, String time, String status) throws RemoteException;

    // Rana - Cancel appointment
    boolean cancelAppointment(int appointmentID) throws RemoteException;

    // Rana - Reschedule appointment
    boolean rescheduleAppointment(int appointmentID, String newDate, String newTime) throws RemoteException;

    // Rana - Main manage function (cancel or reschedule)
    boolean manageAppointment(int appointmentID, String operation, String newDate, String newTime) throws RemoteException;

    // Rana - Get appointments for doctor (returns formatted string)
    String getAppointmentsForDoctor(int doctorID) throws RemoteException;
     int getDoctorIDByEmail(String email) throws RemoteException;

    // Mahmoud - Get all doctor names
    List<String> getAllDoctorNames() throws RemoteException;
    
    // Salma - Get doctor phone number by email
    String getDoctorPhoneByEmail(String email) throws RemoteException;

}
