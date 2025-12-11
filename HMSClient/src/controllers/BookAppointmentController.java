package controllers;

import gui.BookAppointment;
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

public class BookAppointmentController {
    
    private final BookAppointment gui;
    private final Registry registry;
    
    // Mahmoud
    public BookAppointmentController(BookAppointment gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;
        
        // Mahmoud
        gui.getLblPatientName().setText("Patient: " + gui.getPatientName());
        
        gui.getBtnConfirmBooking().addActionListener(new ConfirmBookingAction());
        gui.getBtnCancel().addActionListener(new CancelAction());
        
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
            
            if (doctors.isEmpty()) {
                gui.getCmbDoctor().setModel(new DefaultComboBoxModel<>(new String[]{"No doctors available"}));
            } else {
                gui.getCmbDoctor().setModel(new DefaultComboBoxModel<>(doctors.toArray(new String[0])));
            }
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(BookAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
            gui.getCmbDoctor().setModel(new DefaultComboBoxModel<>(new String[]{"Error loading doctors"}));
        }
    }
    
    // Mahmoud
    class ConfirmBookingAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Mahmoud
                AppointmentInterface appointmentService = (AppointmentInterface) registry.lookup("appointment");
                
                String selectedDoctor = (String) gui.getCmbDoctor().getSelectedItem();
                Date selectedDate = gui.getDateChooser().getDate();
                String selectedTimeSlot = (String) gui.getCmbTimeSlot().getSelectedItem();
                
                if (selectedDoctor == null || selectedDoctor.equals("No doctors available") || selectedDoctor.equals("Error loading doctors")) {
                    JOptionPane.showMessageDialog(gui, "Please select a valid doctor", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (selectedDate == null) {
                    JOptionPane.showMessageDialog(gui, "Please select an appointment date", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (selectedTimeSlot == null) {
                    JOptionPane.showMessageDialog(gui, "Please select a time slot", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Mahmoud - Extract doctor name (remove specialty)
                String doctorName = selectedDoctor.split(" - ")[0];
                
                // Mahmoud - Format date
                String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);
                
                // Mahmoud - Book appointment
                String response = appointmentService.bookAppointment(
                    gui.getPatientName(),
                    doctorName,
                    formattedDate,
                    selectedTimeSlot
                );
                
                JOptionPane.showMessageDialog(gui, response, "Success", JOptionPane.INFORMATION_MESSAGE);
                
                gui.dispose();
                
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(BookAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, "Booking failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Mahmoud
    class CancelAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.dispose();
        }
    }
}
