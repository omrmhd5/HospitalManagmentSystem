package controllers;

import gui.RecordTestResult;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmi.LabTechnicianInterface;

public class RecordTestResultController {
    
    private final RecordTestResult gui;
    private final Registry registry;
    
    public RecordTestResultController(RecordTestResult gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;
        gui.setVisible(true);
        
        gui.getBtnLoadTest().addActionListener(new LoadTestAction());
        gui.getBtnSubmit().addActionListener(new SubmitResultAction());
        gui.getBtnClose().addActionListener(new CloseAction());
        
        // Load pending tests into combo box
        loadPendingTests();
    }
    
    /**
     * Load all pending lab tests into the combo box
     */
    private void loadPendingTests() {
        try {
            LabTechnicianInterface service = (LabTechnicianInterface) registry.lookup("labtech");
            java.util.List<String> pendingTests = service.getPendingLabTests();
            
            gui.getCmbTestID().removeAllItems();
            gui.getCmbTestID().addItem("Select Test");
            
            for (String test : pendingTests) {
                gui.getCmbTestID().addItem(test);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(RecordTestResultController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(gui, 
                "Error loading pending tests: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    class LoadTestAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String selected = (String) gui.getCmbTestID().getSelectedItem();
                if (selected == null || selected.equals("Select Test")) {
                    // Try to get from editable field
                    String testIDInput = gui.getCmbTestID().getEditor().getItem().toString().trim();
                    if (testIDInput.isEmpty()) {
                        JOptionPane.showMessageDialog(gui, 
                            "Please select or enter a Test ID", 
                            "Validation Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    int testID;
                    try {
                        // Extract ID from "ID - Type - Patient" format or just ID
                        if (testIDInput.contains(" - ")) {
                            testID = Integer.parseInt(testIDInput.split(" - ")[0]);
                        } else {
                            testID = Integer.parseInt(testIDInput);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(gui, 
                            "Invalid Test ID format", 
                            "Validation Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    loadTestDetails(testID);
                } else {
                    // Extract ID from "ID - Type - Patient" format
                    int testID = Integer.parseInt(selected.split(" - ")[0]);
                    loadTestDetails(testID);
                }
            } catch (Exception ex) {
                Logger.getLogger(RecordTestResultController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, 
                    "Error loading test details: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Load test details from database
     */
    private void loadTestDetails(int testID) {
        try {
            LabTechnicianInterface service = (LabTechnicianInterface) registry.lookup("labtech");
            String testDetails = service.getLabTestDetails(testID);
            
            if (testDetails != null && !testDetails.equals("Lab test not found")) {
                gui.getTxtTestDetails().setText(testDetails);
                gui.getTxtTestDetails().setCaretPosition(0);
            } else {
                JOptionPane.showMessageDialog(gui, 
                    "Test not found with ID: " + testID, 
                    "Not Found", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(RecordTestResultController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(gui, 
                "Error loading test details: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    class SubmitResultAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Validate test ID
                String selected = (String) gui.getCmbTestID().getSelectedItem();
                int testID;
                
                if (selected == null || selected.equals("Select Test")) {
                    String testIDInput = gui.getCmbTestID().getEditor().getItem().toString().trim();
                    if (testIDInput.isEmpty()) {
                        JOptionPane.showMessageDialog(gui, 
                            "Please select or enter a Test ID", 
                            "Validation Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    try {
                        if (testIDInput.contains(" - ")) {
                            testID = Integer.parseInt(testIDInput.split(" - ")[0]);
                        } else {
                            testID = Integer.parseInt(testIDInput);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(gui, 
                            "Invalid Test ID format", 
                            "Validation Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    testID = Integer.parseInt(selected.split(" - ")[0]);
                }
                
                // Validate result
                String result = gui.getTxtResult().getText().trim();
                if (result.isEmpty()) {
                    JOptionPane.showMessageDialog(gui, 
                        "Please enter the test result", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    gui.getTxtResult().requestFocus();
                    return;
                }
                
                LabTechnicianInterface service = (LabTechnicianInterface) registry.lookup("labtech");
                String response = service.recordLabTestResult(testID, result);
                
                if (response.contains("successfully") || response.contains("saved")) {
                    JOptionPane.showMessageDialog(gui, 
                        response, 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Clear form and reload pending tests
                    gui.getTxtTestDetails().setText("");
                    gui.getTxtResult().setText("");
                    loadPendingTests();
                } else {
                    JOptionPane.showMessageDialog(gui, 
                        response, 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(RecordTestResultController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, 
                    "Error submitting result: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                Logger.getLogger(RecordTestResultController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, 
                    "An error occurred: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    class CloseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.dispose();
        }
    }
}
