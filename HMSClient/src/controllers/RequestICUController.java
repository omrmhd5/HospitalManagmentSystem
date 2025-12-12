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
        
        // Load patients from database
        loadPatients();
    }
    
    /**
     * Load all patients from the database into the combo box
     */
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
                // Validate all fields before submission
                if (!validateInput()) {
                    return;
                }

                // Use Strategy Pattern via DoctorRequestInterface
                DoctorRequestInterface doctorRequestService = (DoctorRequestInterface) registry.lookup("doctorrequest");

                // Collect and format all fields from GUI
                String doctorEmail = gui.getDoctorEmail(); // Use logged-in doctor's email
                String selectedPatient = (String) gui.getCmbPatientName().getSelectedItem();
                String urgency = gui.getUrgencyField().getSelectedItem().toString();
                String diagnosis = gui.getDiagnosisField().getText().trim();

                // Format date (already validated in validateInput())
                Date selectedDate = gui.getDateChooser().getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = sdf.format(selectedDate);

                // Format time
                String timeHour = (String) gui.getCmbTimeHour().getSelectedItem();
                String timeMinute = (String) gui.getCmbTimeMinute().getSelectedItem();
                String formattedTime = timeHour + ":" + timeMinute;

                // Format duration
                String durationHour = (String) gui.getCmbDurationHour().getSelectedItem();
                String durationMinute = (String) gui.getCmbDurationMinute().getSelectedItem();
                String expectedDuration = durationHour + " hours " + durationMinute + " minutes";

                // Execute ICU request using strategy pattern
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
                     // 🔹 Get ICU service
                    ICUInterface icuService = (ICUInterface) registry.lookup("icu");

                    // 🔹 Fetch current ICU state
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

    /**
     * Validate all input fields before submission
     */
    private boolean validateInput() {
        // Validate Patient selection
        String selectedPatient = (String) gui.getCmbPatientName().getSelectedItem();
        if (selectedPatient == null || selectedPatient.equals("No patients available") || 
            selectedPatient.equals("Error loading patients") || selectedPatient.equals("Loading patients...")) {
            JOptionPane.showMessageDialog(gui, 
                "Please select a valid patient", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate Date
        Date selectedDate = gui.getDateChooser().getDate();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(gui, 
                "Please select a date", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            gui.getDateChooser().requestFocus();
            return false;
        }

        // Validate Time
        Object timeHour = gui.getCmbTimeHour().getSelectedItem();
        Object timeMinute = gui.getCmbTimeMinute().getSelectedItem();
        if (timeHour == null || timeMinute == null) {
            JOptionPane.showMessageDialog(gui, 
                "Please select a time", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate Urgency
        Object urgency = gui.getUrgencyField().getSelectedItem();
        if (urgency == null) {
            JOptionPane.showMessageDialog(gui, 
                "Please select an urgency level", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate Diagnosis
        String diagnosis = gui.getDiagnosisField().getText().trim();
        if (diagnosis.isEmpty()) {
            JOptionPane.showMessageDialog(gui, 
                "Please enter a diagnosis", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            gui.getDiagnosisField().requestFocus();
            return false;
        }

        // Validate Expected Duration
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



    /**
     * Clear all form fields after successful submission
     */
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
