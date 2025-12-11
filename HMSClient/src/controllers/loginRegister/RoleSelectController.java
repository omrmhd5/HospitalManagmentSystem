/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.loginRegister;

import gui.loginRegister.RoleSelect;
import gui.loginRegister.Login;
import gui.loginRegister.PatientRegister;
import gui.loginRegister.DoctorRegister;
import gui.loginRegister.Register;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

public class RoleSelectController {
    
    RoleSelect gui;
    Registry r;
    
    // Mahmoud
    public RoleSelectController(RoleSelect gui, Registry r) {
        this.gui = gui;
        this.r = r;
        gui.getContinueButton().addActionListener(new ContinueAction());
    }
    
    // Mahmoud
    class ContinueAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            String selectedRole = gui.getSelectedRole();
            
            if (selectedRole == null) {
                JOptionPane.showMessageDialog(gui, "Please select a role", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            gui.dispose();
            
            // Mahmoud
            Login loginGui = new Login(selectedRole);
            LoginController loginController = new LoginController(loginGui, r);
        }
    }
}
