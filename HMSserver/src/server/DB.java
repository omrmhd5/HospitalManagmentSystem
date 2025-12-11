package server;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Appointment;
import model.Diagnosis;
import model.Doctor;
import model.Drug;
import model.ICURequest;
import model.LabTest;
import model.Patient;
import model.Pharmacist;
import model.Prescription;
import model.User;
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
    public Patient getPatientByName(String patientName) throws RemoteException {
        Document doc = patient.find(Filters.eq("name", patientName)).first();
        if (doc == null) {
            return null;
        }
        // Mahmoud - Manually create Patient object from Document to avoid Gson RMI issues
        Patient p = new Patient(
            doc.getInteger("userID", 0),
            doc.getString("name"),
            doc.getString("email"),
            doc.getString("password"),
            doc.getInteger("patientID", 0),
            doc.getString("contactInfo"),
            doc.getString("gender"),
            doc.getInteger("age", 0),
            doc.getString("medicalHistory"),
            doc.getString("dateOfBirth"),
            doc.getString("address"),
            doc.getString("phoneNumber"),
            this
        );
        return p;
    }
    
    // Salma - Get patient by email
    public Patient getPatientByEmail(String email) throws RemoteException {
        Document doc = patient.find(Filters.eq("email", email)).first();
        if (doc == null) {
            return null;
        }
        // Manually create Patient object from Document 
        Patient p = new Patient(
            doc.getInteger("userID", 0),
            doc.getString("name"),
            doc.getString("email"),
            doc.getString("password"),
            doc.getInteger("patientID", 0),
            doc.getString("contactInfo"),
            doc.getString("gender"),
            doc.getInteger("age", 0),
            doc.getString("medicalHistory"),
            doc.getString("dateOfBirth") != null ? doc.getString("dateOfBirth") : "",
            doc.getString("address") != null ? doc.getString("address") : "",
            doc.getString("phoneNumber") != null ? doc.getString("phoneNumber") : "",
            this
        );
        return p;
    }
    
    // Mahmoud
    public Doctor getDoctorByName(String doctorName) throws RemoteException {
        Document doc = doctor.find(Filters.eq("name", doctorName)).first();
        if (doc == null) {
            return null;
        }
        // Mahmoud - Manually create Doctor object from Document to avoid Gson RMI issues
        Doctor d = new Doctor(
            doc.getInteger("userID", 0),
            doc.getString("name"),
            doc.getString("email"),
            doc.getString("password"),
            doc.getInteger("doctorID", 0),
            doc.getString("specialization"),
            doc.getString("availabilitySchedule")
        );
        return d;
    }
    
    // Salma - Get doctor by email
    public Doctor getDoctorByEmail(String email) throws RemoteException {
        Document doc = doctor.find(Filters.eq("email", email)).first();
        if (doc == null) {
            return null;
        }
        // Manually create Doctor object from Document to avoid Gson RMI issues
        Doctor d = new Doctor(
            doc.getInteger("userID", 0),
            doc.getString("name"),
            doc.getString("email"),
            doc.getString("password"),
            doc.getInteger("doctorID", 0),
            doc.getString("specialization") != null ? doc.getString("specialization") : "",
            doc.getString("availabilitySchedule") != null ? doc.getString("availabilitySchedule") : ""
        );
        return d;
    }
    
    // Salma - Get doctor phone number by email
    public String getDoctorPhoneByEmail(String email) {
        Document doc = doctor.find(Filters.eq("email", email)).first();
        if (doc == null) {
            return "";
        }
        return doc.getString("phoneNumber") != null ? doc.getString("phoneNumber") : "";
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
    
    // Salma - Update patient profile by email
    public boolean updatePatientByEmail(String email, String name, String dateOfBirth, 
                                       String gender, String address, String phoneNumber) {
        Document doc = patient.find(Filters.eq("email", email)).first();
        if (doc == null) {
            return false;
        }
        
        // Update the patient collection with new values
        Document updateDoc = new Document("$set", new Document()
            .append("name", name)
            .append("dateOfBirth", dateOfBirth)
            .append("gender", gender)
            .append("address", address)
            .append("phoneNumber", phoneNumber));
        
        patient.updateOne(Filters.eq("email", email), updateDoc);
        System.out.println("Patient profile updated in patient collection for: " + email);
        
        // update the user collection with the new name (for login purposes)
        Document userUpdateDoc = new Document("$set", new Document()
            .append("name", name));
        
        user.updateOne(Filters.and(
            Filters.eq("email", email),
            Filters.eq("role", "Patient")
        ), userUpdateDoc);
        System.out.println("Patient name updated in user collection for: " + email);
        
        return true;
    }
    
    // ========================================
    // Appointment DB
    // ========================================
    
    // Mahmoud
    public void insertAppointment(Appointment a) {
        // Mahmoud - Manually create Document to avoid Gson serialization of RMI object
        Document patientDoc = new Document();
        if (a.getPatient() != null) {
            Patient p = a.getPatient();
            patientDoc.append("patientID", p.getPatientID())
                    .append("name", p.getName() != null ? p.getName() : "")
                    .append("contactInfo", p.getContactInfo() != null ? p.getContactInfo() : "")
                    .append("gender", p.getGender() != null ? p.getGender() : "")
                    .append("age", p.getAge())
                    .append("medicalHistory", p.getMedicalHistory() != null ? p.getMedicalHistory() : "");
        }
        
        Document doctorDoc = new Document();
        if (a.getDoctor() != null) {
            Doctor d = a.getDoctor();
            doctorDoc.append("doctorID", d.getDoctorID())
                    .append("name", d.getName() != null ? d.getName() : "")
                    .append("specialization", d.getSpecialization() != null ? d.getSpecialization() : "")
                    .append("availabilitySchedule", d.getAvailabilitySchedule() != null ? d.getAvailabilitySchedule() : "");
        }
        
        Document doc = new Document()
                .append("appointmentID", a.getAppointmentID())
                .append("date", a.getDate() != null ? a.getDate() : "")
                .append("time", a.getTime() != null ? a.getTime() : "")
                .append("status", a.getStatus() != null ? a.getStatus() : "")
                .append("patient", patientDoc)
                .append("doctor", doctorDoc);
        
        appointment.insertOne(doc);
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
        // Mahmoud - Manually create Document to avoid Gson serialization of RMI object
        Document patientDoc = new Document();
        if (p.getPatient() != null) {
            Patient patient = p.getPatient();
            patientDoc.append("patientID", patient.getPatientID())
                    .append("name", patient.getName())
                    .append("contactInfo", patient.getContactInfo())
                    .append("gender", patient.getGender())
                    .append("age", patient.getAge())
                    .append("medicalHistory", patient.getMedicalHistory());
        }
        
        Document doctorDoc = new Document();
        if (p.getDoctor() != null) {
            Doctor d = p.getDoctor();
            doctorDoc.append("doctorID", d.getDoctorID())
                    .append("name", d.getName())
                    .append("specialization", d.getSpecialization())
                    .append("availabilitySchedule", d.getAvailabilitySchedule());
        }
        
        Document doc = new Document()
                .append("prescriptionID", p.getPrescriptionID())
                .append("medicine", p.getMedicine())
                .append("dosage", p.getDosage())
                .append("diagnosis", p.getDiagnosis())
                .append("patient", patientDoc)
                .append("doctor", doctorDoc);
        
        prescription.insertOne(doc);
        System.out.println("Prescription is inserted.");
    }
    
    // ========================================
    // Pharmacist DB
    // ========================================
    
    // Omar Mo
    public String requestMedicineRefill(int pharmacistID, String medicineName, int quantity) {
        return "Refill request submitted successfully for " 
                + medicineName + " (Quantity: " + quantity + ")";
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
        try {
            // Manually create Document to avoid Gson serialization issues with RMI objects
            Document doc = new Document()
                    .append("testID", labTest.getTestID())
                    .append("type", labTest.getType() != null ? labTest.getType() : "")
                    .append("result", labTest.getResult() != null ? labTest.getResult() : "")
                    .append("date", labTest.getDate() != null ? labTest.getDate() : "")
                    .append("status", labTest.getStatus() != null ? labTest.getStatus() : "")
                    .append("doctorName", labTest.getDoctorName() != null ? labTest.getDoctorName() : "")
                    .append("doctorEmail", labTest.getDoctorEmail() != null ? labTest.getDoctorEmail() : "")
                    .append("doctorPhone", labTest.getDoctorPhone() != null ? labTest.getDoctorPhone() : "")
                    .append("patientName", labTest.getPatientName() != null ? labTest.getPatientName() : "")
                    .append("patientAge", labTest.getPatientAge())
                    .append("patientGender", labTest.getPatientGender() != null ? labTest.getPatientGender() : "")
                    .append("patientDateOfBirth", labTest.getPatientDateOfBirth() != null ? labTest.getPatientDateOfBirth() : "");
            
            labtest.insertOne(doc);
        } catch (Exception e) {
            System.err.println("Error saving lab test to database: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save lab test to database", e);
        }
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
    // Doctor DB
    // ========================================
    
    // Mahmoud
    public List<String> getAllPatientNames() {
        List<String> patientNames = new ArrayList<>();
        // Mahmoud - Query patient collection for all patients
        for (Document doc : patient.find()) {
            String name = doc.getString("name");
            if (name != null && !name.isEmpty()) {
                patientNames.add(name);
            }
        }
        return patientNames;
    }
    
    // Mahmoud
    public List<String> getAllDoctorNames() {
        List<String> doctorNames = new ArrayList<>();
        // Mahmoud - Query user collection for doctors
        for (Document doc : user.find(Filters.eq("role", "Doctor"))) {
            String name = doc.getString("name");
            String specialty = doc.getString("specialty");
            if (name != null) {
                doctorNames.add(name + " - " + (specialty != null ? specialty : "General Practice"));
            }
        }
        return doctorNames;
    }
    
    // ========================================
    // Authentication DB
    // ========================================
    
    // Mahmoud
    public String authenticateUser(String email, String password, String role) {
        Document doc = user.find(Filters.and(
                Filters.eq("email", email),
                Filters.eq("password", password),
                Filters.eq("role", role)
        )).first();
        
        if (doc == null) {
            return null;
        }
        
        return doc.getString("name");
    }
    
    // Mahmoud
    public boolean registerUser(int userID, String name, String email, String password, String role) {
        Document doc = new Document()
                .append("userID", userID)
                .append("name", name)
                .append("email", email)
                .append("password", password)
                .append("role", role);
        
        user.insertOne(doc);
        return true;
    }
    
    // Mahmoud
    public boolean registerUserWithRole(int userID, String name, String email, String password, String role) {
        // Mahmoud - Create in user collection
        Document userDoc = new Document()
                .append("userID", userID)
                .append("name", name)
                .append("email", email)
                .append("password", password)
                .append("role", role);
        user.insertOne(userDoc);
        
        // Mahmoud - Also create in role-specific collection
        switch (role) {
            case "Doctor":
                Document doctorDoc = new Document()
                        .append("userID", userID)
                        .append("doctorID", userID)
                        .append("name", name)
                        .append("email", email)
                        .append("password", password)
                        .append("role", "Doctor")
                        .append("specialization", "General Practice")
                        .append("availabilitySchedule", "Mon-Fri 9AM-5PM");
                doctor.insertOne(doctorDoc);
                break;
                
            case "Patient":
                Document patientDoc = new Document()
                        .append("userID", userID)
                        .append("patientID", userID)
                        .append("name", name)
                        .append("email", email)
                        .append("password", password)
                        .append("role", "Patient")
                        .append("contactInfo", email)
                        .append("gender", "Not Specified")
                        .append("age", 0)
                        .append("medicalHistory", "None")
                        .append("dateOfBirth", "")
                        .append("address", "")
                        .append("phoneNumber", "");
                patient.insertOne(patientDoc);
                break;
                
            case "Pharmacist":
                Document pharmacistDoc = new Document()
                        .append("userID", userID)
                        .append("pharmacistID", userID)
                        .append("name", name)
                        .append("email", email)
                        .append("password", password)
                        .append("role", "Pharmacist");
                pharmacist.insertOne(pharmacistDoc);
                break;
                
            case "Lab Technician":
                Document labTechDoc = new Document()
                        .append("userID", userID)
                        .append("technicianID", userID)
                        .append("name", name)
                        .append("email", email)
                        .append("password", password)
                        .append("role", "Lab Technician");
                labtest.insertOne(labTechDoc);
                break;
                
            case "Admin":
            case "Receptionist":
                // Mahmoud - Admin and Receptionist only in user collection
                break;
        }
        
        return true;
    }
    
    // Mahmoud
    public boolean registerPatientExtended(int userID, String name, String email, String password,
                                           String gender, int age, String phone, String address) {
        // Mahmoud - Create in user collection
        Document userDoc = new Document()
                .append("userID", userID)
                .append("name", name)
                .append("email", email)
                .append("password", password)
                .append("role", "Patient");
        user.insertOne(userDoc);
        
        // Mahmoud - Create in patient collection with extended info
        Document patientDoc = new Document()
                .append("userID", userID)
                .append("patientID", userID)
                .append("name", name)
                .append("email", email)
                .append("password", password)
                .append("role", "Patient")
                .append("contactInfo", email)
                .append("gender", gender)
                .append("age", age)
                .append("phoneNumber", phone)
                .append("address", address)
                .append("medicalHistory", "None")
                .append("dateOfBirth", "");
        patient.insertOne(patientDoc);
        
        return true;
    }
    
    // Mahmoud
    public boolean registerDoctorExtended(int userID, String name, String email, String password,
                                          String specialization, String schedule, String phone) {
        // Mahmoud - Create in user collection
        Document userDoc = new Document()
                .append("userID", userID)
                .append("name", name)
                .append("email", email)
                .append("password", password)
                .append("role", "Doctor")
                .append("specialty", specialization);
        user.insertOne(userDoc);
        
        // Mahmoud - Create in doctor collection with extended info
        Document doctorDoc = new Document()
                .append("userID", userID)
                .append("doctorID", userID)
                .append("name", name)
                .append("email", email)
                .append("password", password)
                .append("role", "Doctor")
                .append("specialization", specialization)
                .append("specialty", specialization)
                .append("availabilitySchedule", schedule)
                .append("phoneNumber", phone);
        doctor.insertOne(doctorDoc);
        
        return true;
    }
    
    // Mahmoud
    public boolean emailExists(String email) {
        Document doc = user.find(Filters.eq("email", email)).first();
        return doc != null;
    }
    
    // ========================================
    // Close Connection
    // ========================================
    
    public void close() {
        mongoClient.close();
    }
}
