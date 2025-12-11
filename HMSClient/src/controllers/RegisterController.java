package controllers;

import gui.Login;
import gui.Register;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmi.AuthInterface;

public class RegisterController {
    
    private final Register gui;
    private final Registry registry;
    
    // Mahmoud
    public RegisterController(Register gui, Registry registry) {
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
                
                String email = gui.getTxtEmail().getText().trim();
                String password = new String(gui.getTxtPassword().getPassword());
                String name = gui.getTxtName().getText().trim();
                String userIDStr = gui.getTxtUserID().getText().trim();
                String role = gui.getRole();
                
                if (email.isEmpty() || password.isEmpty() || name.isEmpty() || userIDStr.isEmpty()) {
                    JOptionPane.showMessageDialog(gui, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int userID;
                try {
                    userID = Integer.parseInt(userIDStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(gui, "User ID must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Mahmoud
                boolean emailExists = authService.emailExists(email);
                if (emailExists) {
                    JOptionPane.showMessageDialog(gui, "Email already exists", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Mahmoud
                boolean success = authService.register(userID, name, email, password, role);
                
                if (success) {
                    JOptionPane.showMessageDialog(gui, "Registration successful! You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    String currentRole = gui.getRole();
                    gui.dispose();
                    
                    // Mahmoud
                    Login loginGui = new Login(currentRole);
                    LoginController loginController = new LoginController(loginGui, registry);
                } else {
                    JOptionPane.showMessageDialog(gui, "Registration failed", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, "Server error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Mahmoud
    class SwitchToLoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String role = gui.getRole();
            gui.dispose();
            
            // Mahmoud
            Login loginGui = new Login(role);
            LoginController loginController = new LoginController(loginGui, registry);
        }
    }
}

