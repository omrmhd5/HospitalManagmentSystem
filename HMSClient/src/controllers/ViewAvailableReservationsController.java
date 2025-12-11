package controllers;

import gui.ViewAvailableReservations;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import rmi.AppointmentInterface;

public class ViewAvailableReservationsController {
    
    private final ViewAvailableReservations gui;
    private final Registry registry;
    
    // Mahmoud
    public ViewAvailableReservationsController(ViewAvailableReservations gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;
        
        gui.getBtnSearch().addActionListener(new SearchAction());
        gui.getBtnClear().addActionListener(new ClearAction());
        gui.getBtnClose().addActionListener(new CloseAction());
        
        // Mahmoud
        loadDoctors();
    }
    
    // Mahmoud
    private void loadDoctors() {
        try {
            // Mahmoud
            AppointmentInterface appointmentService = (AppointmentInterface) registry.lookup("appointment");
            
            // Mahmoud
            List<String> doctors = appointmentService.getAllDoctorNames();
            
            // Mahmoud - Add "All Doctors" option at the beginning
            doctors.add(0, "All Doctors");
            
            gui.getCmbDoctorName().setModel(new DefaultComboBoxModel<>(doctors.toArray(new String[0])));
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ViewAvailableReservationsController.class.getName()).log(Level.SEVERE, null, ex);
            gui.getCmbDoctorName().setModel(new DefaultComboBoxModel<>(new String[]{"All Doctors", "Error loading doctors"}));
        }
    }
    
    // Mahmoud
    class SearchAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Mahmoud
                AppointmentInterface appointmentService = (AppointmentInterface) registry.lookup("appointment");
                
                String selectedDoctor = (String) gui.getCmbDoctorName().getSelectedItem();
                String selectedSpecialty = (String) gui.getCmbSpecialty().getSelectedItem();
                Date selectedDate = gui.getDateChooser().getDate();
                
                // Mahmoud - Extract doctor name (remove specialty suffix) or set to null if "All Doctors"
                String doctorName = null;
                if (selectedDoctor != null && !selectedDoctor.equals("All Doctors") && !selectedDoctor.equals("Loading...")) {
                    doctorName = selectedDoctor.split(" - ")[0];
                }
                
                // Mahmoud - Set specialty to null if "All Specialties"
                String specialty = null;
                if (selectedSpecialty != null && !selectedSpecialty.equals("All Specialties")) {
                    specialty = selectedSpecialty;
                }
                
                // Mahmoud - Format date or set to null
                String formattedDate = null;
                if (selectedDate != null) {
                    formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);
                }
                
                // Mahmoud - Call RMI service
                String result = appointmentService.getAvailableReservations(doctorName, specialty, formattedDate);
                
                // Mahmoud - Display results
                gui.getTxtOutput().setText(result);
                
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(ViewAvailableReservationsController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, "Search failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Mahmoud
    class ClearAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.getCmbDoctorName().setSelectedIndex(0);
            gui.getCmbSpecialty().setSelectedIndex(0);
            gui.getDateChooser().setDate(null);
            gui.getTxtOutput().setText("");
        }
    }
    
    // Mahmoud
    class CloseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.dispose();
        }
    }
}
