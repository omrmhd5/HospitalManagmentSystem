/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rmi;

import java.util.List;
import model.ICURequest;
import java.rmi.RemoteException;

/**
 *
 * @author COMPUMARTS
 */
public interface ICUInterface {
    public boolean createICURequest(ICURequest req) throws RemoteException;
public List<ICURequest> getICURequestsForPatient(int patientID) throws RemoteException;

}
