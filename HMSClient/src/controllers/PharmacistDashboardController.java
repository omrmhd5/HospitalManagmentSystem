package controllers;

import controllers.loginRegister.RoleSelectController;
import gui.PharmacistDashboard;
import gui.ManageDrugInventory;
import gui.loginRegister.RoleSelect;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

public class PharmacistDashboardController {
    
    private final PharmacistDashboard gui;
    private final Registry registry;
    
    public PharmacistDashboardController(PharmacistDashboard gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;
        
        gui.getBtnManageDrugInventory().addActionListener(new ManageDrugInventoryAction());
        gui.getBtnLogout().addActionListener(new LogoutAction());
    }
    
    class ManageDrugInventoryAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ManageDrugInventory manageDrugInventoryGui = new ManageDrugInventory();
            ManageDrugInventoryController manageDrugInventoryController = 
                new ManageDrugInventoryController(manageDrugInventoryGui, registry);
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


