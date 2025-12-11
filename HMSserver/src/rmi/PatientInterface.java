/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
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

    // Mahmoud - Add patient
    boolean addPatient(int patientID, String name, String contactInfo, String gender, 
                       int age, String medicalHistory) throws RemoteException;

    // Mahmoud - Get patient by ID (returns formatted string)
    String getPatientByID(int patientID) throws RemoteException;

    boolean addPatientRecord(int patientID, String record) throws RemoteException;

    String getAllRecords(int patientID) throws RemoteException;
    
    // Mahmoud
    String viewPatientRecord(String patientName) throws RemoteException;
}
