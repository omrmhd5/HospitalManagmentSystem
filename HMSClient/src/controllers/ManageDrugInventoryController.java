package controllers;

import gui.ManageDrugInventory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmi.PharmacyInterface;

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
                PharmacyInterface service =
                        (PharmacyInterface) registry.lookup("pharmacy");

                // Pass individual fields (no object!)
                int drugID = Integer.parseInt(gui.getDrugIDField().getText());
                String name = gui.getNameField().getText();
                String category = gui.getCategoryField().getText();
                int quantity = Integer.parseInt(gui.getQuantityField().getText());
                int reorderLevel = Integer.parseInt(gui.getReorderField().getText());
                String expiryDate = gui.getExpiryField().getText();

                boolean ok = service.addDrug(drugID, name, category, quantity, reorderLevel, expiryDate);
                JOptionPane.showMessageDialog(gui, ok ? "Drug added successfully!" : "Failed to add drug.");

            } catch (Exception ex) {
                Logger.getLogger(ManageDrugInventoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class ReceiveStockAction implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            try {
                PharmacyInterface service =
                        (PharmacyInterface) registry.lookup("pharmacy");

                int id = Integer.parseInt(gui.getDrugIDField().getText());
                int amount = Integer.parseInt(gui.getAmountField().getText());

                boolean ok = service.receiveDrugStock(id, amount);
                JOptionPane.showMessageDialog(gui, ok ? "Stock received!" : "Failed to receive stock.");

            } catch (Exception ex) {
                Logger.getLogger(ManageDrugInventoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class DispenseStockAction implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            try {
                PharmacyInterface service =
                        (PharmacyInterface) registry.lookup("pharmacy");

                int id = Integer.parseInt(gui.getDrugIDField().getText());
                int amount = Integer.parseInt(gui.getAmountField().getText());

                boolean ok = service.dispenseDrug(id, amount);
                JOptionPane.showMessageDialog(gui, ok ? "Drug dispensed!" : "Not enough stock.");

            } catch (Exception ex) {
                Logger.getLogger(ManageDrugInventoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class UpdateDrugAction implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) {
            try {
                PharmacyInterface service =
                        (PharmacyInterface) registry.lookup("pharmacy");

                // Pass individual fields (no object!)
                int drugID = Integer.parseInt(gui.getDrugIDField().getText());
                String name = gui.getNameField().getText();
                String category = gui.getCategoryField().getText();
                int quantity = Integer.parseInt(gui.getQuantityField().getText());
                int reorderLevel = Integer.parseInt(gui.getReorderField().getText());
                String expiryDate = gui.getExpiryField().getText();

                boolean ok = service.updateDrug(drugID, name, category, quantity, reorderLevel, expiryDate);
                JOptionPane.showMessageDialog(gui, ok ? "Drug updated!" : "Update failed.");

            } catch (Exception ex) {
                Logger.getLogger(ManageDrugInventoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
