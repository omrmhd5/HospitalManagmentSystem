package client;

import controllers.LoginController;
import controllers.AddPatientRecordController;
import controllers.RecordTestResultController;

import gui.Login;
import gui.AddPatientRecord;
import gui.RecordTestResult;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {

    public static void main(String[] args) throws RemoteException, NotBoundException {

        // Connect to RMI Registry ONCE
        Registry registry = LocateRegistry.getRegistry(1099);

        // ---------------- LOGIN ----------------
        Login loginGui = new Login();
        LoginController loginController =
                new LoginController(loginGui, registry);
        loginGui.setVisible(true);

        //  ADD PATIENT RECORD //IBRAHIM
        AddPatientRecord addPatientGui = new AddPatientRecord();
        AddPatientRecordController addPatientController =
                new AddPatientRecordController(addPatientGui, registry);
        addPatientGui.setVisible(true);

        //  RECORD TEST RESULT //IBRAHIM
        RecordTestResult testGui = new RecordTestResult();
        RecordTestResultController testController =
                new RecordTestResultController(testGui, registry);
        testGui.setVisible(true);
    }
}

