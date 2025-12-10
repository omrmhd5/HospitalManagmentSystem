/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rmi;

import model.Drug;
import java.rmi.RemoteException;

/**
 *
 * @author COMPUMARTS
 */
public interface PharmacyInterface {
    boolean addDrug(Drug d) throws RemoteException;

Drug getDrugByID(int id) throws RemoteException;

boolean receiveDrugStock(int drugID, int amount) throws RemoteException;

boolean dispenseDrug(int drugID, int amount) throws RemoteException;

boolean updateDrug(Drug d) throws RemoteException;

    
}
