package model;

import java.rmi.RemoteException;
import java.util.List;
import org.bson.Document;
import rmi.AdminInterface;
import server.DB;

public class Admin extends User implements AdminInterface {

    private int adminID;
    private String accessLevel;
    private final DB db;

    public Admin() throws RemoteException {
        this.db = null;
    }
    
    public Admin(DB db) throws RemoteException {
        this.db = db;
    }

    public Admin(int userID, String name, String email, String password,
                 int adminID, String accessLevel) throws RemoteException {
        super(userID, name, email, password, "Admin");
        this.adminID = adminID;
        this.accessLevel = accessLevel;
        this.db = null;
    }
    
    public Admin(int userID, String name, String email, String password,
                 int adminID, String accessLevel, DB db) throws RemoteException {
        super(userID, name, email, password, "Admin");
        this.adminID = adminID;
        this.accessLevel = accessLevel;
        this.db = db;
    }

    // ---------- RMI INTERFACE METHODS - User Management ----------
    // Salma
    
    @Override
    public List<Object[]> getAllUsers() throws RemoteException {
        if (db == null) {
            throw new RemoteException("Database connection not available");
        }
        return db.getAllUsers();
    }
    
    @Override
    public boolean addUser(String fullName, String email, String role) throws RemoteException {
        if (db == null) {
            throw new RemoteException("Database connection not available");
        }
        try {
            // Mahmoud
            int userID = db.getNextUserID();
            // Mahmoud - Use registerUserWithRole to add to both user collection and role-specific collection
            // Default password is "password" - admin can change it later if needed
            boolean success = db.registerUserWithRole(userID, fullName, email, "password", role);
            if (success) {
                System.out.println("Admin: User added to database - " + fullName + " (ID: " + userID + ", Role: " + role + ")");
            }
            return success;
        } catch (Exception e) {
            System.err.println("Error adding user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteUser(int userID) throws RemoteException {
        if (db == null) {
            throw new RemoteException("Database connection not available");
        }
        try {
            db.deleteUserById(userID);
            System.out.println("Admin: User deleted from database with ID: " + userID);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean updateUser(int userID, String fullName, String email, String role) throws RemoteException {
        if (db == null) {
            throw new RemoteException("Database connection not available");
        }
        try {
            // Mahmoud - Get old user document to preserve password
            Document oldUserDoc = db.getUserById(userID);
            String password = "password"; // Default password
            if (oldUserDoc != null && oldUserDoc.getString("password") != null) {
                password = oldUserDoc.getString("password");
            }
            
            // Mahmoud - Delete old user (this will also delete from role-specific collection)
            db.deleteUserById(userID);
            
            // Mahmoud - Use registerUserWithRole to add to both user collection and role-specific collection
            boolean success = db.registerUserWithRole(userID, fullName, email, password, role);
            if (success) {
                System.out.println("Admin: User updated in database - " + fullName + " (ID: " + userID + ", Role: " + role + ")");
            }
            return success;
        } catch (Exception e) {
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ---------- UML METHODS ----------
    public void manageUser(User u) {
        System.out.println("Managing user: " + u.getName());
    }
    
    // Salma 
    @Override
    public String generateReport(String reportType) throws RemoteException {
        if (db == null) {
            throw new RemoteException("Database connection not available");
        }
        
        StringBuilder report = new StringBuilder();
        report.append("=".repeat(80)).append("\n");
        report.append("HOSPITAL MANAGEMENT SYSTEM - REPORT\n");
        report.append("Report Type: ").append(reportType).append("\n");
        report.append("Generated: ").append(new java.util.Date()).append("\n");
        report.append("=".repeat(80)).append("\n\n");
        
        try {
            switch (reportType) {
                case "All Users":
                    report.append(generateAllUsersReport());
                    break;
                case "Users by Role":
                    report.append(generateUsersByRoleReport());
                    break;
                case "All Patients":
                    report.append(generateAllPatientsReport());
                    break;
                case "All Appointments":
                    report.append(generateAllAppointmentsReport());
                    break;
                case "Drug Inventory":
                    report.append(generateDrugInventoryReport());
                    break;
                case "ICU Requests":
                    report.append(generateICURequestsReport());
                    break;
                case "Lab Tests":
                    report.append(generateLabTestsReport());
                    break;
                default:
                    return "Invalid report type: " + reportType;
            }
        } catch (Exception e) {
            return "Error generating report: " + e.getMessage();
        }
        
        return report.toString();
    }
    
    private String generateAllUsersReport() {
        StringBuilder report = new StringBuilder();
        List<Object[]> users = db.getAllUsers();
        
        report.append("TOTAL USERS: ").append(users.size()).append("\n\n");
        report.append(String.format("%-10s %-30s %-30s %-20s\n", "User ID", "Name", "Email", "Role"));
        report.append("-".repeat(90)).append("\n");
        
        for (Object[] user : users) {
            report.append(String.format("%-10s %-30s %-30s %-20s\n", 
                user[0], user[1], user[2], user[3]));
        }
        
        return report.toString();
    }
    
    private String generateUsersByRoleReport() {
        StringBuilder report = new StringBuilder();
        List<Object[]> users = db.getAllUsers();
        
        java.util.Map<String, Integer> roleCount = new java.util.HashMap<>();
        java.util.Map<String, java.util.List<Object[]>> usersByRole = new java.util.HashMap<>();
        
        for (Object[] user : users) {
            String role = (String) user[3];
            roleCount.put(role, roleCount.getOrDefault(role, 0) + 1);
            usersByRole.computeIfAbsent(role, k -> new java.util.ArrayList<>()).add(user);
        }
        
        report.append("USERS BY ROLE SUMMARY\n");
        report.append("-".repeat(50)).append("\n");
        for (java.util.Map.Entry<String, Integer> entry : roleCount.entrySet()) {
            report.append(String.format("%-20s: %d users\n", entry.getKey(), entry.getValue()));
        }
        report.append("\n");
        
        for (java.util.Map.Entry<String, java.util.List<Object[]>> entry : usersByRole.entrySet()) {
            report.append("\n").append(entry.getKey().toUpperCase()).append(" (").append(entry.getValue().size()).append(")\n");
            report.append("-".repeat(90)).append("\n");
            report.append(String.format("%-10s %-30s %-30s\n", "User ID", "Name", "Email"));
            report.append("-".repeat(70)).append("\n");
            for (Object[] user : entry.getValue()) {
                report.append(String.format("%-10s %-30s %-30s\n", user[0], user[1], user[2]));
            }
        }
        
        return report.toString();
    }
    
    private String generateAllPatientsReport() {
        StringBuilder report = new StringBuilder();
        // Get patients directly from database documents to avoid parsing issues
        List<org.bson.Document> patientDocs = new java.util.ArrayList<>();
        for (org.bson.Document doc : db.getPatientCollection().find()) {
            patientDocs.add(doc);
        }
        
        report.append("TOTAL PATIENTS: ").append(patientDocs.size()).append("\n\n");
        report.append(String.format("%-10s %-30s %-15s %-10s %-20s\n", "Patient ID", "Name", "Gender", "Age", "Contact"));
        report.append("-".repeat(85)).append("\n");
        
        for (org.bson.Document doc : patientDocs) {
            Integer patientID = doc.getInteger("patientID");
            String name = doc.getString("name");
            String gender = doc.getString("gender");
            Integer age = doc.getInteger("age");
            String contact = doc.getString("contactInfo");
            
            report.append(String.format("%-10s %-30s %-15s %-10s %-20s\n", 
                patientID != null ? patientID : "N/A",
                name != null ? name : "N/A",
                gender != null ? gender : "N/A",
                age != null ? age : "N/A",
                contact != null ? contact : "N/A"));
        }
        
        return report.toString();
    }
    
    private String generateAllAppointmentsReport() {
        StringBuilder report = new StringBuilder();
        List<Document> appointments = db.getAllAppointments();
        
        report.append("TOTAL APPOINTMENTS: ").append(appointments.size()).append("\n\n");
        report.append(String.format("%-15s %-30s %-30s %-15s %-15s %-15s\n", 
            "Appointment ID", "Patient", "Doctor", "Date", "Time", "Status"));
        report.append("-".repeat(120)).append("\n");
        
        for (Document doc : appointments) {
            Document patientDoc = (Document) doc.get("patient");
            Document doctorDoc = (Document) doc.get("doctor");
            String patientName = patientDoc != null ? patientDoc.getString("name") : "N/A";
            String doctorName = doctorDoc != null ? doctorDoc.getString("name") : "N/A";
            
            report.append(String.format("%-15s %-30s %-30s %-15s %-15s %-15s\n",
                doc.getInteger("appointmentID"), patientName, doctorName,
                doc.getString("date"), doc.getString("time"), doc.getString("status")));
        }
        
        return report.toString();
    }
    
    private String generateDrugInventoryReport() {
        StringBuilder report = new StringBuilder();
        List<Drug> drugs = db.getAllDrugs();
        
        report.append("TOTAL DRUGS: ").append(drugs.size()).append("\n\n");
        report.append(String.format("%-10s %-30s %-20s %-10s %-15s %-15s\n", 
            "Drug ID", "Name", "Category", "Quantity", "Reorder Level", "Expiry Date"));
        report.append("-".repeat(100)).append("\n");
        
        for (Drug d : drugs) {
            report.append(String.format("%-10s %-30s %-20s %-10s %-15s %-15s\n",
                d.getDrugID(), d.getName(), d.getCategory(), d.getQuantity(), 
                d.getReorderLevel(), d.getExpiryDate()));
        }
        
        return report.toString();
    }
    
    private String generateICURequestsReport() {
        StringBuilder report = new StringBuilder();
        List<Document> icuRequests = db.getAllICURequests();
        
        report.append("TOTAL ICU REQUESTS: ").append(icuRequests.size()).append("\n\n");
        report.append(String.format("%-15s %-30s %-15s %-15s %-15s %-20s\n", 
            "Request ID", "Patient", "Date", "Time", "Urgency", "Diagnosis"));
        report.append("-".repeat(110)).append("\n");
        
        for (Document doc : icuRequests) {
            // Patient is stored as a nested document
            Document patientDoc = (Document) doc.get("patient");
            String patientName = "N/A";
            if (patientDoc != null) {
                patientName = patientDoc.getString("name");
                if (patientName == null || patientName.isEmpty()) {
                    patientName = "N/A";
                }
            }
            
            Integer requestID = doc.getInteger("requestID");
            String date = doc.getString("date");
            String time = doc.getString("time");
            String urgency = doc.getString("urgency");
            String diagnosis = doc.getString("diagnosis");
            
            report.append(String.format("%-15s %-30s %-15s %-15s %-15s %-20s\n",
                requestID != null ? requestID : "N/A",
                patientName,
                date != null ? date : "N/A",
                time != null ? time : "N/A",
                urgency != null ? urgency : "N/A",
                diagnosis != null ? diagnosis : "N/A"));
        }
        
        return report.toString();
    }
    
    private String generateLabTestsReport() {
        StringBuilder report = new StringBuilder();
        List<Document> labTests = db.getAllLabTests();
        
        report.append("TOTAL LAB TESTS: ").append(labTests.size()).append("\n\n");
        report.append(String.format("%-15s %-30s %-30s %-20s %-15s\n", 
            "Test ID", "Patient", "Test Type", "Date", "Status"));
        report.append("-".repeat(110)).append("\n");
        
        for (Document doc : labTests) {
            // Patient name is stored directly, not as nested document
            String patientName = doc.getString("patientName");
            if (patientName == null || patientName.isEmpty()) {
                patientName = "N/A";
            }
            
            // Test type is stored as "type", not "testType"
            String testType = doc.getString("type");
            if (testType == null || testType.isEmpty()) {
                testType = "N/A";
            }
            
            Integer testID = doc.getInteger("testID");
            String date = doc.getString("date");
            String status = doc.getString("status");
            
            report.append(String.format("%-15s %-30s %-30s %-20s %-15s\n",
                testID != null ? testID : "N/A",
                patientName,
                testType,
                date != null ? date : "N/A",
                status != null ? status : "N/A"));
        }
        
        return report.toString();
    }

    public int getAdminID() { return adminID; }

    @Override
    public void print() throws RemoteException {
        System.out.println("Hello World");   
    }

}
