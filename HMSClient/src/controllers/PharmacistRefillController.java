/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;
import gui.RequestRefill;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import rmi.PharmacistInterface;
/**
 *
 * @author omarm
 */

//ibrahim
public class PharmacistRefillController {
    
    private RequestRefill gui;
    private Registry registry;

    public PharmacistRefillController(RequestRefill gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;

        // Register button listener
        gui.getBtnSendRequest().addActionListener(new SendRequestAction());
    }

    class SendRequestAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Get user input from GUI
                int pharmacistID = Integer.parseInt(gui.getTxtPharmacistID().getText());
                String medicineName = gui.getTxtMedicineName().getText();
                int quantity = Integer.parseInt(gui.getTxtQuantity().getText());

                // Get remote object from RMI registry
                PharmacistInterface pharmacist = 
                        (PharmacistInterface) registry.lookup("pharmacist");

                // Call RMI method
                String response = pharmacist.requestMedicineRefill(
                        pharmacistID, 
                        medicineName, 
                        quantity
                );

                // Show result output
                gui.setOutputMessage(response);

            } catch (NumberFormatException ex) {
                gui.setOutputMessage("Invalid input. Please enter valid numbers.");
            } catch (RemoteException | NotBoundException ex) {
                gui.setOutputMessage("Connection error: " + ex.getMessage());
            }
        }
    }
}
