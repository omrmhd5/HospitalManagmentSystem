/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import GUI.ViewPatientRecord;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmi.PatientInterface;

/**
 *
 * @author omrmh
 */
public class ViewPatientRecordController {
    
    // We have reference to both the GUI and the rmi registry
    ViewPatientRecord gui;
    Registry r;
    
    // The constructor takes the gui and the rmi registry as paramaters
    public ViewPatientRecordController(ViewPatientRecord gui, Registry r)
    {
        this.gui = gui;
        this.r = r;
        // This registers the button with our action listener below (the inner class)
        gui.getViewButton().addActionListener(new ViewBtnAction());
    }
    
    // This class is responsible for handling the button click
    class ViewBtnAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {

                // We try to obtain a remote reference to the patient remote object
                // that lives on the server. (using the registry object obtained from
                // the constructor above)
                PatientInterface patientService = (PatientInterface) r.lookup("patient");
                
                String patientName = gui.getPatientNameField().getText();
                // Mahmoud
                String response = patientService.viewPatientRecord(patientName);
                // Mahmoud
                gui.getRecordArea().setText(response);
               
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(ViewPatientRecordController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, "Failed to retrieve record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }
    
}


