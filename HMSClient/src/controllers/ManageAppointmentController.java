package controllers;

import gui.ManageAppointment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Appointment;
import rmiServer.AppointmentInterface;

public class ManageAppointmentController {

    ManageAppointment gui;
    Registry registry;

    public ManageAppointmentController(ManageAppointment gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;

        // Registering listeners
        gui.getSearchButton().addActionListener(new SearchButtonAction());
        gui.getCancelButton().addActionListener(new CancelButtonAction());
        gui.getRescheduleButton().addActionListener(new RescheduleButtonAction());
        gui.getConfirmButton().addActionListener(new ConfirmButtonAction());
    }

    /** ------------------ SEARCH ------------------ **/
    class SearchButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                AppointmentInterface service =
                        (AppointmentInterface) registry.lookup("AppointmentService");

                int id = Integer.parseInt(gui.getAppointmentIDField().getText());

                Appointment a = service.getAppointmentByID(id);

                if (a == null) {
                    gui.setDetailsText("Appointment not found.");
                } else {
                    gui.setDetailsText(
                            "Doctor: " + a.getDoctor().getName() + "\n"
                            + "Date: " + a.getDate() + "\n"
                            + "Time: " + a.getTime() + "\n"
                            + "Status: " + a.getStatus()
                    );
                }

            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(ManageAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /** ------------------ CANCEL ------------------ **/
    class CancelButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                AppointmentInterface service =
                        (AppointmentInterface) registry.lookup("AppointmentService");

                int id = Integer.parseInt(gui.getAppointmentIDField().getText());

                boolean ok = service.cancelAppointment(id);

                gui.setMessage(ok
                        ? "Appointment cancelled successfully."
                        : "Cancellation failed.");

            } catch (Exception ex) {
                Logger.getLogger(ManageAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /** ------------------ RESCHEDULE (SHOW FIELDS) ------------------ **/
    class RescheduleButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            // Show hidden fields
            gui.showRescheduleFields();

        }
    }

    /** ------------------ CONFIRM RESCHEDULE ------------------ **/
    class ConfirmButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                AppointmentInterface service =
                        (AppointmentInterface) registry.lookup("AppointmentService");

                int id = Integer.parseInt(gui.getAppointmentIDField().getText());
                String newDate = gui.getNewDateField().getText();
                String newTime = gui.getNewTimeField().getText();

                boolean ok = service.rescheduleAppointment(id, newDate, newTime);

                gui.setMessage(ok
                        ? "Appointment rescheduled successfully."
                        : "Reschedule failed.");

            } catch (Exception ex) {
                Logger.getLogger(ManageAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
