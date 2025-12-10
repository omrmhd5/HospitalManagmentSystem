/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import GUI.RecordPrescription;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmi.PrescriptionInterface;

/**
 *
 * @author omrmh
 */
public class RecordPrescriptionController {
    
    // We have reference to both the GUI and the rmi registry
    RecordPrescription gui;
    Registry r;
    
    // The constructor takes the gui and the rmi registry as paramaters
    public RecordPrescriptionController(RecordPrescription gui, Registry r)
    {
        this.gui = gui;
        this.r = r;
        // This registers the button with our action listener below (the inner class)
        gui.getRecordButton().addActionListener(new RecordBtnAction());
    }
    
    // This class is responsible for handling the button click
    class RecordBtnAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {

                // We try to obtain a remote reference to the prescription remote object
                // that lives on the server. (using the registry object obtained from
                // the constructor above)
                PrescriptionInterface prescriptionService = (PrescriptionInterface) r.lookup("prescription");
                
                String patientName = gui.getPatientNameField().getText();
                String doctor = (String) gui.getDoctorCombo().getSelectedItem();
                // Mahmoud
                String medicine = gui.getMedicineField().getText();
                // Mahmoud
                String dosage = gui.getDosageField().getText();
                // Mahmoud
                String diagnosis = gui.getDiagnosisArea().getText();
                // Mahmoud
                String response = prescriptionService.recordPrescription(patientName, doctor, medicine, dosage, diagnosis);
                JOptionPane.showMessageDialog(gui, response);
               
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(RecordPrescriptionController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, "Recording failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }
    
}

