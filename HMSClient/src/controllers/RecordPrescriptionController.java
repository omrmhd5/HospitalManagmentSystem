package controllers;

import gui.RecordPrescription;
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
import rmi.PrescriptionInterface;

public class RecordPrescriptionController {
    
    private final RecordPrescription gui;
    private final Registry registry;
    
    // Mahmoud
    public RecordPrescriptionController(RecordPrescription gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;
        
        // Mahmoud
        gui.getLblDoctorName().setText("Doctor: " + gui.getDoctorName());
        gui.getTxtDoctor().setText(gui.getDoctorName());
        
        gui.getBtnRecord().addActionListener(new RecordAction());
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
            Logger.getLogger(RecordPrescriptionController.class.getName()).log(Level.SEVERE, null, ex);
            gui.getCmbPatientName().setModel(new DefaultComboBoxModel<>(new String[]{"Error loading patients"}));
        }
    }
    
    // Mahmoud
    class RecordAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Mahmoud
                PrescriptionInterface prescriptionService = (PrescriptionInterface) registry.lookup("prescription");
                
                String selectedPatient = (String) gui.getCmbPatientName().getSelectedItem();
                String doctorName = gui.getTxtDoctor().getText();
                String medicine = gui.getTxtMedicine().getText();
                String dosage = gui.getTxtDosage().getText();
                String diagnosis = gui.getTxtDiagnosis().getText();
                
                if (selectedPatient == null || selectedPatient.equals("No patients available") || selectedPatient.equals("Error loading patients")) {
                    JOptionPane.showMessageDialog(gui, "Please select a valid patient", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (medicine == null || medicine.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(gui, "Please enter medicine name", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (dosage == null || dosage.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(gui, "Please enter dosage", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Mahmoud - Record prescription
                String response = prescriptionService.recordPrescription(
                    selectedPatient,
                    doctorName,
                    medicine,
                    dosage,
                    diagnosis
                );
                
                JOptionPane.showMessageDialog(gui, response, "Success", JOptionPane.INFORMATION_MESSAGE);
                
                gui.dispose();
                
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(RecordPrescriptionController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, "Recording failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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


