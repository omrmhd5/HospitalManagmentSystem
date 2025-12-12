/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.Patient;

public interface PatientInterface extends Remote {

    boolean addPatient(Patient p) throws RemoteException;

    boolean addPatientRecord(int patientID, String record)
            throws RemoteException;
}
