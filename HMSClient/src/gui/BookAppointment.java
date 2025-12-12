package gui;

import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class BookAppointment extends javax.swing.JFrame {
    
    private String patientName;
    private String patientEmail;
    private boolean isReceptionistMode;
    
    // Mahmoud
    public BookAppointment() {
        this("Guest", "guest@example.com", false);
    }
    
    // Mahmoud
    public BookAppointment(String patientName, String patientEmail) {
        this(patientName, patientEmail, false);
    }
    
    // Receptionist mode constructor
    public BookAppointment(boolean isReceptionistMode) {
        this("", "", isReceptionistMode);
    }
    
    // Mahmoud
    public BookAppointment(String patientName, String patientEmail, boolean isReceptionistMode) {
        this.patientName = patientName;
        this.patientEmail = patientEmail;
        this.isReceptionistMode = isReceptionistMode;
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbDoctor = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        dateChooser = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        cmbTimeSlot = new javax.swing.JComboBox<>();
        btnConfirmBooking = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblPatientName = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmbPatient = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Hospital Management System - Book Appointment");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Book Appointment");

        jLabel2.setText("Select Doctor:");

        cmbDoctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Loading doctors..." }));

        jLabel3.setText("Appointment Date:");

        jLabel4.setText("Time Slot:");

        cmbTimeSlot.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM" }));

        btnConfirmBooking.setText("Confirm Booking");
        btnConfirmBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmBookingActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        lblPatientName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPatientName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPatientName.setText("Patient: John Doe");
        
        jLabel5.setText("Select Patient:");
        
        cmbPatient.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Loading patients..." }));
        
        // Configure patient dropdown based on mode
        if (isReceptionistMode) {
            // Receptionist mode: show dropdown, hide label
            lblPatientName.setVisible(false);
            jLabel5.setVisible(true);
            cmbPatient.setVisible(true);
            cmbPatient.setEnabled(true);
        } else {
            // Patient mode: hide dropdown, show label
            lblPatientName.setVisible(true);
            jLabel5.setVisible(false);
            cmbPatient.setVisible(false);
            cmbPatient.setEnabled(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(lblPatientName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPatient, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDoctor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTimeSlot, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConfirmBooking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(lblPatientName)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTimeSlot, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnConfirmBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmBookingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnConfirmBookingActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed

    public JComboBox<String> getCmbDoctor() { return cmbDoctor; }
    public JDateChooser getDateChooser() { return dateChooser; }
    public JComboBox<String> getCmbTimeSlot() { return cmbTimeSlot; }
    public JComboBox<String> getCmbPatient() { return cmbPatient; }
    public JButton getBtnConfirmBooking() { return btnConfirmBooking; }
    public JButton getBtnCancel() { return btnCancel; }
    public JLabel getLblPatientName() { return lblPatientName; }
    public String getPatientName() { return patientName; }
    public String getPatientEmail() { return patientEmail; }
    public boolean isReceptionistMode() { return isReceptionistMode; }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new BookAppointment("John Doe", "john@example.com"));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnConfirmBooking;
    private javax.swing.JComboBox<String> cmbDoctor;
    private javax.swing.JComboBox<String> cmbPatient;
    private javax.swing.JComboBox<String> cmbTimeSlot;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblPatientName;
    // End of variables declaration//GEN-END:variables
}
