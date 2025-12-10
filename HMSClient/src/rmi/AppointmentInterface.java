package rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface AppointmentInterface extends Remote {
    // Mahmoud
    String bookAppointment(String patientName, String doctorName, String date, String time) throws RemoteException;
}

