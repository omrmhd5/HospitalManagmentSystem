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
        this.setVisible(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblWelcome = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnViewAppointments = new javax.swing.JButton();
        btnRecordDiagnosis = new javax.swing.JButton();
        btnRecordPrescription = new javax.swing.JButton();
        btnViewPatientRecords = new javax.swing.JButton();
        btnRequestLabTest = new javax.swing.JButton();
        btnRequestRefill = new javax.swing.JButton();
        btnRequestICU = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Hospital Management System - Doctor Dashboard");

        jPanel1.setBackground(new java.awt.Color(0, 0, 153));

        lblWelcome.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(255, 255, 255));
        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWelcome.setText("Welcome, Doctor");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblWelcome)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Please select an option:");

        btnViewAppointments.setBackground(new java.awt.Color(0, 0, 153));
        btnViewAppointments.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnViewAppointments.setForeground(new java.awt.Color(255, 255, 255));
        btnViewAppointments.setText("View Appointments");
        btnViewAppointments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewAppointmentsActionPerformed(evt);
            }
        });

        btnRecordDiagnosis.setBackground(new java.awt.Color(0, 0, 153));
        btnRecordDiagnosis.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRecordDiagnosis.setForeground(new java.awt.Color(255, 255, 255));
        btnRecordDiagnosis.setText("Record Diagnosis");
        btnRecordDiagnosis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecordDiagnosisActionPerformed(evt);
            }
        });

        btnRecordPrescription.setBackground(new java.awt.Color(0, 0, 153));
        btnRecordPrescription.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRecordPrescription.setForeground(new java.awt.Color(255, 255, 255));
        btnRecordPrescription.setText("Record Prescription");
        btnRecordPrescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecordPrescriptionActionPerformed(evt);
            }
        });

        btnViewPatientRecords.setBackground(new java.awt.Color(0, 0, 153));
        btnViewPatientRecords.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnViewPatientRecords.setForeground(new java.awt.Color(255, 255, 255));
        btnViewPatientRecords.setText("View Patient Records");
        btnViewPatientRecords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewPatientRecordsActionPerformed(evt);
            }
        });

        btnRequestLabTest.setBackground(new java.awt.Color(0, 0, 153));
        btnRequestLabTest.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRequestLabTest.setForeground(new java.awt.Color(255, 255, 255));
        btnRequestLabTest.setText("Request Lab Test");
        btnRequestLabTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRequestLabTestActionPerformed(evt);
            }
        });

        btnRequestRefill.setBackground(new java.awt.Color(0, 0, 153));
        btnRequestRefill.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRequestRefill.setForeground(new java.awt.Color(255, 255, 255));
        btnRequestRefill.setText("Request Refill");
        btnRequestRefill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRequestRefillActionPerformed(evt);
            }
        });

        btnRequestICU.setBackground(new java.awt.Color(0, 0, 153));
        btnRequestICU.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRequestICU.setForeground(new java.awt.Color(255, 255, 255));
        btnRequestICU.setText("Request ICU");
        btnRequestICU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRequestICUActionPerformed(evt);
            }
        });

        btnLogout.setBackground(new java.awt.Color(0, 0, 153));
        btnLogout.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(btnViewAppointments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRecordDiagnosis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRecordPrescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnViewPatientRecords, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRequestLabTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRequestRefill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRequestICU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
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
                .addGap(40, 40, 40)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        
        // Set consistent window size and center
        setSize(600, 800);
        setLocationRelativeTo(null);
        
        // Set background color for content pane (light blue)
        getContentPane().setBackground(new java.awt.Color(153, 204, 255));
        
        // Set title label properties
        lblWelcome.setBackground(new java.awt.Color(0, 0, 153));
        lblWelcome.setForeground(new java.awt.Color(255, 255, 255));
        lblWelcome.setOpaque(true);
        lblWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblWelcome.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 15, 20));
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblWelcome;
    // End of variables declaration//GEN-END:variables
}

