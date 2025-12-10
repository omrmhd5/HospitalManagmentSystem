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
    }
    
    // Mahmoud
    public void insertAppointment (Appointment a) {
        appointment.insertOne(Document.parse(gson.toJson(a)));
        System.out.println("Appointment is inserted.");
    }
    
    public void close() {
        mongoClient.close();
    }
}
