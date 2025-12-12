package controllers;

import gui.PatientDashboard;
import gui.ViewProfile;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.PatientInterface;

/**
 * Controller for ViewProfile GUI
 * Follows the same pattern as LoginController
 * @author salma
 */
public class ViewProfileController {
    
    // References to GUI, RMI registry, and PatientDashboard for welcome message updates
    ViewProfile gui;
    Registry r;
    PatientDashboard patientDashboard;
    
    // Constructor takes GUI, RMI registry, and PatientDashboard as parameters
    public ViewProfileController(ViewProfile gui, Registry r, PatientDashboard patientDashboard) {
        this.gui = gui;
        this.r = r;
        this.patientDashboard = patientDashboard;
        
        // Load profile data when controller is created
        loadProfileData();
        
        // Register the Edit button with our action listener
        gui.getEditButton().addActionListener(new EditButtonAction());
    }
    
    // Load profile data from server and populate GUI fields
    // Salma
    private void loadProfileData() {
        try {
            // Get patient email from GUI
            String email = gui.getPatientEmail();
            if (email == null || email.isEmpty()) {
                System.err.println("Error: Patient email is not available");
                return;
            }
            
            // Lookup the patient remote object
            PatientInterface patient = (PatientInterface) r.lookup("patient");
            
            // Call remote methods to get profile data by email
            String name = patient.getProfileNameByEmail(email);
            String dob = patient.getProfileDateOfBirthByEmail(email);
            String gender = patient.getProfileGenderByEmail(email);
            String address = patient.getProfileAddressByEmail(email);
            String phone = patient.getProfilePhoneNumberByEmail(email);
            
            // Populate GUI fields with profile data
            gui.getNameField().setText(name != null ? name : "");
            gui.getDateOfBirthField().setText(dob != null ? dob : "");
            gui.getGenderField().setText(gender != null ? gender : "");
            gui.getAddressField().setText(address != null ? address : "");
            gui.getPhoneNumberField().setText(phone != null ? phone : "");
            
            System.out.println("Profile loaded successfully for: " + name);
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ViewProfileController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error loading profile: " + ex.getMessage());
        }
    }
    
    // Action listener for Edit button
    class EditButtonAction implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                // Check if fields are currently enabled (edit mode)
                boolean isEditable = gui.getNameField().isEnabled();
                
                if (!isEditable) {
                    // Switch to edit mode - enable all fields
                    gui.getNameField().setEnabled(true);
                    gui.getDateOfBirthField().setEnabled(true);
                    gui.getGenderField().setEnabled(true);
                    gui.getAddressField().setEnabled(true);
                    gui.getPhoneNumberField().setEnabled(true);
                    gui.getEditButton().setText("Save");
                    System.out.println("Edit mode enabled");
                    
                } else {
                    // Save mode - collect data and send to server
                    String email = gui.getPatientEmail();
                    if (email == null || email.isEmpty()) {
                        System.err.println("Error: Patient email is not available");
                        return;
                    }
                    
                    String name = gui.getNameField().getText();
                    String dob = gui.getDateOfBirthField().getText();
                    String gender = gui.getGenderField().getText();
                    String address = gui.getAddressField().getText();
                    String phone = gui.getPhoneNumberField().getText();
                    
                    // Lookup patient remote object and update profile by email
                    PatientInterface patient = (PatientInterface) r.lookup("patient");
                    boolean success = patient.updateProfileByEmail(email, name, dob, gender, address, phone);
                    
                    if (success) {
                        // Switch back to view mode - disable all fields
                        gui.getNameField().setEnabled(false);
                        gui.getDateOfBirthField().setEnabled(false);
                        gui.getGenderField().setEnabled(false);
                        gui.getAddressField().setEnabled(false);
                        gui.getPhoneNumberField().setEnabled(false);
                        gui.getEditButton().setText("Edit");
                        
                        // Update PatientDashboard welcome message if name changed
                        if (patientDashboard != null) {
                            patientDashboard.updateWelcomeMessage(name);
                        }
                        
                        javax.swing.JOptionPane.showMessageDialog(gui, 
                            "Profile updated successfully!", 
                            "Success", 
                            javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("Profile updated successfully!");
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(gui, 
                            "Failed to update profile. Please try again.", 
                            "Error", 
                            javax.swing.JOptionPane.ERROR_MESSAGE);
                        System.err.println("Failed to update profile");
                    }
                }
                
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(ViewProfileController.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }
}

