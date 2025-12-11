package controllers;

import gui.PatientDashboard;
import gui.RoleSelect;
import gui.BookAppointment;
import gui.ViewAvailableReservations;
import gui.ManageAppointment;
import gui.ViewProfile;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

public class PatientDashboardController {
    
    private final PatientDashboard gui;
    private final Registry registry;
    
    // Mahmoud
    public PatientDashboardController(PatientDashboard gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;
        
        gui.getBtnBookAppointment().addActionListener(new BookAppointmentAction());
        gui.getBtnViewAvailableReservations().addActionListener(new ViewAvailableReservationsAction());
        gui.getBtnManageAppointment().addActionListener(new ManageAppointmentAction());
        gui.getBtnViewProfile().addActionListener(new ViewProfileAction());
        gui.getBtnLogout().addActionListener(new LogoutAction());
    }
    
    // Mahmoud
    class BookAppointmentAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Mahmoud
            BookAppointment bookAppointmentGui = new BookAppointment(gui.getPatientName(), gui.getPatientEmail());
            BookAppointmentController bookAppointmentController = new BookAppointmentController(bookAppointmentGui, registry);
        }
    }
    
    // Mahmoud
    class ViewAvailableReservationsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Mahmoud
            ViewAvailableReservations viewAvailableReservationsGui = new ViewAvailableReservations();
            ViewAvailableReservationsController viewAvailableReservationsController = new ViewAvailableReservationsController(viewAvailableReservationsGui, registry);
        }
    }
    
    // Mahmoud
    class ManageAppointmentAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Mahmoud
            ManageAppointment manageAppointmentGui = new ManageAppointment();
            ManageAppointmentController manageAppointmentController = new ManageAppointmentController(manageAppointmentGui, registry);
        }
    }
    
    // Mahmoud
    class ViewProfileAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Mahmoud
            ViewProfile viewProfileGui = new ViewProfile();
            ViewProfileController viewProfileController = new ViewProfileController(viewProfileGui, registry);
        }
    }
    
    // Mahmoud
    class LogoutAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(gui, 
                "Are you sure you want to logout?", 
                "Logout Confirmation", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                gui.dispose();
                
                // Mahmoud
                RoleSelect roleSelect = new RoleSelect();
                RoleSelectController roleSelectController = new RoleSelectController(roleSelect, registry);
            }
        }
    }
}

