package controllers.loginRegister;

import controllers.DoctorDashboardController;
import controllers.PatientDashboardController;
import gui.DoctorDashboard;
import gui.loginRegister.Login;
import gui.PatientDashboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmi.AuthInterface;

public class LoginController {
    
    private final Login gui;
    private final Registry registry;
    
    // Mahmoud
    public LoginController(Login gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;
        
        gui.getBtnLogin().addActionListener(new LoginAction());
        gui.getBtnSwitchToRegister().addActionListener(new SwitchToRegisterAction());
    }
    
    // Mahmoud
    class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Mahmoud
                AuthInterface authService = (AuthInterface) registry.lookup("auth");
                
                String email = gui.getTxtEmail().getText().trim();
                String password = new String(gui.getTxtPassword().getPassword());
                String role = gui.getRole();
                
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(gui, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Mahmoud
                String result = authService.login(email, password, role);
                
                if (result.contains("successful")) {
                    // Mahmoud - Extract name from result: "Login successful! Welcome Name (Role)"
                    String name = extractNameFromResult(result);
                    
                    JOptionPane.showMessageDialog(gui, result, "Success", JOptionPane.INFORMATION_MESSAGE);
                    gui.dispose();
                    
                    // Mahmoud - Open appropriate dashboard based on role
                    if (role.equals("Patient")) {
                        PatientDashboard patientDashboard = new PatientDashboard(name, email);
                        PatientDashboardController patientDashboardController = new PatientDashboardController(patientDashboard, registry);
                    } else if (role.equals("Doctor")) {
                        DoctorDashboard doctorDashboard = new DoctorDashboard(name, email);
                        DoctorDashboardController doctorDashboardController = new DoctorDashboardController(doctorDashboard, registry);
                    } else {
                        JOptionPane.showMessageDialog(null, "Dashboard for " + role + " is coming soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(gui, result, "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, "Server error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        // Mahmoud
        private String extractNameFromResult(String result) {
            try {
                // Format: "Login successful! Welcome Name (Role)"
                int welcomeIndex = result.indexOf("Welcome ") + 8;
                int roleIndex = result.indexOf(" (", welcomeIndex);
                if (welcomeIndex > 7 && roleIndex > welcomeIndex) {
                    return result.substring(welcomeIndex, roleIndex);
                }
            } catch (Exception ex) {
                // If extraction fails, return default
            }
            return "User";
        }
    }
    
    // Mahmoud
    class SwitchToRegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String role = gui.getRole();
            gui.dispose();
            
            // Mahmoud - Route to role-specific register form
            switch (role) {
                case "Patient":
                    gui.loginRegister.PatientRegister patientRegisterGui = new gui.loginRegister.PatientRegister();
                    PatientRegisterController patientRegisterController = new PatientRegisterController(patientRegisterGui, registry);
                    break;
                    
                case "Doctor":
                    gui.loginRegister.DoctorRegister doctorRegisterGui = new gui.loginRegister.DoctorRegister();
                    DoctorRegisterController doctorRegisterController = new DoctorRegisterController(doctorRegisterGui, registry);
                    break;
                    
                default:
                    // Mahmoud - For other roles, use basic register form
                    gui.loginRegister.Register registerGui = new gui.loginRegister.Register(role);
                    RegisterController registerController = new RegisterController(registerGui, registry);
                    break;
            }
        }
    }
}
