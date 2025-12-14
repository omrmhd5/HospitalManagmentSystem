package controllers;

import gui.GenerateReport;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmi.AdminInterface;

public class GenerateReportController {
    
    private final GenerateReport gui;
    private final Registry registry;
    
    public GenerateReportController(GenerateReport gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;
        gui.setVisible(true);
        
        gui.getBtnGenerate().addActionListener(new GenerateAction());
        gui.getBtnClose().addActionListener(new CloseAction());
    }
    
    class GenerateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String reportType = (String) gui.getCmbReportType().getSelectedItem();
                
                if (reportType == null || reportType.equals("Select Report Type")) {
                    JOptionPane.showMessageDialog(gui, 
                        "Please select a report type", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                AdminInterface service = (AdminInterface) registry.lookup("admin");
                String report = service.generateReport(reportType);
                
                gui.getTxtReport().setText(report);
                gui.getTxtReport().setCaretPosition(0); // Scroll to top
                
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(GenerateReportController.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(gui, 
                    "Error generating report: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                Logger.getLogger(GenerateReportController.class.getName()).log(Level.SEVERE, null, ex);
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



