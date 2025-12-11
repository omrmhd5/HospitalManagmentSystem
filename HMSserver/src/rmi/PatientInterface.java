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

    
    // Salma - Get patient profile by email
    String getProfileNameByEmail(String email) throws RemoteException;
    String getProfileDateOfBirthByEmail(String email) throws RemoteException;
    String getProfileGenderByEmail(String email) throws RemoteException;
    String getProfileAddressByEmail(String email) throws RemoteException;
    String getProfilePhoneNumberByEmail(String email) throws RemoteException;
    
    // Update patient profile
    void updateProfile(String name, String dateOfBirth, String gender, 
                      String address, String phoneNumber) throws RemoteException;
    
    // Salma - Update patient profile by email
    boolean updateProfileByEmail(String email, String name, String dateOfBirth, 
                                 String gender, String address, String phoneNumber) throws RemoteException;

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
