package model;

import DesignPattern.Strategy.DoctorRequestStrategy;
import DesignPattern.Strategy.LabTestRequest;
import DesignPattern.Strategy.MedicineRefillRequest;
import DesignPattern.Strategy.ICURequest;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmi.DoctorRequestInterface;
import rmi.ICUInterface;
import rmi.LabTestInterface;
import rmi.PharmacyInterface;
import server.DB;

public class DoctorRequestService extends UnicastRemoteObject implements DoctorRequestInterface {
    
    private final DB db;
    private final LabTestInterface labTestService;
    private final PharmacyInterface pharmacyService;
    private final ICUInterface icuService;
    
    public DoctorRequestService(DB db, LabTestInterface labTestService, 
                                PharmacyInterface pharmacyService, ICUInterface icuService) 
            throws RemoteException {
        this.db = db;
        this.labTestService = labTestService;
        this.pharmacyService = pharmacyService;
        this.icuService = icuService;
    }
    
    @Override
    public boolean executeLabTestRequest(String doctorEmail, String testType,
                                        String patientName, int patientAge, String patientGender,
                                        String patientDateOfBirth, String doctorPhone) throws RemoteException {
        try {
            // Get doctor from database
            Doctor doctor = db.getDoctorByEmail(doctorEmail);
            if (doctor == null) {
                return false;
            }
            
            // Create and set strategy
            DoctorRequestStrategy strategy = new LabTestRequest(
                testType, patientName, patientAge, patientGender, patientDateOfBirth,
                doctorEmail, doctorPhone, labTestService
            );
            
            doctor.setRequestStrategy(strategy);
            doctor.executeRequest();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean executeMedicineRefillRequest(String doctorEmail,
                                               String prescriptionID, String medicineName,
                                               int pharmacistID, int quantity) throws RemoteException {
        try {
            // Get doctor from database
            Doctor doctor = db.getDoctorByEmail(doctorEmail);
            if (doctor == null) {
                return false;
            }
            
            // Create and set strategy
            DoctorRequestStrategy strategy = new MedicineRefillRequest(
                prescriptionID, medicineName, pharmacistID, quantity, pharmacyService
            );
            
            doctor.setRequestStrategy(strategy);
            doctor.executeRequest();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public int getLastICURequestID() throws RemoteException {
        return db.getLastICURequestID();
    }
    
    @Override
    public boolean executeICURequest(String doctorEmail, String patientName,
                                    String date, String time, String urgency, String diagnosis,
                                    String expectedDuration) throws RemoteException {
        try {
            // Get doctor from database
            Doctor doctor = db.getDoctorByEmail(doctorEmail);
            if (doctor == null) {
                return false;
            }
            
            // Create and set strategy
            DoctorRequestStrategy strategy = new ICURequest(
                patientName, date, time, urgency, diagnosis, expectedDuration,
                icuService
            );
            
            doctor.setRequestStrategy(strategy);
            doctor.executeRequest();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

