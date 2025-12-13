package DesignPattern.Observer;

import java.rmi.RemoteException;

public interface AppointmentSubject {

    void addObserver(AppointmentObserver o) throws RemoteException;

    void removeObserver(AppointmentObserver o) throws RemoteException;

    void notifyObservers(String message) throws RemoteException;
}
