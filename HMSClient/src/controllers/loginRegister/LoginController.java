package controllers.loginRegister;

import controllers.AdminDashboardController;
import controllers.DoctorDashboardController;
import controllers.LabTechnicianDashboardController;
import controllers.PatientDashboardController;
import controllers.PharmacistDashboardController;
import controllers.ReceptionistDashboardController;
import gui.AdminDashboard;
import gui.DoctorDashboard;
import gui.LabTechnicianDashboard;
import gui.PharmacistDashboard;
import gui.ReceptionistDashboard;
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
import rmi.UserInterface;

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
                UserInterface userService = (UserInterface) registry.lookup("user");
                
                String email = gui.getTxtEmail().getText().trim();
                String password = new String(gui.getTxtPassword().getPassword());
                String role = gui.getRole();
                
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(gui, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Mahmoud
                String result = userService.login(email, password, role);
                
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
                    } else if (role.equals("Admin")) {
                        AdminDashboard adminDashboard = new AdminDashboard(name, email);
                        AdminDashboardController adminDashboardController = new AdminDashboardController(adminDashboard, registry);
                    } else if (role.equals("Pharmacist")) {
                        PharmacistDashboard pharmacistDashboard = new PharmacistDashboard(name, email);
                        PharmacistDashboardController pharmacistDashboardController = new PharmacistDashboardController(pharmacistDashboard, registry);
                    } else if (role.equals("Lab Technician")) {
                        LabTechnicianDashboard labTechnicianDashboard = new LabTechnicianDashboard(name, email);
                        LabTechnicianDashboardController labTechnicianDashboardController = new LabTechnicianDashboardController(labTechnicianDashboard, registry);
                    } else if (role.equals("Receptionist")) {
                        ReceptionistDashboard receptionistDashboard = new ReceptionistDashboard(name, email);
                        ReceptionistDashboardController receptionistDashboardController = new ReceptionistDashboardController(receptionistDashboard, registry);
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
