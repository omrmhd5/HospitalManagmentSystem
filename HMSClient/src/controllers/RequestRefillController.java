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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import rmi.DoctorRequestInterface;
import rmi.PharmacyInterface;
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

        // Populate dropdowns
        populateDropdowns();
        
        // When button is clicked -> send refill request
        gui.getBtnSendRequest().addActionListener(new SendRefillAction());
    }
    
    private void populateDropdowns() {
        try {
            PharmacyInterface pharmacy = (PharmacyInterface) registry.lookup("pharmacy");
            
            // Populate pharmacists dropdown
            List<String> pharmacists = pharmacy.getAllPharmacists();
            if (pharmacists != null && !pharmacists.isEmpty()) {
                gui.getCmbPharmacist().setModel(new DefaultComboBoxModel<>(pharmacists.toArray(new String[0])));
            } else {
                gui.getCmbPharmacist().setModel(new DefaultComboBoxModel<>(new String[]{"No pharmacists available"}));
            }
            
            // Populate medicines dropdown
            List<String> drugs = pharmacy.getAllDrugs();
            if (drugs != null && !drugs.isEmpty()) {
                gui.getCmbMedicine().setModel(new DefaultComboBoxModel<>(drugs.toArray(new String[0])));
            } else {
                gui.getCmbMedicine().setModel(new DefaultComboBoxModel<>(new String[]{"No medicines available"}));
            }
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(RequestRefillController.class.getName())
                  .log(Level.SEVERE, null, ex);
            gui.setOutputMessage("Error loading data: " + ex.getMessage());
            gui.getCmbPharmacist().setModel(new DefaultComboBoxModel<>(new String[]{"Error loading pharmacists"}));
            gui.getCmbMedicine().setModel(new DefaultComboBoxModel<>(new String[]{"Error loading medicines"}));
        }
    }
    
    private int extractID(String selectedItem) {
        if (selectedItem == null || selectedItem.isEmpty() || selectedItem.contains("Error") || selectedItem.contains("No ")) {
            return -1;
        }
        try {
            String[] parts = selectedItem.split(" - ");
            if (parts.length > 0) {
                return Integer.parseInt(parts[0].trim());
            }
        } catch (NumberFormatException e) {
            Logger.getLogger(RequestRefillController.class.getName())
                  .log(Level.WARNING, "Failed to extract ID from: " + selectedItem, e);
        }
        return -1;
    }
    
    private String extractName(String selectedItem) {
        if (selectedItem == null || selectedItem.isEmpty() || selectedItem.contains("Error") || selectedItem.contains("No ")) {
            return "";
        }
        try {
            String[] parts = selectedItem.split(" - ");
            if (parts.length > 1) {
                return parts[1].trim();
            }
        } catch (Exception e) {
            Logger.getLogger(RequestRefillController.class.getName())
                  .log(Level.WARNING, "Failed to extract name from: " + selectedItem, e);
        }
        return "";
    }

    class SendRefillAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String selectedPharmacist = (String) gui.getCmbPharmacist().getSelectedItem();
                String selectedMedicine = (String) gui.getCmbMedicine().getSelectedItem();
                String quantityText = gui.getTxtQuantity().getText().trim();
                
                if (selectedPharmacist == null || selectedPharmacist.contains("Error") || selectedPharmacist.contains("No ")) {
                    gui.setOutputMessage("Please select a valid pharmacist.");
                    return;
                }
                
                if (selectedMedicine == null || selectedMedicine.contains("Error") || selectedMedicine.contains("No ")) {
                    gui.setOutputMessage("Please select a valid medicine.");
                    return;
                }
                
                if (quantityText.isEmpty()) {
                    gui.setOutputMessage("Please enter a quantity.");
                    return;
                }
                
                int pharmacistID = extractID(selectedPharmacist);
                String medicineName = extractName(selectedMedicine);
                
                if (pharmacistID == -1) {
                    gui.setOutputMessage("Invalid pharmacist selection.");
                    return;
                }
                
                if (medicineName.isEmpty()) {
                    gui.setOutputMessage("Invalid medicine selection.");
                    return;
                }
                
                int quantity;
                try {
                    quantity = Integer.parseInt(quantityText);
                    if (quantity <= 0) {
                        gui.setOutputMessage("Quantity must be greater than 0.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    gui.setOutputMessage("Invalid quantity. Please enter a valid number.");
                    return;
                }
                
                // Use Strategy Pattern via DoctorRequestInterface
                DoctorRequestInterface doctorRequestService = (DoctorRequestInterface) registry.lookup("doctorrequest");
                
                // Execute medicine refill request using strategy pattern
                boolean success = doctorRequestService.executeMedicineRefillRequest(
                    gui.getDoctorEmail(),
                    "", // prescriptionID (can be empty for refill requests)
                    medicineName,
                    pharmacistID,
                    quantity
                );
                
                if (success) {
                    gui.setOutputMessage("Medicine refill request submitted successfully!");
                    gui.getTxtQuantity().setText("");
                } else {
                    gui.setOutputMessage("Failed to submit medicine refill request. Please try again.");
                }

            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(RequestRefillController.class.getName())
                      .log(Level.SEVERE, null, ex);
                gui.setOutputMessage("Server error: Unable to connect to server. Please try again later.");
            } catch (Exception ex) {
                Logger.getLogger(RequestRefillController.class.getName())
                      .log(Level.SEVERE, null, ex);
                gui.setOutputMessage("Unexpected error: " + ex.getMessage());
            }
        }
    }
}
