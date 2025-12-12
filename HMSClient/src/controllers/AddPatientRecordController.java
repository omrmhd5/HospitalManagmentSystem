/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import gui.AddPatientRecord;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.PatientInterface;

public class AddPatientRecordController {

    private final AddPatientRecord gui;
    private final Registry registry;

    public AddPatientRecordController(AddPatientRecord gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;

        gui.getBtnAddPatient().addActionListener(new AddPatientAction());
        gui.getBtnAddRecord().addActionListener(new AddRecordAction());
    }

    //  ADD NEW PATIENT 
    class AddPatientAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                PatientInterface patientService =
                        (PatientInterface) registry.lookup("patient");

                int patientID = Integer.parseInt(gui.getTxtPatientID().getText());
                String name = gui.getTxtName().getText();               // ✅ FIX
                String contactInfo = gui.getTxtContactInfo().getText();
                String gender = gui.getTxtGender().getText();
                int age = Integer.parseInt(gui.getTxtAge().getText());
                String medicalHistory = gui.getTxtMedicalHistory().getText();

                // Call RMI with individual fields (no object!)
                boolean ok = service.addPatient(id, name, contact, gender, age, history);

                if (success) {
                    gui.setOutputMessage("Patient added successfully.");
                } else {
                    gui.setOutputMessage("Failed to add patient.");
                }

            } catch (NumberFormatException ex) {
                gui.setOutputMessage("Invalid Patient ID or Age.");
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(AddPatientRecordController.class.getName())
                        .log(Level.SEVERE, null, ex);
                gui.setOutputMessage("Server error. Please try again.");
            }
        }
    }

    // ADD PATIENT RECORD 
    class AddRecordAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                PatientInterface patientService =
                        (PatientInterface) registry.lookup("patient");

                int patientID = Integer.parseInt(gui.getTxtPatientID().getText());
                String recordText = gui.getTxtMedicalHistory().getText();

                boolean success =
                        patientService.addPatientRecord(patientID, recordText);

                if (success) {
                    gui.setOutputMessage("Patient record added successfully.");
                } else {
                    gui.setOutputMessage("Patient not found.");
                }

            } catch (NumberFormatException ex) {
                gui.setOutputMessage("Invalid Patient ID.");
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(AddPatientRecordController.class.getName())
                        .log(Level.SEVERE, null, ex);
                gui.setOutputMessage("Server error. Please try again.");
            }
        }
    }
}