package controllers;

import gui.RecordDiagnosis;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmi.DiagnosisInterface;

public class RecordDiagnosisController {

    RecordDiagnosis gui;
    Registry registry;

    public RecordDiagnosisController(RecordDiagnosis gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;

        gui.getSubmitButton().addActionListener(new SubmitAction());
    }

    class SubmitAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                DiagnosisInterface service =
                        (DiagnosisInterface) registry.lookup("diagnosis");

                int diagnosisID = (int) (Math.random() * 100000);

                
                int appointmentID =
                        Integer.parseInt(gui.getAppointmentIDField().getText());

                String notes = gui.getClinicalNotesField().getText();
                String diagnosisText = gui.getDiagnosisField().getText();

               
                boolean ok = service.recordDiagnosis(
                        diagnosisID,
                        appointmentID,
                        notes,
                        diagnosisText
                );

                JOptionPane.showMessageDialog(gui,
                        ok ? "Diagnosis successfully recorded!"
                           : "Failed to record diagnosis.");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gui,
                        "Appointment ID must be a number",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);

            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(RecordDiagnosisController.class.getName())
                      .log(Level.SEVERE, null, ex);
            }
        }
    }
}
