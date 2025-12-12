package controllers;

import gui.ManageAppointment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmi.AppointmentInterface;

public class ManageAppointmentController {

    private final ManageAppointment gui;
    private final Registry registry;

    public ManageAppointmentController(ManageAppointment gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;

        gui.getSearchButton().addActionListener(new SearchAction());
        gui.getCancelButton().addActionListener(new CancelAction());
        gui.getRescheduleButton().addActionListener(new ShowRescheduleAction());
        gui.getConfirmButton().addActionListener(new ConfirmRescheduleAction());
    }

    /** ------------------- SEARCH ------------------- **/
    class SearchAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                AppointmentInterface service = (AppointmentInterface) registry.lookup("appointment");

                String idText = gui.getAppointmentIDField().getText().trim();
                if (idText.isEmpty()) {
                    JOptionPane.showMessageDialog(gui, "Please enter Appointment ID.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int id = Integer.parseInt(idText);

                String details = service.getAppointmentByID(id);
                gui.setDetailsText(details);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gui, "Appointment ID must be a number.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(gui, "Error retrieving appointment.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(ManageAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /** ------------------- CANCEL APPOINTMENT ------------------- **/
    class CancelAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            AppointmentInterface service =
                    (AppointmentInterface) registry.lookup("appointment");

            String idText = gui.getAppointmentIDField().getText().trim();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(gui, "Enter Appointment ID first.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = Integer.parseInt(idText);

            boolean ok = service.cancelAppointment(id);

            if (ok) {
                JOptionPane.showMessageDialog(gui,
                        "Appointment cancelled successfully.");

                // 🔄 REFRESH DETAILS AREA
                String updatedDetails = service.getAppointmentByID(id);
                gui.setDetailsText(updatedDetails);

            } else {
                JOptionPane.showMessageDialog(gui,
                        "Cancellation failed.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(gui, "Error cancelling appointment.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ManageAppointmentController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }
}


    /** ------------------- SHOW RESCHEDULE FIELDS ------------------- **/
    class ShowRescheduleAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.showRescheduleFields();
        }
    }

    /** ------------------- CONFIRM RESCHEDULE ------------------- **/
    class ConfirmRescheduleAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            AppointmentInterface service =
                    (AppointmentInterface) registry.lookup("appointment");

            String idText = gui.getAppointmentIDField().getText().trim();
            String newDate = gui.getNewDateField().getText().trim();
            String newTime = gui.getNewTimeField().getText().trim();

            // Validation
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(gui, "Enter Appointment ID.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newDate.isEmpty()) {
                JOptionPane.showMessageDialog(gui, "Enter new date.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newTime.isEmpty()) {
                JOptionPane.showMessageDialog(gui, "Enter new time.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = Integer.parseInt(idText);

            boolean ok = service.rescheduleAppointment(id, newDate, newTime);

            if (ok) {
                JOptionPane.showMessageDialog(gui,
                        "Appointment rescheduled successfully.");

                // 🔄 REFRESH DETAILS AREA
                String updatedDetails = service.getAppointmentByID(id);
                gui.setDetailsText(updatedDetails);

            } else {
                JOptionPane.showMessageDialog(gui,
                        "Reschedule failed.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(gui, "Error rescheduling appointment.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ManageAppointmentController.class.getName())
                  .log(Level.SEVERE, null, ex);
        }
    }
}

}
