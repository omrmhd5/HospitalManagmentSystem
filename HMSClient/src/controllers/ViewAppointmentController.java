package controllers;

import gui.ViewAppointment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Appointment;
import rmiServer.AppointmentInterface;

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
                        (AppointmentInterface) registry.lookup("AppointmentService");

                int doctorID = Integer.parseInt(gui.getDoctorIDField().getText());

                List<Appointment> list = service.getAppointmentsForDoctor(doctorID);

                if (list.isEmpty()) {
                    gui.setOutput("No appointments found.");
                    return;
                }

                StringBuilder sb = new StringBuilder();
                for (Appointment a : list) {
                    sb.append(a.toReadableString()).append("\n-----------------\n");
                }

                gui.setOutput(sb.toString());

            } catch (Exception ex) {
                Logger.getLogger(ViewAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}