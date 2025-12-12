package controllers;

import gui.HandleICURequests;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import rmi.ICUInterface;

public class HandleICURequestsController {
    
    private final HandleICURequests gui;
    private final Registry registry;
    private final String handlerRole;
    
    public HandleICURequestsController(HandleICURequests gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;
        this.handlerRole = gui.getHandlerRole();
        
        // Set up action listeners
        gui.getBtnRefresh().addActionListener(new RefreshAction());
        gui.getBtnViewDetails().addActionListener(new ViewDetailsAction());
        gui.getBtnApprove().addActionListener(new ApproveAction());
        gui.getBtnReject().addActionListener(new RejectAction());
        gui.getBtnClose().addActionListener(new CloseAction());
        
        // Load initial data
        loadPendingRequests();
    }
    
    private void loadPendingRequests() {
        try {
            ICUInterface icuService = (ICUInterface) registry.lookup("icu");
            List<String> pendingRequests = icuService.getPendingICURequests();
            
            DefaultTableModel model = (DefaultTableModel) gui.getRequestsTable().getModel();
            model.setRowCount(0); // Clear existing rows
            
            for (String requestStr : pendingRequests) {
                String[] parts = requestStr.split("\\|");
                if (parts.length >= 7) {
                    Object[] row = {
                        Integer.parseInt(parts[0]), // Request ID
                        parts[1], // Patient
                        parts[2], // Doctor
                        parts[3], // Date
                        parts[4], // Time
                        parts[5], // Urgency
                        parts[8]  // Status
                    };
                    model.addRow(row);
                }
            }
            
            if (pendingRequests.isEmpty()) {
                JOptionPane.showMessageDialog(gui, "No pending ICU requests found.", 
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(HandleICURequestsController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(gui, "Error loading ICU requests: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private int getSelectedRequestID() {
        int selectedRow = gui.getRequestsTable().getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(gui, "Please select a request first.", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        
        DefaultTableModel model = (DefaultTableModel) gui.getRequestsTable().getModel();
        return (Integer) model.getValueAt(selectedRow, 0);
    }
    
    class RefreshAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadPendingRequests();
        }
    }
    
    class ViewDetailsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int requestID = getSelectedRequestID();
            if (requestID < 0) return;
            
            try {
                ICUInterface icuService = (ICUInterface) registry.lookup("icu");
                String details = icuService.getICURequestDetails(requestID);
                gui.getDetailsArea().setText(details);
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(HandleICURequestsController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, "Error loading request details: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    class ApproveAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int requestID = getSelectedRequestID();
            if (requestID < 0) return;
            
            try {
                ICUInterface icuService = (ICUInterface) registry.lookup("icu");
                
                // Get request details for display
                String details = icuService.getICURequestDetails(requestID);
                
                // Process through Chain of Responsibility on server side
                String result = icuService.processRequestThroughChain(requestID, handlerRole);
                
                // Parse result message
                if (result.startsWith("SUCCESS:")) {
                    // Request was approved
                    JOptionPane.showMessageDialog(gui, 
                        result.substring(8).trim() + "\n\n" + 
                        "Chain of Responsibility: " + handlerRole + " handler processed the request successfully.", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadPendingRequests();
                    gui.getDetailsArea().setText("Request approved by " + handlerRole + ".\n\n" + details);
                    
                } else if (result.startsWith("ESCALATE:")) {
                    // Request needs escalation
                    JOptionPane.showMessageDialog(gui, 
                        result.substring(9).trim() + "\n\n" +
                        "The request will be escalated to the appropriate handler.", 
                        "Escalation Required", JOptionPane.WARNING_MESSAGE);
                    loadPendingRequests();
                    gui.getDetailsArea().setText("Request requires escalation.\n\n" + details);
                    
                } else if (result.startsWith("PROCESSED:")) {
                    // Request was processed
                    JOptionPane.showMessageDialog(gui, 
                        result.substring(10).trim(), 
                        "Processed", JOptionPane.INFORMATION_MESSAGE);
                    loadPendingRequests();
                    gui.getDetailsArea().setText("Request processed.\n\n" + details);
                    
                } else if (result.startsWith("INFO:")) {
                    // Informational message
                    JOptionPane.showMessageDialog(gui, 
                        result.substring(5).trim(), 
                        "Information", JOptionPane.INFORMATION_MESSAGE);
                    loadPendingRequests();
                    
                } else if (result.startsWith("ERROR:")) {
                    // Error occurred
                    JOptionPane.showMessageDialog(gui, 
                        result.substring(6).trim(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(HandleICURequestsController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, "Error processing request: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    class RejectAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int requestID = getSelectedRequestID();
            if (requestID < 0) return;
            
            try {
                ICUInterface icuService = (ICUInterface) registry.lookup("icu");
                
                // Get request details to show urgency
                String details = icuService.getICURequestDetails(requestID);
                
                // First check if this handler can reject (without actually rejecting)
                String checkResult = icuService.canRejectRequest(requestID, handlerRole);
                
                if (checkResult.startsWith("ALLOWED:")) {
                    // Handler has authority to reject - ask for confirmation
                    int confirm = JOptionPane.showConfirmDialog(gui, 
                        "Chain of Responsibility: " + handlerRole + " can reject this request.\n\n" +
                        "Are you sure you want to reject this ICU request?", 
                        "Confirm Rejection", 
                        JOptionPane.YES_NO_OPTION);
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Now actually reject through chain
                        String result = icuService.rejectRequestThroughChain(requestID, handlerRole);
                        
                        if (result.startsWith("SUCCESS:")) {
                            JOptionPane.showMessageDialog(gui, 
                                "ICU Request rejected successfully.", 
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                            loadPendingRequests();
                            gui.getDetailsArea().setText("Request rejected by " + handlerRole + ".\n\n" + details);
                        } else {
                            JOptionPane.showMessageDialog(gui, 
                                result, 
                                "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    
                } else if (checkResult.startsWith("ESCALATE:")) {
                    // Handler cannot reject - needs escalation
                    JOptionPane.showMessageDialog(gui, 
                        checkResult.substring(9).trim() + "\n\n" +
                        "This request must be handled by the appropriate authority.\n" +
                        "Please do not reject it - it will be escalated automatically.", 
                        "Cannot Reject - Escalation Required", JOptionPane.WARNING_MESSAGE);
                    gui.getDetailsArea().setText("Cannot reject: " + checkResult.substring(9).trim() + "\n\n" + details);
                    
                } else if (checkResult.startsWith("INFO:")) {
                    // Informational message
                    JOptionPane.showMessageDialog(gui, 
                        checkResult.substring(5).trim(), 
                        "Information", JOptionPane.INFORMATION_MESSAGE);
                    loadPendingRequests();
                    
                } else if (checkResult.startsWith("ERROR:")) {
                    // Error occurred
                    JOptionPane.showMessageDialog(gui, 
                        checkResult.substring(6).trim(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(HandleICURequestsController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, "Error rejecting request: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
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

