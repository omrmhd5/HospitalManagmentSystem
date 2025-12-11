package controllers;

import gui.EditProfile;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.PatientInterface;

/**
 * Controller for EditProfile GUI
 * Follows the same pattern as ViewProfileController
 * @author salma
 */
public class EditProfileController {
    
    // References to GUI and RMI registry
    EditProfile gui;
    Registry r;
    
    // Constructor takes GUI and RMI registry as parameters
    public EditProfileController(EditProfile gui, Registry r) {
        this.gui = gui;
        this.r = r;
        
        // Load current profile data when controller is created
        loadProfileData();
        
        // Register the Save button with our action listener
        gui.getSaveButton().addActionListener(new SaveButtonAction());
    }
    
    // Load profile data from server and populate GUI fields
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
            
            // Parse and set date of birth
            if (dob != null && !dob.isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(dob);
                    gui.getDateOfBirthChooser().setDate(date);
                } catch (Exception e) {
                    System.err.println("Error parsing date: " + e.getMessage());
                }
            }
            
            // Set gender radio button
            if (gender != null) {
                if ("Male".equalsIgnoreCase(gender)) {
                    gui.getMaleRadioButton().setSelected(true);
                } else if ("Female".equalsIgnoreCase(gender)) {
                    gui.getFemaleRadioButton().setSelected(true);
                }
            }
            
            gui.getAddressField().setText(address != null ? address : "");
            gui.getPhoneNumberField().setText(phone != null ? phone : "");
            
            System.out.println("Edit Profile loaded successfully for: " + name);
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error loading profile: " + ex.getMessage());
        }
    }
    
    // Action listener for Save button
    class SaveButtonAction implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                // Get patient email from GUI
                String email = gui.getPatientEmail();
                if (email == null || email.isEmpty()) {
                    System.err.println("Error: Patient email is not available");
                    javax.swing.JOptionPane.showMessageDialog(gui, 
                        "Error: Patient email is not available", 
                        "Error", 
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Collect data from GUI fields
                String name = gui.getNameField().getText();
                
                // Get date from date chooser and format it
                String dob = "";
                Date selectedDate = gui.getDateOfBirthChooser().getDate();
                if (selectedDate != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    dob = sdf.format(selectedDate);
                }
                
                // Get gender from radio buttons
                String gender = "";
                if (gui.getMaleRadioButton().isSelected()) {
                    gender = "Male";
                } else if (gui.getFemaleRadioButton().isSelected()) {
                    gender = "Female";
                }
                
                String address = gui.getAddressField().getText();
                String phone = gui.getPhoneNumberField().getText();
                
                // Lookup patient remote object and update profile by email
                PatientInterface patient = (PatientInterface) r.lookup("patient");
                boolean success = patient.updateProfileByEmail(email, name, dob, gender, address, phone);
                
                if (success) {
                    javax.swing.JOptionPane.showMessageDialog(gui, 
                        "Profile updated successfully!", 
                        "Success", 
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Profile saved successfully!");
                    
                    gui.dispose();
                } else {
                    javax.swing.JOptionPane.showMessageDialog(gui, 
                        "Failed to update profile. Please try again.", 
                        "Error", 
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                    System.err.println("Failed to update profile");
                }
                
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Error saving profile: " + ex.getMessage());
                javax.swing.JOptionPane.showMessageDialog(gui, 
                    "Error saving profile: " + ex.getMessage(), 
                    "Error", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

