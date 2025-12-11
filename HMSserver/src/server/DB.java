package server;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Appointment;
import model.AvailableReservation;
import model.Diagnosis;
import model.Doctor;
import model.Drug;
import model.ICURequest;
import model.LabTest;
import model.Patient;
import model.Pharmacist;
import model.Prescription;
import org.bson.Document;

public class DB {
    public static MongoClient mongoClient;
    public static MongoDatabase database;
    public static Gson gson = new Gson();
    
    // Collections
    private final MongoCollection<Document> patient;
    private final MongoCollection<Document> appointment;
    private final MongoCollection<Document> prescription;
    private final MongoCollection<Document> pharmacist;
    private final MongoCollection<Document> labtest;
    private final MongoCollection<Document> icu;
    private final MongoCollection<Document> diagnosis;
    private final MongoCollection<Document> drug;
    private final MongoCollection<Document> doctor;
    private final MongoCollection<Document> user;
    
    public DB() {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
        
        mongoClient = new MongoClient();
        database = mongoClient.getDatabase("HMS");
        
        patient = database.getCollection("patient");
        appointment = database.getCollection("appointment");
        prescription = database.getCollection("prescription");
        pharmacist = database.getCollection("pharmacist");
        labtest = database.getCollection("labtest");
        icu = database.getCollection("icu");
        diagnosis = database.getCollection("diagnosis");
        drug = database.getCollection("drug");
        doctor = database.getCollection("doctor");
        user = database.getCollection("user");
    }
    
    // ========================================
    // Patient DB
    // ========================================
    
    // Mahmoud
    public void insertPatient(Patient p) {
        patient.insertOne(Document.parse(gson.toJson(p)));
        System.out.println("Patient is inserted.");
    }
    
    // Mahmoud
    public Patient getPatientByID(int id) {
        Document doc = patient.find(Filters.eq("patientID", id)).first();
        return gson.fromJson(doc.toJson(), Patient.class);
    }
    
    // Mahmoud
    public Patient getPatientByName(String patientName) {
        Document doc = patient.find(Filters.eq("contactInfo", patientName)).first();
        return gson.fromJson(doc.toJson(), Patient.class);
    }
    
    // Ibrahim
    public boolean addPatientRecord(int patientID, String record) {
        Patient p = getPatientByID(patientID);
        p.addRecord(record);
        patient.replaceOne(
            Filters.eq("patientID", patientID),
            Document.parse(gson.toJson(p))
        );
        return true;
    }
    
    // Ibrahim
    public List<String> getRecordsForPatient(int patientID) {
        Patient p = getPatientByID(patientID);
        return p.getRecords();
    }
    
    // Salma
    public Document getPatientById(int userID) {
        Document doc = patient.find(Filters.eq("userID", userID)).first();
        System.out.println("Patient found: " + doc.toJson());
        return doc;
    }
    
    // ========================================
    // Appointment DB
    // ========================================
    
    // Mahmoud
    public void insertAppointment(Appointment a) {
        appointment.insertOne(Document.parse(gson.toJson(a)));
        System.out.println("Appointment is inserted.");
    }
    
    // Rana
    public Appointment getAppointmentByID(int id) {
        Document doc = appointment.find(Filters.eq("appointmentID", id)).first();
        return gson.fromJson(doc.toJson(), Appointment.class);
    }
    
    // Rana
    public void updateAppointment(Appointment a) {
        appointment.replaceOne(
            Filters.eq("appointmentID", a.getAppointmentID()),
            Document.parse(gson.toJson(a))
        );
    }
    
    // Rana
    public void deleteAppointment(int id) {
        appointment.deleteOne(Filters.eq("appointmentID", id));
    }
    
    // Rana
    public List<Appointment> getAppointmentsForDoctor(int doctorID) {
        List<Appointment> list = new ArrayList<>();
        for (Document doc : appointment.find(Filters.eq("doctor.doctorID", doctorID))) {
            Appointment a = gson.fromJson(doc.toJson(), Appointment.class);
            list.add(a);
        }
        return list;
    }
    
    // ========================================
    // Prescription DB
    // ========================================
    
    // Mahmoud
    public void insertPrescription(Prescription p) {
        prescription.insertOne(Document.parse(gson.toJson(p)));
        System.out.println("Prescription is inserted.");
    }
    
    // ========================================
    // Pharmacist DB
    // ========================================
    
    // Omar Mo
    public String requestMedicineRefill(int pharmacistID, String medicineName, int quantity) {
        Document doc = pharmacist.find(Filters.eq("pharmacistID", pharmacistID)).first();
        Pharmacist ph = gson.fromJson(doc.toJson(), Pharmacist.class);
        return ph.requestMedicineRefill(medicineName, quantity);
    }
    
    // ========================================
    // Lab Test DB
    // ========================================
    
    // Omar Mo
    public String recordLabTestResult(int testID, String result) {
        Document doc = labtest.find(Filters.eq("testID", testID)).first();
        LabTest test = gson.fromJson(doc.toJson(), LabTest.class);
        test.setResult(result);
        labtest.replaceOne(
            Filters.eq("testID", testID),
            Document.parse(gson.toJson(test))
        );
        return "Test result saved successfully.";
    }
    
    // Salma
    public void saveLabTest(LabTest labTest) {
        labtest.insertOne(Document.parse(gson.toJson(labTest)));
        System.out.println("Lab test saved to database with ID: " + labTest.getTestID());
    }
    
    // ========================================
    // ICU DB
    // ========================================
    
    // Rana
    public void addICURequest(ICURequest req) {
        icu.insertOne(Document.parse(gson.toJson(req)));
    }
    
    // Rana
    public List<ICURequest> getRequestsForPatient(int patientID) {
        List<ICURequest> list = new ArrayList<>();
        for (Document doc : icu.find(Filters.eq("patient.patientID", patientID))) {
            list.add(gson.fromJson(doc.toJson(), ICURequest.class));
        }
        return list;
    }
    
    // ========================================
    // Available Reservation DB
    // ========================================
    
    // Tasneem
    public List<AvailableReservation> getAvailableReservations(String doctorName, String specialty, String date) {
        List<AvailableReservation> list = new ArrayList<>();
        Document filter = new Document();
        
        if (doctorName != null && !doctorName.isEmpty())
            filter.append("doctor.name", doctorName);
        
        if (specialty != null && !specialty.isEmpty())
            filter.append("doctor.specialty", specialty);
        
        if (date != null && !date.isEmpty())
            filter.append("date", date);
        
        for (Document doc : doctor.find(filter)) {
            AvailableReservation ar = gson.fromJson(doc.toJson(), AvailableReservation.class);
            list.add(ar);
        }
        return list;
    }
    
    // ========================================
    // Diagnosis DB
    // ========================================
    
    // Tasneem
    public void addDiagnosis(Diagnosis d) {
        diagnosis.insertOne(Document.parse(gson.toJson(d)));
    }
    
    // Tasneem
    public List<Diagnosis> getDiagnosesForPatient(int patientID) {
        List<Diagnosis> list = new ArrayList<>();
        for (Document doc : diagnosis.find(Filters.eq("patient.patientID", patientID))) {
            list.add(gson.fromJson(doc.toJson(), Diagnosis.class));
        }
        return list;
    }
    
    // ========================================
    // Drug DB
    // ========================================
    
    // Tasneem
    public void addDrug(Drug d) {
        drug.insertOne(Document.parse(gson.toJson(d)));
    }
    
    // Tasneem
    public Drug getDrugByID(int drugID) {
        Document doc = drug.find(Filters.eq("drugID", drugID)).first();
        return gson.fromJson(doc.toJson(), Drug.class);
    }
    
    // Tasneem
    public boolean updateDrug(Drug d) {
        drug.replaceOne(
            Filters.eq("drugID", d.getDrugID()),
            Document.parse(gson.toJson(d))
        );
        return true;
    }
    
    // ========================================
    // User DB
    // ========================================
    
    // Salma
    public void saveUser(int userID, String fullName, String email, String role) {
        Document doc = new Document("userID", userID)
            .append("fullName", fullName)
            .append("email", email)
            .append("role", role);
        user.insertOne(doc);
        System.out.println("User saved to database: " + fullName);
    }
    
    // Salma
    public void deleteUserById(int userID) {
        user.deleteOne(Filters.eq("userID", userID));
        System.out.println("User deleted from database with ID: " + userID);
    }
    
    // ========================================
    // Close Connection
    // ========================================
    
    public void close() {
        mongoClient.close();
    }
}
