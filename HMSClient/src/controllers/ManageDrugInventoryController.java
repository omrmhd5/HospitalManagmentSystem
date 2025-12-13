package controllers;

import gui.ManageDrugInventory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
        gui.setVisible(true);

        gui.getAddButton().addActionListener(new AddDrugAction());
        gui.getUpdateButton().addActionListener(new UpdateDrugAction());
        gui.getDeleteButton().addActionListener(new DeleteDrugAction());
        
       
        loadDrugs();
        
       
        loadCategories();
        
      
        gui.getDrugIDField().addActionListener(new DrugIDActionListener());
    }
    
    
    private void loadCategories() {
        String[] categories = {
            "Select Category",
            "Antibiotics",
            "Analgesics",
            "Antipyretics",
            "Antihistamines",
            "Vitamins",
            "Cardiovascular",
            "Respiratory",
            "Gastrointestinal",
            "Neurological",
            "Dermatological",
            "Antifungal",
            "Antiviral",
            "Hormonal",
            "Immunosuppressants",
            "Other"
        };
        
        gui.getCategoryField().removeAllItems();
        for (String category : categories) {
            gui.getCategoryField().addItem(category);
        }
    }
    
    
    private void loadDrugs() {
        try {
            PharmacyInterface service = (PharmacyInterface) registry.lookup("pharmacy");
            java.util.List<String> drugs = service.getAllDrugs();
            
            gui.getDrugIDField().removeAllItems();
            gui.getDrugIDField().addItem("Select Drug");
            
            for (String drug : drugs) {
                gui.getDrugIDField().addItem(drug);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ManageDrugInventoryController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(gui, 
                "Error loading drugs: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    class DrugIDActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selected = (String) gui.getDrugIDField().getSelectedItem();
            if (selected != null && !selected.equals("Select Drug")) {
                try {
                   
                    int drugID = Integer.parseInt(selected.split(" - ")[0]);
                    loadDrugInfo(drugID);
                } catch (Exception ex) {
                    
                }
            }
        }
    }
    
    
    private void loadDrugInfo(int drugID) {
        try {
            PharmacyInterface service = (PharmacyInterface) registry.lookup("pharmacy");
            String drugInfo = service.getDrugByID(drugID);
            
            if (drugInfo != null && !drugInfo.equals("Drug not found")) {
                
                parseAndFillDrugInfo(drugInfo);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ManageDrugInventoryController.class.getName()).log(Level.SEVERE, null, ex);
           
        }
    }
    
   
    private void parseAndFillDrugInfo(String drugInfo) {
        try {
            String[] lines = drugInfo.split("\n");
            for (String line : lines) {
                if (line.contains("Name:")) {
                    String name = line.substring(line.indexOf(":") + 1).trim();
                    gui.getNameField().setText(name);
                } else if (line.contains("Category:")) {
                    String category = line.substring(line.indexOf(":") + 1).trim();
                  
                    javax.swing.DefaultComboBoxModel<String> model = (javax.swing.DefaultComboBoxModel<String>) gui.getCategoryField().getModel();
                    boolean found = false;
                    for (int i = 0; i < model.getSize(); i++) {
                        if (model.getElementAt(i).equals(category)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found && !category.isEmpty()) {
                       
                        model.insertElementAt(category, 1); 
                    }
                    gui.getCategoryField().setSelectedItem(category);
                } else if (line.contains("Quantity:")) {
                    String quantity = line.substring(line.indexOf(":") + 1).trim();
                    gui.getQuantityField().setText(quantity);
                } else if (line.contains("Reorder Level:")) {
                    String reorderLevel = line.substring(line.indexOf(":") + 1).trim();
                    gui.getReorderField().setText(reorderLevel);
                } else if (line.contains("Expiry Date:")) {
                    String expiryDate = line.substring(line.indexOf(":") + 1).trim();
                    try {
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date date = sdf.parse(expiryDate);
                        gui.getExpiryField().setDate(date);
                    } catch (Exception ex) {
                        System.out.println("Error");
                    }
                }
            }
        } catch (Exception ex) {
             System.out.println("Error");
        }
    }

    class AddDrugAction implements ActionListener {
        @Override 
        public void actionPerformed(ActionEvent e) {
            try {
                
                if (!validateDrugFields(true)) {
                    return;
                }

                PharmacyInterface service = (PharmacyInterface) registry.lookup("pharmacy");

               
                int drugID;
                try {
                    String drugIDInput = gui.getDrugIDField().getEditor().getItem().toString().trim();
                    drugID = Integer.parseInt(drugIDInput);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(gui, 
                        "Please enter a valid Drug ID", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String name = gui.getNameField().getText().trim();
                String category = (String) gui.getCategoryField().getSelectedItem();
                int quantity = Integer.parseInt(gui.getQuantityField().getText().trim());
                int reorderLevel = Integer.parseInt(gui.getReorderField().getText().trim());
                String expiryDate = formatDate(gui.getExpiryField().getDate());

                
                if (quantity < 0) {
                    JOptionPane.showMessageDialog(gui, 
                        "Quantity cannot be negative", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (reorderLevel < 0) {
                    JOptionPane.showMessageDialog(gui, 
                        "Reorder level cannot be negative", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean ok = service.addDrug(drugID, name, category, quantity, reorderLevel, expiryDate);
                
                if (ok) {
                    JOptionPane.showMessageDialog(gui, 
                        "Drug added successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(gui, 
                        "Failed to add drug. Drug ID may already exist.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gui, 
                    "Please enter valid numeric values for Drug ID, Quantity, and Reorder Level", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(ManageDrugInventoryController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, 
                    "Error communicating with server: " + ex.getMessage(), 
                    "Connection Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                Logger.getLogger(ManageDrugInventoryController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, 
                    "An error occurred: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class DeleteDrugAction implements ActionListener {
        @Override 
        public void actionPerformed(ActionEvent e) {
            try {
                
                String selected = (String) gui.getDrugIDField().getSelectedItem();
                if (selected == null || selected.equals("Select Drug")) {
                    JOptionPane.showMessageDialog(gui, 
                        "Please select a drug to delete", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    gui.getDrugIDField().requestFocus();
                    return;
                }
                
                int drugID;
                try {
                    
                    drugID = Integer.parseInt(selected.split(" - ")[0]);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(gui, 
                        "Invalid drug selection", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    gui.getDrugIDField().requestFocus();
                    return;
                }
                
                
                int confirm = JOptionPane.showConfirmDialog(gui, 
                    "Are you sure you want to delete drug with ID: " + drugID + "?", 
                    "Confirm Deletion", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                PharmacyInterface service = (PharmacyInterface) registry.lookup("pharmacy");
                boolean ok = service.deleteDrug(drugID);
                
                if (ok) {
                    JOptionPane.showMessageDialog(gui, 
                        "Drug deleted successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(gui, 
                        "Failed to delete drug. Drug may not exist.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }

            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(ManageDrugInventoryController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, 
                    "Error communicating with server: " + ex.getMessage(), 
                    "Connection Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                Logger.getLogger(ManageDrugInventoryController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, 
                    "An error occurred: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class UpdateDrugAction implements ActionListener {
        @Override 
        public void actionPerformed(ActionEvent e) {
            try {
               
                if (!validateDrugFields(false)) {
                    return;
                }

                PharmacyInterface service = (PharmacyInterface) registry.lookup("pharmacy");

               
                int drugID = getSelectedDrugID();
                if (drugID == -1) {
                    JOptionPane.showMessageDialog(gui, 
                        "Please select a drug to update", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String name = gui.getNameField().getText().trim();
                String category = (String) gui.getCategoryField().getSelectedItem();
                int quantity = Integer.parseInt(gui.getQuantityField().getText().trim());
                int reorderLevel = Integer.parseInt(gui.getReorderField().getText().trim());
                String expiryDate = formatDate(gui.getExpiryField().getDate());

                
                if (quantity < 0) {
                    JOptionPane.showMessageDialog(gui, 
                        "Quantity cannot be negative", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (reorderLevel < 0) {
                    JOptionPane.showMessageDialog(gui, 
                        "Reorder level cannot be negative", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean ok = service.updateDrug(drugID, name, category, quantity, reorderLevel, expiryDate);
                
                if (ok) {
                    JOptionPane.showMessageDialog(gui, 
                        "Drug updated successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(gui, 
                        "Failed to update drug. Drug may not exist.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gui, 
                    "Please enter valid numeric values for Drug ID, Quantity, and Reorder Level", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(ManageDrugInventoryController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, 
                    "Error communicating with server: " + ex.getMessage(), 
                    "Connection Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                Logger.getLogger(ManageDrugInventoryController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, 
                    "An error occurred: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    private boolean validateDrugFields(boolean isAdd) {
        
        String selected = (String) gui.getDrugIDField().getSelectedItem();
        if (selected == null || selected.equals("Select Drug")) {
    if (!isAdd) {
        JOptionPane.showMessageDialog(
            gui,
            "Please select a drug",
            "Validation Error",
            JOptionPane.ERROR_MESSAGE
        );
        gui.getDrugIDField().requestFocus();
        return false;
    }
}


        
        String name = gui.getNameField().getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(gui, 
                "Please enter a drug name", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            gui.getNameField().requestFocus();
            return false;
        }

        
        String category = (String) gui.getCategoryField().getSelectedItem();
        if (category == null || category.isEmpty() || category.equals("Select Category") || category.trim().isEmpty()) {
            JOptionPane.showMessageDialog(gui, 
                "Please select a category", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            gui.getCategoryField().requestFocus();
            return false;
        }

      
        String quantityText = gui.getQuantityField().getText().trim();
        if (quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(gui, 
                "Please enter a quantity", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            gui.getQuantityField().requestFocus();
            return false;
        }
        
        try {
            int quantity = Integer.parseInt(quantityText);
            if (quantity < 0) {
                JOptionPane.showMessageDialog(gui, 
                    "Quantity cannot be negative", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                gui.getQuantityField().requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(gui, 
                "Quantity must be a valid number", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            gui.getQuantityField().requestFocus();
            return false;
        }

      
        String reorderText = gui.getReorderField().getText().trim();
        if (reorderText.isEmpty()) {
            JOptionPane.showMessageDialog(gui, 
                "Please enter a reorder level", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            gui.getReorderField().requestFocus();
            return false;
        }
        
        try {
            int reorderLevel = Integer.parseInt(reorderText);
            if (reorderLevel < 0) {
                JOptionPane.showMessageDialog(gui, 
                    "Reorder level cannot be negative", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                gui.getReorderField().requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(gui, 
                "Reorder level must be a valid number", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            gui.getReorderField().requestFocus();
            return false;
        }

      
        java.util.Date expiryDate = gui.getExpiryField().getDate();
        if (expiryDate == null) {
            JOptionPane.showMessageDialog(gui, 
                "Please select an expiry date", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            gui.getExpiryField().requestFocus();
            return false;
        }

        return true;
    }
    
  
    private void clearForm() {
        gui.getDrugIDField().setSelectedIndex(0);
        gui.getNameField().setText("");
        gui.getCategoryField().setSelectedIndex(0); // "Select Category"
        gui.getQuantityField().setText("");
        gui.getReorderField().setText("");
        gui.getExpiryField().setDate(null);
        gui.getDrugIDField().requestFocus();
    }
    
    
    private String formatDate(java.util.Date date) {
        if (date == null) {
            return "";
        }
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    
    
    private int getSelectedDrugID() {
        String selected = (String) gui.getDrugIDField().getSelectedItem();
        if (selected != null && !selected.equals("Select Drug")) {
            try {
                return Integer.parseInt(selected.split(" - ")[0]);
            } catch (Exception ex) {
                return -1;
            }
        }
        return -1;
    }
}
