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
import model.Patient;
import rmi.PatientInterface;
/**
 *
 * @author omarm
 */
public class AddPatientRecordController {

    private final AddPatientRecord gui;
    private final Registry registry;

    public AddPatientRecordController(AddPatientRecord gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;

        // Register button listeners
        gui.getBtnAddPatient().addActionListener(new AddPatientAction());
        gui.getBtnAddRecord().addActionListener(new AddRecordAction());
    }

    // =====================================================================
    // ACTION: ADD NEW PATIENT
    // =====================================================================
    class AddPatientAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                PatientInterface service = (PatientInterface) registry.lookup("patient");

                int id = Integer.parseInt(gui.getTxtPatientID().getText());
                String name = gui.getTxtName().getText();
                String gender = gui.getTxtGender().getText();
                int age = Integer.parseInt(gui.getTxtAge().getText());
                String contact = gui.getTxtContactInfo().getText();
                String history = gui.getTxtMedicalHistory().getText();

                // Create model object
                Patient p = new Patient(id, name, contact, gender, age, history);

                boolean ok = service.addPatient(p);

                if (ok) {
                    gui.setOutputMessage("Patient added successfully!");
                } else {
                    gui.setOutputMessage("Failed to add patient.");
                }

            } catch (NumberFormatException ex) {
                gui.setOutputMessage("Invalid number format for ID or Age.");
            } catch (NotBoundException | RemoteException ex) {
                Logger.getLogger(AddPatientRecordController.class.getName()).log(Level.SEVERE, null, ex);
                gui.setOutputMessage("Server error: " + ex.getMessage());
            }
        }
    }

    
    // ACTION: ADD PATIENT RECORD ENTRY
   
    class AddRecordAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                PatientInterface service = (PatientInterface) registry.lookup("patient");

                int patientID = Integer.parseInt(gui.getTxtPatientID().getText());
                String recordText = gui.getTxtMedicalHistory().getText();

                boolean ok = service.addPatientRecord(patientID, recordText);

                if (ok) {
                    gui.setOutputMessage("Record added successfully!");
                } else {
                    gui.setOutputMessage("Failed to add record (patient not found).");
                }

            } catch (NumberFormatException ex) {
                gui.setOutputMessage("Invalid Patient ID.");
            } catch (NotBoundException | RemoteException ex) {
                Logger.getLogger(AddPatientRecordController.class.getName()).log(Level.SEVERE, null, ex);
                gui.setOutputMessage("Server error: " + ex.getMessage());
            }
        }
    }
}
