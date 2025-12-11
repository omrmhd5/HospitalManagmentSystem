package controllers;

import gui.RequestLabTest;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import rmi.AppointmentInterface;
import rmi.LabTestInterface;

/**
 * Controller for RequestLabTest GUI
 * Follows the same pattern as ViewProfileController and EditProfileController
 * @author salma
 */
public class RequestLabTestController {
    
    // References to GUI and RMI registry
    RequestLabTest gui;
    Registry r;
    
    // Constructor takes GUI and RMI registry as parameters
    public RequestLabTestController(RequestLabTest gui, Registry r) {
        this.gui = gui;
        this.r = r;
        
        // Populate doctor information if available
        if (gui.getDoctorName() != null && !gui.getDoctorName().isEmpty()) {
            gui.getDoctorNameField().setText(gui.getDoctorName());
        }
        if (gui.getDoctorEmail() != null && !gui.getDoctorEmail().isEmpty()) {
            gui.getDoctorEmailField().setText(gui.getDoctorEmail());
            // Load doctor phone number from database
            loadDoctorPhone();
        }
        
        // Load available test types into ComboBox
        loadTestTypes();
        
        // Register the Submit button with our action listener
        gui.getSubmitButton().addActionListener(new SubmitButtonAction());
        
    }
    
    // Load doctor phone number from database
    private void loadDoctorPhone() {
        try {
            String email = gui.getDoctorEmail();
            if (email == null || email.isEmpty()) {
                return;
            }
            
            // Lookup the appointment service to get doctor phone
            AppointmentInterface appointmentService = (AppointmentInterface) r.lookup("appointment");
            String phone = appointmentService.getDoctorPhoneByEmail(email);
            
            if (phone != null && !phone.isEmpty()) {
                gui.getDoctorPhoneField().setText(phone);
            }
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(RequestLabTestController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error loading doctor phone: " + ex.getMessage());
        }
    }
    
    // Load available test types from server
    private void loadTestTypes() {
        try {
            // Lookup the labtest remote object
            LabTestInterface labTest = (LabTestInterface) r.lookup("labtest");
            
            // Get available test types from server
            String[] testTypes = labTest.getAvailableTestTypes();
            
            // Populate ComboBox with test types
            gui.getTestTypeComboBox().setModel(new DefaultComboBoxModel<>(testTypes));
                        
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(RequestLabTestController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error loading test types: " + ex.getMessage());
        }
    }
    
    // Action listener for Submit button
    class SubmitButtonAction implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            submitRequest();
        }
    }
    
    // Method to submit the lab test request
    private void submitRequest() {
        try {
            // Validate and collect data from GUI fields
            String doctorName = gui.getDoctorNameField().getText().trim();
            String doctorEmail = gui.getDoctorEmailField().getText().trim();
            String doctorPhone = gui.getDoctorPhoneField().getText().trim();
            String testType = (String) gui.getTestTypeComboBox().getSelectedItem();
            
            String patientName = gui.getPatientNameField().getText().trim();
            String patientAgeStr = gui.getPatientAgeField().getText().trim();
            
            // Get gender from radio buttons
            String patientGender = "";
            if (gui.getMaleRadioButton().isSelected()) {
                patientGender = "Male";
            } else if (gui.getFemaleRadioButton().isSelected()) {
                patientGender = "Female";
            }
            
            // Validate required fields
            if ( patientName.isEmpty()) {
                JOptionPane.showMessageDialog(gui, 
                    "Patient Name are required!", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validate gender selection
            if (patientGender.isEmpty()) {
                JOptionPane.showMessageDialog(gui, 
                    "Please select a gender (Male or Female)!", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Parse patient age
            int patientAge = 0;
            try {
                if (!patientAgeStr.isEmpty()) {
                    patientAge = Integer.parseInt(patientAgeStr);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(gui, 
                    "Please enter a valid age!", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Get date from date chooser and format it
            String patientDOB = "";
            Date selectedDate = gui.getPatientDateOfBirthChooser().getDate();
            if (selectedDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                patientDOB = sdf.format(selectedDate);
            }
            
            // Lookup labtest remote object and submit request directly
            LabTestInterface labTest = (LabTestInterface) r.lookup("labtest");
            boolean success = labTest.submitLabTestRequest(
                doctorName,
                doctorEmail,
                doctorPhone,
                testType,
                patientName,
                patientAge,
                patientGender,
                patientDOB
            );
            
            if (success) {
                JOptionPane.showMessageDialog(gui, 
                    "Lab test request submitted successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                                
                gui.dispose();
            } else {
                JOptionPane.showMessageDialog(gui, 
                    "Failed to submit lab test request.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(RequestLabTestController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(gui, 
                "Error communicating with server: " + ex.getMessage(), 
                "Connection Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

