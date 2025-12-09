package remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Appointment;
import model.ICURoom;
import model.LabTest;
import model.Patient;
import model.Prescription;
import model.Report;

public class HospitalServiceImpl extends UnicastRemoteObject implements HospitalService {

    private List<Patient> patients = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private List<LabTest> labTests = new ArrayList<>();
    private List<Prescription> prescriptions = new ArrayList<>();
    private List<ICURoom> rooms = new ArrayList<>();

    public HospitalServiceImpl() throws RemoteException {
        super();
    }

    // ---------- Patient ----------

    @Override
    public Patient getPatient(int id) throws RemoteException {
        return patients.stream()
                .filter(p -> p.getPatientID() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addPatient(Patient p) throws RemoteException {
        patients.add(p);
    }

    // ---------- Appointment ----------

    @Override
    public Appointment bookAppointment(Appointment a) throws RemoteException {
        a.confirmReservation();       // logic inside Appointment
        appointments.add(a);
        return a;
    }

    @Override
    public List<Appointment> getAppointmentsForDoctor(int doctorId) throws RemoteException {
        return appointments.stream()
                .filter(a -> a.getDoctor().getDoctorID() == doctorId)
                .collect(Collectors.toList());
    }

    // ---------- Lab Test ----------

    @Override
    public void recordLabTestResult(LabTest test) throws RemoteException {
        test.recordTestResult();      // logic inside LabTest
        labTests.add(test);
    }

    // ---------- Prescription ----------

    @Override
    public void addPrescription(Prescription p) throws RemoteException {
        prescriptions.add(p);
    }

    // ---------- ICU ----------

    @Override
    public ICURoom requestICURoom(int roomID, int doctorID) throws RemoteException {
        ICURoom room = rooms.stream()
                .filter(r -> r.getRoomID() == roomID)
                .findFirst()
                .orElse(null);

        if (room != null) {
            room.handleRequest(doctorID);   // logic inside ICURoom
        }
        return room;
    }

    // ---------- Report ----------

    @Override
    public Report generateReport(String type) throws RemoteException {
        Report r = new Report(type);
        r.generateReport();
        return r;
    }
}
