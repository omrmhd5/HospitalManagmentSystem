package controllers;

import gui.ViewAvailableReservations;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AvailableReservation;
import rmi.AppointmentInterface;
import rmiServer.AppointmentInterface;

public class ViewAvailableReservationsController {

    ViewAvailableReservations gui;
    Registry registry;

    public ViewAvailableReservationsController(ViewAvailableReservations gui, Registry registry) {
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

                String doctorName = gui.getDoctorNameField().getText();
                String specialty = gui.getSpecialtyField().getText();
                String date      = gui.getDateField().getText();

                List<AvailableReservation> list = 
                        service.getAvailableReservations(doctorName, specialty, date);

                if (list.isEmpty()) {
                    gui.setOutput("No available reservations found.");
                    return;
                }

                StringBuilder sb = new StringBuilder();
                for (AvailableReservation a : list) {
                    sb.append(a.toReadableString())
                      .append("\n----------------------\n");
                }

                gui.setOutput(sb.toString());

            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(ViewAvailableReservationsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
