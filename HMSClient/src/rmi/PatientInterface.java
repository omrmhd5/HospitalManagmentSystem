/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;
import model.Patient;
/**
 *
 * @author omarm
 */
//ibrahim
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

    boolean addPatient(Patient p) throws RemoteException;

    Patient getPatientByID(int patientID) throws RemoteException;

    boolean addPatientRecord(int patientID, String record) throws RemoteException;

    String getAllRecords(int patientID) throws RemoteException;
    
    // Mahmoud
    String viewPatientRecord(String patientName) throws RemoteException;
}
