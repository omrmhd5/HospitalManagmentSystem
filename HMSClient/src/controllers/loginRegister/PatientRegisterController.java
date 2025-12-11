package controllers.loginRegister;

import gui.loginRegister.Login;
import gui.loginRegister.PatientRegister;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmi.AuthInterface;

public class PatientRegisterController {
    
    private final PatientRegister gui;
    private final Registry registry;
    
    // Mahmoud
    public PatientRegisterController(PatientRegister gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;
        
        gui.getBtnRegister().addActionListener(new RegisterAction());
        gui.getBtnSwitchToLogin().addActionListener(new SwitchToLoginAction());
    }
    
    // Mahmoud
    class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Mahmoud
                AuthInterface authService = (AuthInterface) registry.lookup("auth");
                
                String userIDStr = gui.getTxtUserID().getText().trim();
                String name = gui.getTxtName().getText().trim();
                String email = gui.getTxtEmail().getText().trim();
                String password = new String(gui.getTxtPassword().getPassword());
                String gender = (String) gui.getCmbGender().getSelectedItem();
                String ageStr = gui.getTxtAge().getText().trim();
                String phone = gui.getTxtPhone().getText().trim();
                String address = gui.getTxtAddress().getText().trim();
                
                if (userIDStr.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty() || ageStr.isEmpty()) {
                    JOptionPane.showMessageDialog(gui, "Please fill in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int userID;
                int age;
                try {
                    userID = Integer.parseInt(userIDStr);
                    age = Integer.parseInt(ageStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(gui, "User ID and Age must be numbers", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Mahmoud
                boolean emailExists = authService.emailExists(email);
                if (emailExists) {
                    JOptionPane.showMessageDialog(gui, "Email already exists", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Mahmoud
                boolean success = authService.registerPatient(userID, name, email, password, gender, age, phone, address);
                
                if (success) {
                    JOptionPane.showMessageDialog(gui, "Registration successful! You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    gui.dispose();
                    
                    // Mahmoud
                    Login loginGui = new Login("Patient");
                    LoginController loginController = new LoginController(loginGui, registry);
                } else {
                    JOptionPane.showMessageDialog(gui, "Registration failed", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(PatientRegisterController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, "Server error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Mahmoud
    class SwitchToLoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.dispose();
            
            // Mahmoud
            Login loginGui = new Login("Patient");
            LoginController loginController = new LoginController(loginGui, registry);
        }
    }
}

