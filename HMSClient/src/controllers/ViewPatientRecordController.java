package controllers;

import gui.ViewPatientRecord;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import rmi.PatientInterface;

public class ViewPatientRecordController {
    
    private final ViewPatientRecord gui;
    private final Registry registry;
    
    // Mahmoud
    public ViewPatientRecordController(ViewPatientRecord gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;
        
        // Mahmoud
        gui.getLblDoctorName().setText("Doctor: " + gui.getDoctorName());
        
        gui.getBtnViewRecord().addActionListener(new ViewRecordAction());
        gui.getBtnCancel().addActionListener(new CancelAction());
        
        // Mahmoud
        loadPatients();
    }
    
    // Mahmoud
    private void loadPatients() {
        try {
            // Mahmoud
            PatientInterface patientService = (PatientInterface) registry.lookup("patient");
            
            // Mahmoud
            List<String> patients = patientService.getAllPatientNames();
            
            if (patients.isEmpty()) {
                gui.getCmbPatientName().setModel(new DefaultComboBoxModel<>(new String[]{"No patients available"}));
            } else {
                gui.getCmbPatientName().setModel(new DefaultComboBoxModel<>(patients.toArray(new String[0])));
            }
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ViewPatientRecordController.class.getName()).log(Level.SEVERE, null, ex);
            gui.getCmbPatientName().setModel(new DefaultComboBoxModel<>(new String[]{"Error loading patients"}));
        }
    }
    
    // Mahmoud
    class ViewRecordAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Mahmoud
                PatientInterface patientService = (PatientInterface) registry.lookup("patient");
                
                String selectedPatient = (String) gui.getCmbPatientName().getSelectedItem();
                
                if (selectedPatient == null || selectedPatient.equals("No patients available") || selectedPatient.equals("Error loading patients")) {
                    JOptionPane.showMessageDialog(gui, "Please select a valid patient", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Mahmoud - View patient record
                String record = patientService.viewPatientRecord(selectedPatient);
                
                // Mahmoud
                gui.getTxtRecordArea().setText(record);
                
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(ViewPatientRecordController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, "Failed to retrieve record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Mahmoud
    class CancelAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.dispose();
        }
    }
}


