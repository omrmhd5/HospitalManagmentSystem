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

    boolean addPatient(Patient p) throws RemoteException;

    Patient getPatientByID(int patientID) throws RemoteException;

    boolean addPatientRecord(int patientID, String record) throws RemoteException;

    String getAllRecords(int patientID) throws RemoteException;
}