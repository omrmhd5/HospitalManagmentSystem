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
            
            // Parse and set date of birth
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(dob);
                gui.getDateOfBirthChooser().setDate(date);
            } catch (Exception e) {
                System.err.println("Error parsing date: " + e.getMessage());
            }
            
            // Set gender radio button
            if ("Male".equalsIgnoreCase(gender)) {
                gui.getMaleRadioButton().setSelected(true);
            } else if ("Female".equalsIgnoreCase(gender)) {
                gui.getFemaleRadioButton().setSelected(true);
            }
            
            gui.getAddressField().setText(address);
            gui.getPhoneNumberField().setText(phone);
            
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
                
                // Lookup patient remote object and update profile
                PatientInterface patient = (PatientInterface) r.lookup("patient");
                patient.updateProfile(name, dob, gender, address, phone);
                
                System.out.println("Profile saved successfully!");
                
                // Optionally close the window after saving
                gui.dispose();
                
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Error saving profile: " + ex.getMessage());
            }
        }
    }
}

