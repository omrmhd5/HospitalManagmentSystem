package controllers;

import controllers.loginRegister.RoleSelectController;
import gui.ReceptionistDashboard;
import gui.loginRegister.RoleSelect;
import gui.BookAppointment;
import gui.AddPatientRecord;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

public class ReceptionistDashboardController {
    
    private final ReceptionistDashboard gui;
    private final Registry registry;
    
    public ReceptionistDashboardController(ReceptionistDashboard gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;
        
        gui.getBtnBookAppointment().addActionListener(new BookAppointmentAction());
        gui.getBtnAddPatientRecord().addActionListener(new AddPatientRecordAction());
        gui.getBtnHandleICURequests().addActionListener(new HandleICURequestsAction());
        gui.getBtnLogout().addActionListener(new LogoutAction());
    }
    
    class BookAppointmentAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Open BookAppointment GUI in receptionist mode - can select patient from dropdown
            BookAppointment bookAppointmentGui = new BookAppointment(true);
            BookAppointmentController bookAppointmentController = new BookAppointmentController(bookAppointmentGui, registry);
        }
    }
    
    class AddPatientRecordAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Open AddPatientRecord GUI
            AddPatientRecord addPatientRecordGui = new AddPatientRecord();
            addPatientRecordGui.setVisible(true);
            AddPatientRecordController addPatientRecordController = new AddPatientRecordController(addPatientRecordGui, registry);
        }
    }
    
    class HandleICURequestsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.HandleICURequests handleICURequestsGui = new gui.HandleICURequests("Receptionist");
            HandleICURequestsController handleICURequestsController = 
                new HandleICURequestsController(handleICURequestsGui, registry);
        }
    }
    
    class LogoutAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(
                gui,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                gui.dispose();
                RoleSelect roleSelectGui = new RoleSelect();
                RoleSelectController roleSelectController = new RoleSelectController(roleSelectGui, registry);
            }
        }
    }
}

