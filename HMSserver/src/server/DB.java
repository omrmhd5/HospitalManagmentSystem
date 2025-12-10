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
import org.bson.Document;

public class DB {

    public static MongoClient mongoClient;
    public static MongoDatabase database;

    // --- Collections ---
    private MongoCollection<Document> patientCollection;

    public static Gson gson = new Gson();

    public DB() {

        // Disable MongoDB noise logs
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        // Open local connection
        mongoClient = new MongoClient();

        // Select database
        database = mongoClient.getDatabase("HospitalSystem");

        // Patient collection
        patientCollection = database.getCollection("Patients");
    }



    // Insert a new patient (used in Add Patient Record) // ibrahim
    public void insertPatient(Patient p) {
        patientCollection.insertOne(Document.parse(gson.toJson(p)));
        System.out.println("Patient inserted: " + p.getPatientID());
    }

    // Get patient by ID //ibrahim
    public Patient getPatientByID(int id) {
        Document doc = patientCollection.find(Filters.eq("patientID", id)).first();
        if (doc == null) return null;
        return gson.fromJson(doc.toJson(), Patient.class);
    }

    // Add patient record entry to existing patient //ibrahim
    public boolean addPatientRecord(int patientID, String record) {

        Patient p = getPatientByID(patientID);

        if (p == null)
            return false;

        // Add the record inside model object
        p.addRecord(record);

        // Save updated object back into database
        patientCollection.replaceOne(
                Filters.eq("patientID", patientID),
                Document.parse(gson.toJson(p))
        );

        return true;
    }

    // List all records for specific patient // ibrahim
    public List<String> getRecordsForPatient(int patientID) {

        Patient p = getPatientByID(patientID);

        if (p == null)
            return new ArrayList<>();

        return p.getRecords();
    }

    // ============================================================

    public void close() {
        mongoClient.close();
    }
}
