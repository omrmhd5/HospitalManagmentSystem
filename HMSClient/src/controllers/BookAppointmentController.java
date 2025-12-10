/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import GUI.BookAppointment;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmi.AppointmentInterface;

/**
 *
 * @author omrmh
 */
public class BookAppointmentController {
    
    // We have reference to both the GUI and the rmi registry
    BookAppointment gui;
    Registry r;
    private String selectedTime = "9:00";
    
    // The constructor takes the gui and the rmi registry as paramaters
    public BookAppointmentController(BookAppointment gui, Registry r)
    {
        this.gui = gui;
        this.r = r;
        // This registers the button with our action listener below (the inner class)
        gui.getConfirmButton().addActionListener(new ConfirmBtnAction());
        gui.getButton2().addActionListener(new TimeBtnAction());
        gui.getButton3().addActionListener(new TimeBtnAction());
        gui.getButton4().addActionListener(new TimeBtnAction());
        gui.getButton5().addActionListener(new TimeBtnAction());
        gui.getButton6().addActionListener(new TimeBtnAction());
        gui.getButton7().addActionListener(new TimeBtnAction());
        gui.getButton8().addActionListener(new TimeBtnAction());
        gui.getButton9().addActionListener(new TimeBtnAction());
    }
    
    // Mahmoud
    class TimeBtnAction implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            Button btn = (Button) ae.getSource();
            selectedTime = btn.getLabel();
        }
    }
    
    // This class is responsible for handling the button click
    class ConfirmBtnAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {

                // We try to obtain a remote reference to the appointment remote object
                // that lives on the server. (using the registry object obtained from
                // the constructor above)
                AppointmentInterface appointmentService = (AppointmentInterface) r.lookup("appointment");
                
                String doctor = (String) gui.getDoctorCombo().getSelectedItem();
                // Mahmoud
                String date = getFormattedDate();
                // Mahmoud
                String time = selectedTime;
                // Mahmoud
                String response = appointmentService.bookAppointment("Demo Patient", doctor, date, time);
                JOptionPane.showMessageDialog(gui, response);
               
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(BookAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, "Booking failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }
    
    // Mahmoud
    private String getFormattedDate() {
        Date date = gui.getDateChooser().getDate();
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    
}

