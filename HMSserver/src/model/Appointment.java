package model;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import rmi.AppointmentInterface;
import server.DB;
import  DesignPattern.Observer.AppointmentSubject;
import  DesignPattern.Observer.AppointmentObserver;
import java.util.ArrayList;

public class Appointment extends UnicastRemoteObject implements AppointmentInterface, AppointmentSubject  {

    private int appointmentID;
    private Patient patient;
    private Doctor doctor;
    private String date;
    private String time;
    private String status;
    private final DB db;
    private List<AppointmentObserver> observers = new ArrayList<>();

  
   // Mahmoud
    public Appointment(DB db) throws RemoteException{
        this.db = db;
    }
    
    // Mahmoud
    public Appointment(int appointmentID, Patient patient, Doctor doctor,
                       String date, String time, DB db) throws RemoteException {
        this.db = db;
        this.appointmentID = appointmentID;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.status = "Pending";
    }
  
    
    // Logic that belongs to Appointment
    public void confirmReservation() {
        this.status = "Confirmed";
    }


   public void updateStatus(String status) throws RemoteException {
    this.status = status;
    notifyObservers("Appointment status changed to: " + status);
}

    
    // Mahmoud
    @Override
    public String bookAppointment(String patientName, String doctorName, String date, String time)
            throws RemoteException {

        Patient patient = db.getPatientByName(patientName);
        Doctor doctor = db.getDoctorByName(doctorName);

        int newID = db.getNextAppointmentID();

        Appointment appointment = new Appointment(
                newID,
                patient,
                doctor,
                date,
                time,
                db
        );

        db.insertAppointment(appointment);
        appointment.addObserver(patient);
        appointment.addObserver(doctor);
        AppointmentObserver receptionist = null;
        appointment.addObserver(receptionist);

            


        return "Appointment booked successfully.\nAppointment ID: " + newID;
    }
    
    //Tasneem
    @Override
    public void addObserver(AppointmentObserver o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void removeObserver(AppointmentObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String message) throws RemoteException {
      
        String appointmentInfo = toReadableString();
        for (AppointmentObserver o : observers) {
            o.update(appointmentInfo, message);
        }
    }



    // Getters / setters
    public int getAppointmentID() { return appointmentID; }
    public void setAppointmentID(int appointmentID) { this.appointmentID = appointmentID; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    // Rana
    @Override
public String getAppointmentByID(int appointmentID) throws RemoteException {
    Appointment a = db.getAppointmentByID(appointmentID);

    if (a == null) {
        return "Appointment not found.";
    }

    return
        "Appointment ID: " + a.getAppointmentID() + "\n" +
        "Date: " + a.getDate() + "\n" +
        "Time: " + a.getTime() + "\n" +
        "Status: " + a.getStatus() + "\n" +
        "Patient: " + (a.getPatient() != null ? a.getPatient().getName() : "N/A") + "\n" +
        "Doctor: " + (a.getDoctor() != null ? a.getDoctor().getName() : "N/A");
}

    
    // Rana
    @Override
    public boolean updateAppointment(int appointmentID, String patientName, String doctorName, 
                                      String date, String time, String status) throws RemoteException {
        Appointment appointment = db.getAppointmentByID(appointmentID);
        
        if (appointment == null) {
            return false;
        }
        
        // Mahmoud - Create Patient object
        Patient patient = new Patient(
            0, patientName, "", "", 0, patientName, "", 0, "", "", "", "", db
        );
        
        // Mahmoud - Create Doctor object
        Doctor doctor = new Doctor(
            0, doctorName, "", "", 0, "", ""
        );
        
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setStatus(status);
        
        // Mahmoud
        db.updateAppointment(appointment);
        return true;
    }
    
    //Rana
    @Override
    
    public boolean cancelAppointment(int appointmentID) throws RemoteException {

        Appointment appointment = db.getAppointmentByID(appointmentID);

        if (appointment == null) {
            return false;
        }

        if ("Cancelled".equalsIgnoreCase(appointment.getStatus()) ||
            "Completed".equalsIgnoreCase(appointment.getStatus())) {
            return false;
        }

        appointment.setStatus("Cancelled");

        return db.updateAppointment(appointment); 
    }


    //Rana
    @Override
    public boolean rescheduleAppointment(int appointmentID, String newDate, String newTime) throws RemoteException {
        Appointment appointment = db.getAppointmentByID(appointmentID);
        
        if (appointment == null) {
            return false;
        }
        
        
        if (appointment.getStatus().equals("Cancelled") || appointment.getStatus().equals("Completed")) {
            System.out.println("Cannot reschedule. Appointment is " + appointment.getStatus());
            return false;
        }

      
        if (!isRescheduleAllowed()) {
            System.out.println("Rescheduling is not allowed this close to the appointment.");
            return false;
        }

       
        appointment.setDate(newDate);
        appointment.setTime(newTime);
        appointment.setStatus("Rescheduled");
        db.updateAppointment(appointment);
        return true;
    }
    

    //Rana
    @Override
    public boolean manageAppointment(int appointmentID, String operation, String newDate, String newTime) throws RemoteException {
        if (operation.equalsIgnoreCase("cancel")) {
            return cancelAppointment(appointmentID);
        }
        else if (operation.equalsIgnoreCase("reschedule")) {
            return rescheduleAppointment(appointmentID, newDate, newTime);
        }
        else {
            System.out.println("Invalid operation.");
            return false;
        }
    }
   
    @Override
    public int getDoctorIDByEmail(String email) throws RemoteException {
        return db.getDoctorIDByEmail(email);
    }


    //Rana
    @Override
    public String getAppointmentsForDoctor(int doctorID) throws RemoteException {
        List<Appointment> list = db.getAppointmentsForDoctor(doctorID);
        
        if (list == null || list.isEmpty()) {
            return "No appointments found for this doctor";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("Doctor's Appointments\n");
        result.append("====================\n\n");
        
        for (Appointment a : list) {
            result.append(a.toReadableString()).append("\n");
            result.append("-----------------\n");
        }
        
        return result.toString();
    }

   
    private boolean isRescheduleAllowed() {
        return true;
    }

   
    private boolean isCancellationAllowed() {
        return true;
    }
    
    public String toReadableString() {
    return  "Appointment ID: " + appointmentID +
            "\nPatient: " + (patient != null ? patient.getName() : "Unknown") +
            "\nDoctor: " + (doctor != null ? doctor.getName() : "Unknown") +
            "\nDate: " + date +
            "\nTime: " + time +
            "\nStatus: " + status;
}

    // Mahmoud
    @Override
    public List<String> getAllDoctorNames() throws RemoteException {
        return db.getAllDoctorNames();
    }
    
    // Salma - Get doctor phone number by email
    @Override
    public String getDoctorPhoneByEmail(String email) throws RemoteException {
        return db.getDoctorPhoneByEmail(email);
    }
    
}
