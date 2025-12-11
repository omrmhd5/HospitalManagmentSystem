package controllers;

import gui.ManageUsers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import rmi.AdminInterface;

/**
 * Controller for ManageUsers GUI
 * Follows the same pattern as other controllers
 * @author salma
 */
public class ManageUsersController {
    
    // References to GUI and RMI registry
    ManageUsers gui;
    Registry r;
    DefaultTableModel tableModel;
    
    // Constructor takes GUI and RMI registry as parameters
    public ManageUsersController(ManageUsers gui, Registry r) {
        this.gui = gui;
        this.r = r;
        
        // Initialize table model
        initializeTable();
        
        // Load users from server
        loadAllUsers();
        
        // Register the Add button with our action listener
        gui.getAddButton().addActionListener(new AddButtonAction());
        
        // Register table row click for delete functionality
        gui.getUsersTable().addMouseListener(new TableMouseAdapter());
    }
    
    // Initialize the table model
    private void initializeTable() {
        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Full Name", "Email", "Role", "Delete"}, 
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only the Delete column (index 4) is clickable
                return column == 4;
            }
        };
        gui.getUsersTable().setModel(tableModel);
    }
    
    // Load all users from server and populate table
    private void loadAllUsers() {
        try {
            // Lookup the admin remote object
            AdminInterface admin = (AdminInterface) r.lookup("admin");
            
            // Get all users from server (returns List<Object[]>)
            // Each Object[] contains: [userID, fullName, email, role]
            List<Object[]> users = admin.getAllUsers();
            
            // Clear existing rows
            tableModel.setRowCount(0);
            
            // Add users to table
            for (Object[] user : users) {
                tableModel.addRow(new Object[]{
                    user[0],  // userID
                    user[1],  // fullName
                    user[2],  // email
                    user[3],  // role
                    "✖"      // Delete button symbol
                });
            }
            
            System.out.println("Loaded " + users.size() + " users from server");
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ManageUsersController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(gui, 
                "Error loading users: " + ex.getMessage(), 
                "Connection Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Action listener for Add button
    class AddButtonAction implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            addUser();
        }
    }
    
    // Method to add a new user
    private void addUser() {
        try {
            // Get data from text fields
            String fullName = gui.getFullNameField().getText().trim();
            String email = gui.getEmailField().getText().trim();
            // Mahmoud
            String role = (String) gui.getRoleComboBox().getSelectedItem();
            
            // Validate required fields
            if (fullName.isEmpty() || email.isEmpty() || role == null || role.isEmpty()) {
                JOptionPane.showMessageDialog(gui, 
                    "All fields are required!", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Lookup admin remote object and add user (ID will be assigned by server)
            AdminInterface admin = (AdminInterface) r.lookup("admin");
            boolean success = admin.addUser(fullName, email, role);
            
            if (success) {
                JOptionPane.showMessageDialog(gui, 
                    "User added successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Reload the table
                loadAllUsers();
                
                // Clear input fields
                gui.getFullNameField().setText("");
                gui.getEmailField().setText("");
                // Mahmoud
                gui.getRoleComboBox().setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(gui, 
                    "Failed to add user.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ManageUsersController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(gui, 
                "Error: " + ex.getMessage(), 
                "Connection Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Mouse adapter for table clicks (delete functionality)
    class TableMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int row = gui.getUsersTable().rowAtPoint(e.getPoint());
            int column = gui.getUsersTable().columnAtPoint(e.getPoint());
            
            // Check if Delete column (index 4) was clicked
            if (column == 4 && row >= 0) {
                deleteUser(row);
            }
        }
    }
    
    // Method to delete a user
    private void deleteUser(int row) {
        try {
            // Get user ID from the table
            int userID = (int) tableModel.getValueAt(row, 0);
            String fullName = (String) tableModel.getValueAt(row, 1);
            
            // Confirm deletion
            int confirm = JOptionPane.showConfirmDialog(gui, 
                "Are you sure you want to delete user: " + fullName + "?", 
                "Confirm Deletion", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Lookup admin remote object and delete user
                AdminInterface admin = (AdminInterface) r.lookup("admin");
                boolean success = admin.deleteUser(userID);
                
                if (success) {
                    JOptionPane.showMessageDialog(gui, 
                        "User deleted successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Reload the table
                    loadAllUsers();
                } else {
                    JOptionPane.showMessageDialog(gui, 
                        "Failed to delete user.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ManageUsersController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(gui, 
                "Error: " + ex.getMessage(), 
                "Connection Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

