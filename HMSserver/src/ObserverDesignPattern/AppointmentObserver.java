package ObserverDesignPattern;

import model.Appointment;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AppointmentObserver extends Remote {
    void update(Appointment appointment, String message) throws RemoteException;
}
