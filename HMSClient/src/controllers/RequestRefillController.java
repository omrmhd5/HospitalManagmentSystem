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
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.PharmacistInterface;
/**
 *
 * @author omarm
 */

//ibrahim
public class RequestRefillController {
    private final RequestRefill gui;
    private final Registry registry;

    public RequestRefillController(RequestRefill gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;

        // When button is clicked -> send refill request
        gui.getBtnSendRequest().addActionListener(new SendRefillAction());
    }

    class SendRefillAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // 1) Lookup pharmacist remote object
                PharmacistInterface ph = (PharmacistInterface) registry.lookup("pharmacist");

                // 2) Read values from GUI
                int pharmacistID = Integer.parseInt(gui.getTxtPharmacistID().getText().trim());
                String medName = gui.getTxtMedicineName().getText().trim();
                int quantity = Integer.parseInt(gui.getTxtQuantity().getText().trim());

                // 3) Call remote method
                String result = ph.requestMedicineRefill(pharmacistID, medName, quantity);

                // 4) Show result to user
                gui.setOutputMessage(result);

            } catch (NumberFormatException ex) {
                gui.setOutputMessage("Please enter valid numbers for ID and quantity.");
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(RequestRefillController.class.getName())
                      .log(Level.SEVERE, null, ex);
                gui.setOutputMessage("Server error: " + ex.getMessage());
            }
        }
    }
}
