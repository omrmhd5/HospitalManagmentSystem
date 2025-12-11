package controllers;

import controllers.loginRegister.RoleSelectController;
import gui.DoctorDashboard;
import gui.loginRegister.RoleSelect;
import gui.ViewAppointment;
import gui.RecordDiagnosis;
import gui.RecordPrescription;
import gui.ViewPatientRecord;
import gui.RequestLabTest;
import gui.RequestRefill;
import gui.RequestICU;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

public class DoctorDashboardController {
    
    private final DoctorDashboard gui;
    private final Registry registry;
    
    public DoctorDashboardController(DoctorDashboard gui, Registry registry) {
        this.gui = gui;
        this.registry = registry;
        
        gui.getBtnViewAppointments().addActionListener(new ViewAppointmentsAction());
        gui.getBtnRecordDiagnosis().addActionListener(new RecordDiagnosisAction());
        gui.getBtnRecordPrescription().addActionListener(new RecordPrescriptionAction());
        gui.getBtnViewPatientRecords().addActionListener(new ViewPatientRecordsAction());
        gui.getBtnRequestLabTest().addActionListener(new RequestLabTestAction());
        gui.getBtnRequestRefill().addActionListener(new RequestRefillAction());
        gui.getBtnRequestICU().addActionListener(new RequestICUAction());
        gui.getBtnViewProfile().addActionListener(new ViewProfileAction());
        gui.getBtnLogout().addActionListener(new LogoutAction());
    }
    
    class ViewAppointmentsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ViewAppointment viewAppointmentGui = new ViewAppointment();
            ViewAppointmentController viewAppointmentController = new ViewAppointmentController(viewAppointmentGui, registry);
        }
    }
    
    class RecordDiagnosisAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RecordDiagnosis recordDiagnosisGui = new RecordDiagnosis();
            RecordDiagnosisController recordDiagnosisController = new RecordDiagnosisController(recordDiagnosisGui, registry);
        }
    }
    
    class RecordPrescriptionAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RecordPrescription recordPrescriptionGui = new RecordPrescription();
            RecordPrescriptionController recordPrescriptionController = new RecordPrescriptionController(recordPrescriptionGui, registry);
        }
    }
    
    class ViewPatientRecordsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ViewPatientRecord viewPatientRecordGui = new ViewPatientRecord();
            ViewPatientRecordController viewPatientRecordController = new ViewPatientRecordController(viewPatientRecordGui, registry);
        }
    }


    // Salma
    class RequestLabTestAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RequestLabTest requestLabTestGui = new RequestLabTest(gui.getDoctorName(), gui.getDoctorEmail());
            RequestLabTestController requestLabTestController = new RequestLabTestController(requestLabTestGui, registry);
        }
    }
    
    class RequestRefillAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RequestRefill requestRefillGui = new RequestRefill();
            RequestRefillController requestRefillController = new RequestRefillController(requestRefillGui, registry);
        }
    }
    
    class RequestICUAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RequestICU requestICUGui = new RequestICU();
            RequestICUController requestICUController = new RequestICUController(requestICUGui, registry);
        }
    }
    
    class ViewProfileAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(gui, 
                "Doctor profile view is coming soon!", 
                "Info", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    class LogoutAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(gui, 
                "Are you sure you want to logout?", 
                "Logout Confirmation", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                gui.dispose();
                
                RoleSelect roleSelect = new RoleSelect();
                RoleSelectController roleSelectController = new RoleSelectController(roleSelect, registry);
            }
        }
    }
}

