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

import model.Patient;
import model.Pharmacist;
import org.bson.Document;

public class DB {

    public static MongoClient mongoClient;
    public static MongoDatabase database;

    // Collections
    private MongoCollection<Document> patientCollection;
    private MongoCollection<Document> pharmacistCollection;

    public static Gson gson = new Gson();

    public DB() {

        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        mongoClient = new MongoClient();
        database = mongoClient.getDatabase("HospitalSystem");

        patientCollection = database.getCollection("Patients");
        pharmacistCollection = database.getCollection("Pharmacists"); // <-- ADD THIS COLLECTION
    }

    // ---------------------------------------------------------
    // Insert new patient
    public void insertPatient(Patient p) {
        patientCollection.insertOne(Document.parse(gson.toJson(p)));
        System.out.println("Patient inserted: " + p.getPatientID());
    }

    // Get patient by ID
    public Patient getPatientByID(int id) {
        Document doc = patientCollection.find(Filters.eq("patientID", id)).first();
        if (doc == null) return null;
        return gson.fromJson(doc.toJson(), Patient.class);
    }

    // Add patient record
    public boolean addPatientRecord(int patientID, String record) {

        Patient p = getPatientByID(patientID);

        if (p == null) return false;

        p.addRecord(record);

        patientCollection.replaceOne(
                Filters.eq("patientID", patientID),
                Document.parse(gson.toJson(p))
        );

        return true;
    }

    // Get patient records list
    public List<String> getRecordsForPatient(int patientID) {

        Patient p = getPatientByID(patientID);

        if (p == null) return new ArrayList<>();

        return p.getRecords();
    }

    // ---------------------------------------------------------
     //IBRAHIM
 
    public String requestMedicineRefill(int pharmacistID, String medicineName, int quantity) {

        if (medicineName == null || medicineName.isBlank()) {
            return "Medicine name cannot be empty.";
        }

        if (quantity <= 0) {
            return "Invalid quantity. Must be greater than zero.";
        }

        // Find pharmacist in DB
        Document doc = pharmacistCollection.find(Filters.eq("pharmacistID", pharmacistID)).first();

        if (doc == null) {
            return "Pharmacist not found in database.";
        }

        Pharmacist pharmacist = gson.fromJson(doc.toJson(), Pharmacist.class);

        // Call model logic
        return pharmacist.requestMedicineRefill(medicineName, quantity);
    }

    // ---------------------------------------------------------

    public void close() {
        mongoClient.close();
    }
}
