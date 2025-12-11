package controllers;

import gui.ViewAppointment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.AppointmentInterface;

public class ViewAppointmentController {

    ViewAppointment gui;
    Registry registry;

    public ViewAppointmentController(ViewAppointment gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;

        gui.getSearchButton().addActionListener(new SearchAction());
    }

    class SearchAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                AppointmentInterface service =
                        (AppointmentInterface) registry.lookup("appointment");

                int doctorID = Integer.parseInt(gui.getDoctorIDField().getText());

                // Returns formatted string
                String appointments = service.getAppointmentsForDoctor(doctorID);

                gui.setOutput(appointments);

            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(ViewAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}