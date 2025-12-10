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
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
   MongoCollection<Document> collection1;
  
   public static Gson gson = new Gson();
   
   MongoCollection<Document> appointmentCollection;


    public DB() {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        mongoClient = new MongoClient();
        database = mongoClient.getDatabase("Hospital");

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





   
//   public DB() 
//    {
//        // Disables Mongo Logs
//        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
//        mongoLogger.setLevel(Level.SEVERE);
//
//        // Initialize
//        mongoClient = new MongoClient();
//        // Database name
//        database = mongoClient.getDatabase("School"); 
//        // Collection for the doctor 
//        collection1 = database.getCollection("Student"); 
//        
//  
//   
//    }
   
//   public void manageAppointment(){
//            System.out.println("ManageAppointment");
//        }
   
//         public void insertStudent(Student s) 
//    {
//        collection1.insertOne(Document.parse(gson.toJson(s)));
//        System.out.println("Student is inserted.");
//    }
         
         
         public void close() 
    {
        mongoClient.close();
    }
}
