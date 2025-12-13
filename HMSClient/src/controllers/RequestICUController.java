package controllers;

import gui.RequestICU;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import rmi.DoctorRequestInterface;
import rmi.PatientInterface;
import rmi.ICUInterface;


public class RequestICUController {

    RequestICU gui;
    Registry registry;

    public RequestICUController(RequestICU gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;
        gui.setVisible(true);
        gui.getSubmitButton().addActionListener(new SubmitAction());
        
        
        loadPatients();
    }
    
    
    private void loadPatients() {
        try {
            PatientInterface patientService = (PatientInterface) registry.lookup("patient");
            List<String> patients = patientService.getAllPatientNames();
            
            if (patients.isEmpty()) {
                gui.getCmbPatientName().setModel(new DefaultComboBoxModel<>(new String[]{"No patients available"}));
            } else {
                gui.getCmbPatientName().setModel(new DefaultComboBoxModel<>(patients.toArray(new String[0])));
            }
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(RequestICUController.class.getName()).log(Level.SEVERE, null, ex);
            gui.getCmbPatientName().setModel(new DefaultComboBoxModel<>(new String[]{"Error loading patients"}));
        }
    }

    class SubmitAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
               
                if (!validateInput()) {
                    return;
                }

               
                DoctorRequestInterface doctorRequestService = (DoctorRequestInterface) registry.lookup("doctorrequest");

              
                String doctorEmail = gui.getDoctorEmail(); 
                String selectedPatient = (String) gui.getCmbPatientName().getSelectedItem();
                String urgency = gui.getUrgencyField().getSelectedItem().toString();
                String diagnosis = gui.getDiagnosisField().getText().trim();

                
                Date selectedDate = gui.getDateChooser().getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = sdf.format(selectedDate);

               
                String timeHour = (String) gui.getCmbTimeHour().getSelectedItem();
                String timeMinute = (String) gui.getCmbTimeMinute().getSelectedItem();
                String formattedTime = timeHour + ":" + timeMinute;

               
                String durationHour = (String) gui.getCmbDurationHour().getSelectedItem();
                String durationMinute = (String) gui.getCmbDurationMinute().getSelectedItem();
                String expectedDuration = durationHour + " hours " + durationMinute + " minutes";

               
                boolean ok = doctorRequestService.executeICURequest(
                    doctorEmail,
                    selectedPatient,
                    formattedDate,
                    formattedTime,
                    urgency,
                    diagnosis,
                    expectedDuration
                );

                if (ok) {
                    
                    ICUInterface icuService = (ICUInterface) registry.lookup("icu");

                   
                    String currentState = icuService.getCurrentICUState();

                    JOptionPane.showMessageDialog(gui,
                        "ICU Request Submitted Successfully!\n" +
                        "Current ICU State: " + currentState,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(gui, 
                        "Failed to submit ICU request. Please try again.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }

            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(RequestICUController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, 
                    "Error communicating with server: " + ex.getMessage(), 
                    "Connection Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
    private boolean validateInput() {
       
        String selectedPatient = (String) gui.getCmbPatientName().getSelectedItem();
        if (selectedPatient == null || selectedPatient.equals("No patients available") || 
            selectedPatient.equals("Error loading patients") || selectedPatient.equals("Loading patients...")) {
            JOptionPane.showMessageDialog(gui, 
                "Please select a valid patient", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

      
        Date selectedDate = gui.getDateChooser().getDate();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(gui, 
                "Please select a date", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            gui.getDateChooser().requestFocus();
            return false;
        }

        
        Object timeHour = gui.getCmbTimeHour().getSelectedItem();
        Object timeMinute = gui.getCmbTimeMinute().getSelectedItem();
        if (timeHour == null || timeMinute == null) {
            JOptionPane.showMessageDialog(gui, 
                "Please select a time", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

      
        Object urgency = gui.getUrgencyField().getSelectedItem();
        if (urgency == null) {
            JOptionPane.showMessageDialog(gui, 
                "Please select an urgency level", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

       
        String diagnosis = gui.getDiagnosisField().getText().trim();
        if (diagnosis.isEmpty()) {
            JOptionPane.showMessageDialog(gui, 
                "Please enter a diagnosis", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            gui.getDiagnosisField().requestFocus();
            return false;
        }

       
        Object durationHour = gui.getCmbDurationHour().getSelectedItem();
        Object durationMinute = gui.getCmbDurationMinute().getSelectedItem();
        if (durationHour == null || durationMinute == null) {
            JOptionPane.showMessageDialog(gui, 
                "Please select an expected duration", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }



    
    private void clearForm() {
        gui.getCmbPatientName().setSelectedIndex(0);
        gui.getDateChooser().setDate(null);
        gui.getCmbTimeHour().setSelectedIndex(0);
        gui.getCmbTimeMinute().setSelectedIndex(0);
        gui.getUrgencyField().setSelectedIndex(0);
        gui.getDiagnosisField().setText("");
        gui.getCmbDurationHour().setSelectedIndex(0);
        gui.getCmbDurationMinute().setSelectedIndex(0);
        gui.getCmbPatientName().requestFocus();
    }
}
