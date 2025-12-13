package controllers;

import gui.RequestLabTest;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import rmi.AppointmentInterface;
import rmi.DoctorRequestInterface;
import rmi.LabTestInterface;
import rmi.PatientInterface;

public class RequestLabTestController {
    
    private final RequestLabTest gui;
    private final Registry registry;
    
    // Salma
    public RequestLabTestController(RequestLabTest gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;
        
        gui.getLblDoctorName().setText("Doctor: " + gui.getDoctorName());
        
        // Populate doctor information if available
        if (gui.getDoctorName() != null && !gui.getDoctorName().isEmpty()) {
            gui.getDoctorNameField().setText(gui.getDoctorName());
        }
        if (gui.getDoctorEmail() != null && !gui.getDoctorEmail().isEmpty()) {
            gui.getDoctorEmailField().setText(gui.getDoctorEmail());
           
            loadDoctorPhone();
        }
        
       
        loadPatients();
        
        // Load available test types into ComboBox
        loadTestTypes();
        
        // Disable patient fields initially
        disablePatientFields();
        
        // Add listener to patient dropdown
        gui.getCmbPatientName().addActionListener(new PatientSelectionAction());
        
        // Register the Submit button with our action listener
        gui.getSubmitButton().addActionListener(new SubmitButtonAction());
    }
    
    // Salma
    private void disablePatientFields() {
        gui.getPatientAgeField().setEditable(false);
        gui.getPatientAgeField().setEnabled(false);
        gui.getMaleRadioButton().setEnabled(false);
        gui.getFemaleRadioButton().setEnabled(false);
        gui.getPatientDateOfBirthChooser().setEnabled(false);
    }
    
    // Salma
    class PatientSelectionAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedPatient = (String) gui.getCmbPatientName().getSelectedItem();
            
            if (selectedPatient == null || selectedPatient.equals("No patients available") || 
                selectedPatient.equals("Error loading patients") || selectedPatient.equals("Loading patients...")) {
                clearPatientFields();
                return;
            }
            
            //  Load patient information
            loadPatientInfo(selectedPatient);
        }
    }
    
    // Salma
    private void loadPatientInfo(String patientName) {
        try {
            
            PatientInterface patientService = (PatientInterface) registry.lookup("patient");
            
            // Get patient record
            String record = patientService.viewPatientRecord(patientName);
            
            // Parse patient record to extract information
            parseAndFillPatientInfo(record);
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(RequestLabTestController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(gui, 
                "Error loading patient information: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            clearPatientFields();
        }
    }
    
    // Salma
    private void parseAndFillPatientInfo(String record) {
        if (record == null || record.isEmpty() || record.contains("not found")) {
            clearPatientFields();
            return;
        }
        
        //  Parse the formatted record string
        String[] lines = record.split("\n");
        String age = "";
        String gender = "";
        String dateOfBirth = "";
        
        for (String line : lines) {
            if (line.startsWith("Age:")) {
                age = line.substring(line.indexOf(":") + 1).trim();
            } else if (line.startsWith("Gender:")) {
                gender = line.substring(line.indexOf(":") + 1).trim();
            } else if (line.startsWith("Date of Birth:")) {
                dateOfBirth = line.substring(line.indexOf(":") + 1).trim();
            }
        }
        
        // Fill age
        if (!age.isEmpty()) {
            gui.getPatientAgeField().setText(age);
        } else {
            gui.getPatientAgeField().setText("");
        }
        
        //Fill gender
        if (gender.equalsIgnoreCase("Male")) {
            gui.getMaleRadioButton().setSelected(true);
            gui.getFemaleRadioButton().setSelected(false);
        } else if (gender.equalsIgnoreCase("Female")) {
            gui.getFemaleRadioButton().setSelected(true);
            gui.getMaleRadioButton().setSelected(false);
        } else {
            gui.getMaleRadioButton().setSelected(false);
            gui.getFemaleRadioButton().setSelected(false);
        }
        
        // Fill date of birth
        if (!dateOfBirth.isEmpty()) {
            try {
                // Try yyyy-MM-dd format first
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dob = sdf.parse(dateOfBirth);
                gui.getPatientDateOfBirthChooser().setDate(dob);
            } catch (ParseException e) {
                // Try MM/dd/yyyy format
                try {
                    SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
                    Date dob = sdf2.parse(dateOfBirth);
                    gui.getPatientDateOfBirthChooser().setDate(dob);
                } catch (ParseException e2) {
                    // If parsing fails, leave empty
                    gui.getPatientDateOfBirthChooser().setDate(null);
                }
            }
        } else {
            gui.getPatientDateOfBirthChooser().setDate(null);
        }
    }
    
    // Salma
    private void clearPatientFields() {
        gui.getPatientAgeField().setText("");
        gui.getMaleRadioButton().setSelected(false);
        gui.getFemaleRadioButton().setSelected(false);
        gui.getPatientDateOfBirthChooser().setDate(null);
    }
    
    // Salma
    private void loadPatients() {
        try {
            // Salma
            PatientInterface patientService = (PatientInterface) registry.lookup("patient");
            
            // Salma
            List<String> patients = patientService.getAllPatientNames();
            
            if (patients.isEmpty()) {
                gui.getCmbPatientName().setModel(new DefaultComboBoxModel<>(new String[]{"No patients available"}));
            } else {
                gui.getCmbPatientName().setModel(new DefaultComboBoxModel<>(patients.toArray(new String[0])));
            }
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(RequestLabTestController.class.getName()).log(Level.SEVERE, null, ex);
            gui.getCmbPatientName().setModel(new DefaultComboBoxModel<>(new String[]{"Error loading patients"}));
        }
    }
    
    // Salma
    private void loadDoctorPhone() {
        try {
            String email = gui.getDoctorEmail();
            if (email == null || email.isEmpty()) {
                return;
            }
            
            // Salma
            AppointmentInterface appointmentService = (AppointmentInterface) registry.lookup("appointment");
            String phone = appointmentService.getDoctorPhoneByEmail(email);
            
            if (phone != null && !phone.isEmpty()) {
                gui.getDoctorPhoneField().setText(phone);
            }
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(RequestLabTestController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error loading doctor phone: " + ex.getMessage());
        }
    }
    
    // Salma
    private void loadTestTypes() {
        try {
            // Salma
            LabTestInterface labTest = (LabTestInterface) registry.lookup("labtest");
            
            // Salma
            String[] testTypes = labTest.getAvailableTestTypes();
            
            // Salma
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
            
            // Salma
            String selectedPatient = (String) gui.getCmbPatientName().getSelectedItem();
            String patientAgeStr = gui.getPatientAgeField().getText().trim();
            
            // Salma
            if (selectedPatient == null || selectedPatient.equals("No patients available") || selectedPatient.equals("Error loading patients")) {
                JOptionPane.showMessageDialog(gui, 
                    "Please select a valid patient", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Get gender from radio buttons
            String patientGender = "";
            if (gui.getMaleRadioButton().isSelected()) {
                patientGender = "Male";
            } else if (gui.getFemaleRadioButton().isSelected()) {
                patientGender = "Female";
            }
            
            //  Validate that patient info was loaded
            if (patientGender.isEmpty()) {
                JOptionPane.showMessageDialog(gui, 
                    "Please select a patient to load their information", 
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
            
            // Use Strategy Pattern via DoctorRequestInterface
            DoctorRequestInterface doctorRequestService = (DoctorRequestInterface) registry.lookup("doctorrequest");
            
            // Execute lab test request using strategy pattern
            boolean success = doctorRequestService.executeLabTestRequest(
                doctorEmail,
                testType,
                selectedPatient,
                patientAge,
                patientGender,
                patientDOB,
                doctorPhone
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

