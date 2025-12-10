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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Appointment;
import model.Patient;
import model.Prescription;
import org.bson.Document;
import model.Appointment;
import model.AvailableReservation;
import model.Diagnosis;
import model.ICURequest;

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

        mongoClient = new MongoClient();

        appointmentCollection = database.getCollection("Appointment");
    }
    
        public void addAppointment(Appointment a) {
        Document doc = Document.parse(gson.toJson(a));
        appointmentCollection.insertOne(doc);
    }
        public Appointment getAppointmentByID(int id) {
        Document doc = appointmentCollection.find(Filters.eq("appointmentID", id)).first();
        if (doc == null) return null;
        return gson.fromJson(doc.toJson(), Appointment.class);
    }

        public void updateAppointment(Appointment a) {
        appointmentCollection.replaceOne(
            Filters.eq("appointmentID", a.getAppointmentID()),
            Document.parse(gson.toJson(a))
        );
    }

        public void deleteAppointment(int id) {
        appointmentCollection.deleteOne(Filters.eq("appointmentID", id));
    }


    public List<Appointment> getAppointmentsForDoctor(int doctorID) {
    List<Appointment> list = new ArrayList<>();

    MongoCollection<Document> col = database.getCollection("Appointment");

    for (Document doc : col.find(Filters.eq("doctor.doctorID", doctorID))) {
        Appointment a = gson.fromJson(doc.toJson(), Appointment.class);
        list.add(a);
    }

    return list;
}
    MongoCollection<Document> icuCollection;
    icuCollection = database.getCollection("ICURequests");
    
    public void addICURequest(ICURequest req) {
    Document doc = Document.parse(gson.toJson(req));
    icuCollection.insertOne(doc);
}

public List<ICURequest> getRequestsForPatient(int patientID) {
    List<ICURequest> list = new ArrayList<>();

    for (Document doc : icuCollection.find(Filters.eq("patient.patientID", patientID))) {
        list.add(gson.fromJson(doc.toJson(), ICURequest.class));
    }

    return list;
}

public List<AvailableReservation> getAvailableReservations(String doctorName, String specialty, String date) {

    List<AvailableReservation> list = new ArrayList<>();

    MongoCollection<Document> col = database.getCollection("DoctorSchedules");

    Document filter = new Document();

    if (doctorName != null && !doctorName.isEmpty())
        filter.append("doctor.name", doctorName);

    if (specialty != null && !specialty.isEmpty())
        filter.append("doctor.specialty", specialty);

    if (date != null && !date.isEmpty())
        filter.append("date", date);

    for (Document doc : col.find(filter)) {

        AvailableReservation ar = gson.fromJson(doc.toJson(), AvailableReservation.class);
        list.add(ar);
    }

    return list;
}

MongoCollection<Document> diagnosisCollection;
diagnosisCollection = database.getCollection("Diagnosis");

public void addDiagnosis(Diagnosis d) {
    Document doc = Document.parse(gson.toJson(d));
    diagnosisCollection.insertOne(doc);
}

public List<Diagnosis> getDiagnosesForPatient(int patientID) {
    List<Diagnosis> list = new ArrayList<>();

    for (Document doc : diagnosisCollection.find(Filters.eq("patient.patientID", patientID))) {
        list.add(gson.fromJson(doc.toJson(), Diagnosis.class));
    }
    return list;
}

MongoCollection<Document> drugCollection;
drugCollection = database.getCollection("Drugs");

public void addDrug(Drug d) {
    drugCollection.insertOne(Document.parse(gson.toJson(d)));
}

public Drug getDrugByID(int drugID) {
    Document doc = drugCollection.find(Filters.eq("drugID", drugID)).first();
    return doc == null ? null : gson.fromJson(doc.toJson(), Drug.class);
}

public boolean updateDrug(Drug d) {
    drugCollection.replaceOne(
        Filters.eq("drugID", d.getDrugID()),
        Document.parse(gson.toJson(d))
    );
    return true;
}
         
         public void close() 
    {
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
          // Salma
          // Get patient profile by user ID
          public Document getPatientById(int userID) {
              // Collection for patients (update collection name as needed)
              MongoCollection<Document> patientCollection = database.getCollection("Patients");
              
              // Find patient by userID
              Document patient = patientCollection.find(Filters.eq("userID", userID)).first();
              
              if (patient != null) {
                  System.out.println("Patient found: " + patient.toJson());
              } else {
                  System.out.println("No patient found with userID: " + userID);
              }
              
              return patient;
          }
          
          // Salma
          // Save lab test to database
          public void saveLabTest(model.LabTest labTest) {
              try {
                  MongoCollection<Document> labTestCollection = database.getCollection("LabTests");
                  
                  // Convert LabTest to JSON Document
                  String json = gson.toJson(labTest);
                  Document doc = Document.parse(json);
                  
                  // Insert into database
                  labTestCollection.insertOne(doc);
                  System.out.println("Lab test saved to database with ID: " + labTest.getTestID());
                  
              } catch (Exception e) {
                  System.err.println("Error saving lab test: " + e.getMessage());
              }
          }
          
          // Salma
          // User management methods
          public void saveUser(int userID, String fullName, String email, String role) {
              try {
                  MongoCollection<Document> userCollection = database.getCollection("Users");
                  Document doc = new Document("userID", userID)
                      .append("fullName", fullName)
                      .append("email", email)
                      .append("role", role);
                  userCollection.insertOne(doc);
                  System.out.println("User saved to database: " + fullName);
              } catch (Exception e) {
                  System.err.println("Error saving user: " + e.getMessage());
              }
          }
          
          public void deleteUserById(int userID) {
              try {
                  MongoCollection<Document> userCollection = database.getCollection("Users");
                  userCollection.deleteOne(Filters.eq("userID", userID));
                  System.out.println("User deleted from database with ID: " + userID);
              } catch (Exception e) {
                  System.err.println("Error deleting user: " + e.getMessage());
              }
          }
}
