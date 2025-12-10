package controllers;

import gui.RequestICU;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;
import rmiServer.AppointmentInterface;

public class RequestICUController {

    RequestICU gui;
    Registry registry;

    public RequestICUController(RequestICU gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;

        gui.getSubmitButton().addActionListener(new SubmitAction());
    }

    class SubmitAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                AppointmentInterface service =
                        (AppointmentInterface) registry.lookup("AppointmentService");

                int patientID = Integer.parseInt(gui.getPatientIDField().getText());
                int doctorID  = Integer.parseInt(gui.getDoctorIDField().getText());

                // Prepare the request object
                ICURequest req = new ICURequest(
                    (int) (Math.random() * 100000), // auto ID
                    new Doctor(doctorID, "Doctor Name"), // replace with actual
                    new Patient(patientID, "Patient Name"),
                    gui.getDateField().getText(),
                    gui.getTimeField().getText(),
                    gui.getUrgencyField().getSelectedItem().toString(),
                    gui.getDiagnosisField().getText(),
                    gui.getDurationField().getText()
                );

                boolean ok = service.createICURequest(req);

                gui.setOutput(ok ? "ICU Request Submitted Successfully!" 
                                 : "Failed to submit ICU request.");

            } catch (Exception ex) {
                Logger.getLogger(RequestICUController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
