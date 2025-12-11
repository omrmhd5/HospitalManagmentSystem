package controllers;

import gui.RequestICU;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmi.ICUInterface;

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
                ICUInterface service =
                        (ICUInterface) registry.lookup("icu");

                // Collect individual fields from GUI
                int requestID = (int) (Math.random() * 100000);
                String doctorName = "Dr. ICU";
                String patientName = gui.getPatientIDField().getText();
                String date = gui.getDateField().getText();
                String time = gui.getTimeField().getText();
                String urgency = gui.getUrgencyField().getSelectedItem().toString();
                String diagnosis = gui.getDiagnosisField().getText();
                String expectedDuration = gui.getDurationField().getText();

                // Pass individual fields (no object!)
                boolean ok = service.createICURequest(requestID, doctorName, patientName, 
                                                      date, time, urgency, diagnosis, expectedDuration);

                JOptionPane.showMessageDialog(gui, ok ? "ICU Request Submitted Successfully!" 
                                 : "Failed to submit ICU request.");

            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(RequestICUController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, "Server error: " + ex.getMessage());
            }
        }
    }
}
