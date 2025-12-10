package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PatientInterface extends Remote {
    // Get patient profile data fields
    String getProfileName() throws RemoteException;
    String getProfileDateOfBirth() throws RemoteException;
    String getProfileGender() throws RemoteException;
    String getProfileAddress() throws RemoteException;
    String getProfilePhoneNumber() throws RemoteException;
    
    // Update patient profile
    void updateProfile(String name, String dateOfBirth, String gender, 
                      String address, String phoneNumber) throws RemoteException;
}

