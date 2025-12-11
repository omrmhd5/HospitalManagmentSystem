package gui;

import javax.swing.JButton;

public class DoctorDashboard extends javax.swing.JFrame {
    
    private String doctorName;
    private String doctorEmail;
    
    public DoctorDashboard(String doctorName, String doctorEmail) {
        this.doctorName = doctorName;
        this.doctorEmail = doctorEmail;
        initComponents();
        lblWelcome.setText("Welcome, " + doctorName);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblWelcome = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnViewAppointments = new javax.swing.JButton();
        btnRecordDiagnosis = new javax.swing.JButton();
        btnRecordPrescription = new javax.swing.JButton();
        btnViewPatientRecords = new javax.swing.JButton();
        btnRequestLabTest = new javax.swing.JButton();
        btnRequestRefill = new javax.swing.JButton();
        btnRequestICU = new javax.swing.JButton();
        btnViewProfile = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hospital Management System - Doctor Dashboard");

        lblWelcome.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWelcome.setText("Welcome, Doctor");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Please select an option:");

        btnViewAppointments.setText("View Appointments");
        btnViewAppointments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewAppointmentsActionPerformed(evt);
            }
        });

        btnRecordDiagnosis.setText("Record Diagnosis");
        btnRecordDiagnosis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecordDiagnosisActionPerformed(evt);
            }
        });

        btnRecordPrescription.setText("Record Prescription");
        btnRecordPrescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecordPrescriptionActionPerformed(evt);
            }
        });

        btnViewPatientRecords.setText("View Patient Records");
        btnViewPatientRecords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewPatientRecordsActionPerformed(evt);
            }
        });

        btnRequestLabTest.setText("Request Lab Test");
        btnRequestLabTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRequestLabTestActionPerformed(evt);
            }
        });

        btnRequestRefill.setText("Request Refill");
        btnRequestRefill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRequestRefillActionPerformed(evt);
            }
        });

        btnRequestICU.setText("Request ICU");
        btnRequestICU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRequestICUActionPerformed(evt);
            }
        });

        btnViewProfile.setText("View Profile");
        btnViewProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewProfileActionPerformed(evt);
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
                    .addComponent(btnViewAppointments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRecordDiagnosis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRecordPrescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnViewPatientRecords, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRequestLabTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRequestRefill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRequestICU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnViewProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(btnViewAppointments, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnRecordDiagnosis, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnRecordPrescription, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnViewPatientRecords, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnRequestLabTest, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnRequestRefill, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnRequestICU, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnViewProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewAppointmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewAppointmentsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnViewAppointmentsActionPerformed

    private void btnRecordDiagnosisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecordDiagnosisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRecordDiagnosisActionPerformed

    private void btnRecordPrescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecordPrescriptionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRecordPrescriptionActionPerformed

    private void btnViewPatientRecordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewPatientRecordsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnViewPatientRecordsActionPerformed

    private void btnRequestLabTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRequestLabTestActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRequestLabTestActionPerformed

    private void btnRequestRefillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRequestRefillActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRequestRefillActionPerformed

    private void btnRequestICUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRequestICUActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRequestICUActionPerformed

    private void btnViewProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewProfileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnViewProfileActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLogoutActionPerformed

    public JButton getBtnViewAppointments() { return btnViewAppointments; }
    public JButton getBtnRecordDiagnosis() { return btnRecordDiagnosis; }
    public JButton getBtnRecordPrescription() { return btnRecordPrescription; }
    public JButton getBtnViewPatientRecords() { return btnViewPatientRecords; }
    public JButton getBtnRequestLabTest() { return btnRequestLabTest; }
    public JButton getBtnRequestRefill() { return btnRequestRefill; }
    public JButton getBtnRequestICU() { return btnRequestICU; }
    public JButton getBtnViewProfile() { return btnViewProfile; }
    public JButton getBtnLogout() { return btnLogout; }
    public String getDoctorName() { return doctorName; }
    public String getDoctorEmail() { return doctorEmail; }
    
    // Update welcome message when name changes
    public void updateWelcomeMessage(String newName) {
        this.doctorName = newName;
        lblWelcome.setText("Welcome, " + newName);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new DoctorDashboard("Dr. John Doe", "doctor@example.com"));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnRecordDiagnosis;
    private javax.swing.JButton btnRecordPrescription;
    private javax.swing.JButton btnRequestICU;
    private javax.swing.JButton btnRequestLabTest;
    private javax.swing.JButton btnRequestRefill;
    private javax.swing.JButton btnViewAppointments;
    private javax.swing.JButton btnViewPatientRecords;
    private javax.swing.JButton btnViewProfile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblWelcome;
    // End of variables declaration//GEN-END:variables
}

