/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import gui.RecordTestResult;
import rmi.LabTechnicianInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author omarm
 */
public class RecordTestResultController {
    
    private RecordTestResult gui;
    private Registry registry;

    public RecordTestResultController(RecordTestResult gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;

        gui.getBtnSubmit().addActionListener(new SubmitAction());
    }

    class SubmitAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                int testID = Integer.parseInt(gui.getTxtTestID().getText());
                String result = gui.getTxtResult().getText();

                if (result == null || result.isBlank()) {
                    gui.setOutputMessage("Result cannot be empty.");
                    return;
                }

                LabTechnicianInterface labTech =
                        (LabTechnicianInterface) registry.lookup("labtech");

                String response =
                        labTech.recordLabTestResult(testID, result);

                gui.setOutputMessage(response);

            } catch (NumberFormatException ex) {
                gui.setOutputMessage("Invalid Test ID.");
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(RecordTestResultController.class.getName())
                        .log(Level.SEVERE, null, ex);
                gui.setOutputMessage("Server error. Please try again.");
            }
        }
    }
}
