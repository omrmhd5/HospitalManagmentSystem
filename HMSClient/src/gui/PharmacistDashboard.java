package gui;

import javax.swing.JButton;

public class PharmacistDashboard extends javax.swing.JFrame {
    
    private String pharmacistName;
    private String pharmacistEmail;
    
    public PharmacistDashboard(String pharmacistName, String pharmacistEmail) {
        this.pharmacistName = pharmacistName;
        this.pharmacistEmail = pharmacistEmail;
        initComponents();
        lblWelcome.setText("Welcome, " + pharmacistName);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblWelcome = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnManageDrugInventory = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hospital Management System - Pharmacist Dashboard");

        lblWelcome.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWelcome.setText("Welcome, Pharmacist");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Please select an option:");

        btnManageDrugInventory.setText("Manage Drug Inventory");
        btnManageDrugInventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageDrugInventoryActionPerformed(evt);
            }
        });

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblWelcome, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnManageDrugInventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblWelcome)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addComponent(btnManageDrugInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnManageDrugInventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageDrugInventoryActionPerformed
    }//GEN-LAST:event_btnManageDrugInventoryActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
    }//GEN-LAST:event_btnLogoutActionPerformed

    public JButton getBtnManageDrugInventory() { return btnManageDrugInventory; }
    public JButton getBtnLogout() { return btnLogout; }
    public String getPharmacistName() { return pharmacistName; }
    public String getPharmacistEmail() { return pharmacistEmail; }
    
    // Update welcome message when name changes
    public void updateWelcomeMessage(String newName) {
        this.pharmacistName = newName;
        lblWelcome.setText("Welcome, " + newName);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new PharmacistDashboard("Pharmacist", "pharmacist@example.com"));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnManageDrugInventory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblWelcome;
    // End of variables declaration//GEN-END:variables
}

