package controllers;

import gui.RecordDiagnosis;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;
import rmiServer.AppointmentInterface;

public class RecordDiagnosisController {

    RecordDiagnosis gui;
    Registry registry;

    public RecordDiagnosisController(RecordDiagnosis gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;

        gui.getSubmitButton().addActionListener(new SubmitAction());
    }

    class SubmitAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                AppointmentInterface service =
                        (AppointmentInterface) registry.lookup("AppointmentService");

                int appointmentID = Integer.parseInt(gui.getAppointmentIDField().getText());
                int patientID     = Integer.parseInt(gui.getPatientIDField().getText());
                int doctorID      = Integer.parseInt(gui.getDoctorIDField().getText());

                String notes     = gui.getClinicalNotesField().getText();
                String diagnosis = gui.getDiagnosisField().getText();

                // Create Diagnosis object
                Diagnosis d = new Diagnosis(
                        (int)(Math.random() * 100000),
                        appointmentID,
                        new Patient(patientID, "Patient Name"),
                        new Doctor(doctorID, "Doctor Name"),
                        notes,
                        diagnosis
                );

                boolean ok = service.recordDiagnosis(d);

                gui.setOutput(ok ?
                    "Diagnosis successfully recorded!" :
                    "Failed to record diagnosis.");

            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(RecordDiagnosisController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
