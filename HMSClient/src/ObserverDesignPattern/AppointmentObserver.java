package ObserverDesignPattern;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AppointmentObserver extends Remote {
    void update(String appointmentInfo, String message) throws RemoteException;
}

