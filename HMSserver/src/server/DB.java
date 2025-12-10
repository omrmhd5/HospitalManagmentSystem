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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import model.Appointment;

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
