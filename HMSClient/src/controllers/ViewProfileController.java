package controllers;

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
    
    // References to GUI and RMI registry
    ViewProfile gui;
    Registry r;
    
    // Constructor takes GUI and RMI registry as parameters
    public ViewProfileController(ViewProfile gui, Registry r) {
        this.gui = gui;
        this.r = r;
        
        // Load profile data when controller is created
        loadProfileData();
        
        // Register the Edit button with our action listener
        gui.getEditButton().addActionListener(new EditButtonAction());
    }
    
    // Load profile data from server and populate GUI fields
    private void loadProfileData() {
        try {
            // Lookup the patient remote object
            PatientInterface patient = (PatientInterface) r.lookup("patient");
            
            // Call remote methods to get profile data
            String name = patient.getProfileName();
            String dob = patient.getProfileDateOfBirth();
            String gender = patient.getProfileGender();
            String address = patient.getProfileAddress();
            String phone = patient.getProfilePhoneNumber();
            
            // Populate GUI fields with profile data
            gui.getNameField().setText(name);
            gui.getDateOfBirthField().setText(dob);
            gui.getGenderField().setText(gender);
            gui.getAddressField().setText(address);
            gui.getPhoneNumberField().setText(phone);
            
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
                    String name = gui.getNameField().getText();
                    String dob = gui.getDateOfBirthField().getText();
                    String gender = gui.getGenderField().getText();
                    String address = gui.getAddressField().getText();
                    String phone = gui.getPhoneNumberField().getText();
                    
                    // Lookup patient remote object and update profile
                    PatientInterface patient = (PatientInterface) r.lookup("patient");
                    patient.updateProfile(name, dob, gender, address, phone);
                    
                    // Switch back to view mode - disable all fields
                    gui.getNameField().setEnabled(false);
                    gui.getDateOfBirthField().setEnabled(false);
                    gui.getGenderField().setEnabled(false);
                    gui.getAddressField().setEnabled(false);
                    gui.getPhoneNumberField().setEnabled(false);
                    gui.getEditButton().setText("Edit");
                    
                    System.out.println("Profile updated successfully!");
                }
                
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(ViewProfileController.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }
}

