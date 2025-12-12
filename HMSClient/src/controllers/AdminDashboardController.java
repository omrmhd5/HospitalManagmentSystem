package controllers;

import controllers.loginRegister.RoleSelectController;
import gui.AdminDashboard;
import gui.GenerateReport;
import gui.ManageUsers;
import gui.loginRegister.RoleSelect;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

public class AdminDashboardController {
    
    private final AdminDashboard gui;
    private final Registry registry;
    
    public AdminDashboardController(AdminDashboard gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;
        
        gui.getBtnManageUsers().addActionListener(new ManageUsersAction());
        gui.getBtnGenerateReports().addActionListener(new GenerateReportsAction());
        gui.getBtnHandleICURequests().addActionListener(new HandleICURequestsAction());
        gui.getBtnLogout().addActionListener(new LogoutAction());
    }
    
    class ManageUsersAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ManageUsers manageUsersGui = new ManageUsers();
            ManageUsersController manageUsersController = new ManageUsersController(manageUsersGui, registry);
        }
    }
    
    
    class GenerateReportsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GenerateReport generateReportGui = new GenerateReport();
            GenerateReportController generateReportController = new GenerateReportController(generateReportGui, registry);
        }
    }
    
    class HandleICURequestsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.HandleICURequests handleICURequestsGui = new gui.HandleICURequests("Admin");
            HandleICURequestsController handleICURequestsController = 
                new HandleICURequestsController(handleICURequestsGui, registry);
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

