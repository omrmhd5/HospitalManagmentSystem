package controllers;

import gui.ManageDrugInventory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Drug;
import rmiServer.AppointmentInterface;

public class ManageDrugInventoryController {

    ManageDrugInventory gui;
    Registry registry;

    public ManageDrugInventoryController(ManageDrugInventory gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;

        gui.getAddButton().addActionListener(new AddDrugAction());
        gui.getReceiveButton().addActionListener(new ReceiveStockAction());
        gui.getDispenseButton().addActionListener(new DispenseStockAction());
        gui.getUpdateButton().addActionListener(new UpdateDrugAction());
    }

    class AddDrugAction implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            try {
                AppointmentInterface service =
                        (AppointmentInterface) registry.lookup("AppointmentService");

                Drug d = new Drug(
                    Integer.parseInt(gui.getDrugIDField().getText()),
                    gui.getNameField().getText(),
                    gui.getCategoryField().getText(),
                    Integer.parseInt(gui.getQuantityField().getText()),
                    Integer.parseInt(gui.getReorderField().getText()),
                    gui.getExpiryField().getText()
                );

                boolean ok = service.addDrug(d);
                gui.setOutput(ok ? "Drug added successfully!" : "Failed to add drug.");

            } catch (Exception ex) {
                Logger.getLogger(ManageDrugInventoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class ReceiveStockAction implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            try {
                AppointmentInterface service =
                        (AppointmentInterface) registry.lookup("AppointmentService");

                int id = Integer.parseInt(gui.getDrugIDField().getText());
                int amount = Integer.parseInt(gui.getAmountField().getText());

                boolean ok = service.receiveDrugStock(id, amount);
                gui.setOutput(ok ? "Stock received!" : "Failed to receive stock.");

            } catch (Exception ex) {
                Logger.getLogger(ManageDrugInventoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class DispenseStockAction implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            try {
                AppointmentInterface service =
                        (AppointmentInterface) registry.lookup("AppointmentService");

                int id = Integer.parseInt(gui.getDrugIDField().getText());
                int amount = Integer.parseInt(gui.getAmountField().getText());

                boolean ok = service.dispenseDrug(id, amount);
                gui.setOutput(ok ? "Drug dispensed!" : "Not enough stock.");

            } catch (Exception ex) {
                Logger.getLogger(ManageDrugInventoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class UpdateDrugAction implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            try {
                AppointmentInterface service =
                        (AppointmentInterface) registry.lookup("AppointmentService");

                Drug d = new Drug(
                    Integer.parseInt(gui.getDrugIDField().getText()),
                    gui.getNameField().getText(),
                    gui.getCategoryField().getText(),
                    Integer.parseInt(gui.getQuantityField().getText()),
                    Integer.parseInt(gui.getReorderField().getText()),
                    gui.getExpiryField().getText()
                );

                boolean ok = service.updateDrug(d);
                gui.setOutput(ok ? "Drug updated!" : "Update failed.");

            } catch (Exception ex) {
                Logger.getLogger(ManageDrugInventoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
