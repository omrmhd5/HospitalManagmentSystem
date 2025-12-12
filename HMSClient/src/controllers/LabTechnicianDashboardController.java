package controllers;

import controllers.loginRegister.RoleSelectController;
import gui.LabTechnicianDashboard;
import gui.RecordTestResult;
import gui.loginRegister.RoleSelect;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmi.LabTechnicianInterface;

public class LabTechnicianDashboardController {
    
    private final LabTechnicianDashboard gui;
    private final Registry registry;
    
    public LabTechnicianDashboardController(LabTechnicianDashboard gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;
        
        gui.getBtnRecordTestResult().addActionListener(new RecordTestResultAction());
        gui.getBtnLogout().addActionListener(new LogoutAction());
    }
    
    class RecordTestResultAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RecordTestResult recordTestResultGui = new RecordTestResult();
            RecordTestResultController recordTestResultController = 
                new RecordTestResultController(recordTestResultGui, registry);
        }
    }
    
    class LogoutAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(gui, 
                "Are you sure you want to logout?", 
                "Logout Confirmation", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                gui.dispose();
                
                RoleSelect roleSelect = new RoleSelect();
                RoleSelectController roleSelectController = new RoleSelectController(roleSelect, registry);
            }
        }
    }
}

