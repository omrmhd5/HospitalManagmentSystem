/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Appointment;
import model.Patient;
import model.Prescription;
import org.bson.Document;

/**
 *
 * @author meriam
 */
public class DB {
   public static MongoClient mongoClient;
    
   public static MongoDatabase database;
   public static Gson gson = new Gson();
   
   //Mahmoud   
   MongoCollection<Document> appointment;
   //Mahmoud
   MongoCollection<Document> prescription;
   //Mahmoud
   MongoCollection<Document> patient;
  
   public DB() 
    {
        // Disables Mongo Logs
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        // Initialize
        mongoClient = new MongoClient();
        // Database name
        database = mongoClient.getDatabase("HMS");

        // Collection for the appointments 
        appointment = database.getCollection("appointment");
        // Collection for the prescriptions
        prescription = database.getCollection("prescription");
        // Collection for the patients
        patient = database.getCollection("patient");
    }
    
    // Mahmoud
    public void insertAppointment (Appointment a) {
        appointment.insertOne(Document.parse(gson.toJson(a)));
        System.out.println("Appointment is inserted.");
    }
    
    // Mahmoud
    public void insertPrescription (Prescription p) {
        prescription.insertOne(Document.parse(gson.toJson(p)));
        System.out.println("Prescription is inserted.");
    }
    
    // Mahmoud
    public void insertPatient (Patient p) {
        patient.insertOne(Document.parse(gson.toJson(p)));
        System.out.println("Patient is inserted.");
    }
    
    // Mahmoud
    public Patient getPatientByName(String patientName) {
        Document query = new Document("contactInfo", patientName);
        Document doc = patient.find(query).first();
        
        return gson.fromJson(doc.toJson(), Patient.class);
    }
    
    public void close() {
        mongoClient.close();
    }
}
