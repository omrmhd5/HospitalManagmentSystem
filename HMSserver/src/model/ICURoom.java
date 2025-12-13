package model;

import DesignPattern.ChainOfResponsibility.ICURequestChain;
import DesignPattern.Strategy.ICURequest;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import rmi.ICUInterface;
import server.DB;
import DesignPattern.State.ICUState;
import DesignPattern.State.PendingState;

public class ICURoom extends UnicastRemoteObject implements ICUInterface {

    private int roomID;
    private String roomNumber;
    private ICUState state;
    private final DB db;

    // Rana
    public ICURoom(DB db) throws RemoteException {
        this.db = db;
         this.state = new PendingState();
    }

    // Rana
    public ICURoom(int roomID, String roomNumber, DB db) throws RemoteException {
        this.db = db;
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.state = new PendingState();
    }

    // Rana
    public void handleRequest() {
          state.handleRequest(this);
    }

    // Rana
    @Override
    public boolean createICURequest(int requestID, String doctorName, String patientName, 
                                    String date, String time, String urgency, 
                                    String diagnosis, String expectedDuration) throws RemoteException {
        Patient patient = db.getPatientByName(patientName);
        Doctor doctor = db.getDoctorByName(doctorName);
        
        // If doctor not found in database, create a basic doctor object (for cases like "Dr. ICU")
        if (doctor == null) {
            doctor = new Doctor(0, doctorName, "", "", 0, "", "");
        }
        
        // If patient not found, this is an error - should not happen
        if (patient == null) {
            throw new RemoteException("Patient not found: " + patientName);
        }
        
        ICURequest req = new ICURequest(requestID, doctor, patient, date, time, urgency, diagnosis, expectedDuration);
        db.addICURequest(req);
        handleRequest();
        return true;
    }

    // Rana
    @Override
    public String getICURequestsForPatient(int patientID) throws RemoteException {
        List<ICURequest> requests = db.getRequestsForPatient(patientID);
        
        if (requests == null || requests.isEmpty()) {
            return "No ICU requests found for this patient";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("ICU Requests\n");
        result.append("====================\n");
        
        for (ICURequest req : requests) {
            result.append("\n").append(req.toReadableString()).append("\n");
            result.append("--------------------\n");
        }
        
        return result.toString();
    }
    
    @Override
    public String getCurrentICUState() throws RemoteException {
        return getState(); // returns state.getStateName()
    }
    
    // Salma - Chain of Responsibility - Get all pending ICU requests as formatted strings
    @Override
    public List<String> getPendingICURequests() throws RemoteException {
        List<Document> pendingDocs = db.getPendingICURequests();
        List<String> formattedRequests = new ArrayList<>();
        
        for (Document doc : pendingDocs) {
            StringBuilder sb = new StringBuilder();
            Integer requestID = doc.getInteger("requestID");
            Document patientDoc = (Document) doc.get("patient");
            Document doctorDoc = (Document) doc.get("doctor");
            
            String patientName = patientDoc != null ? patientDoc.getString("name") : "N/A";
            String doctorName = doctorDoc != null ? doctorDoc.getString("name") : "N/A";
            String date = doc.getString("date");
            String time = doc.getString("time");
            String urgency = doc.getString("urgency");
            String diagnosis = doc.getString("diagnosis");
            String expectedDuration = doc.getString("expectedDuration");
            String status = doc.getString("status");
            
            sb.append(requestID != null ? requestID : "N/A").append("|")
              .append(patientName != null ? patientName : "N/A").append("|")
              .append(doctorName != null ? doctorName : "N/A").append("|")
              .append(date != null ? date : "N/A").append("|")
              .append(time != null ? time : "N/A").append("|")
              .append(urgency != null ? urgency : "N/A").append("|")
              .append(diagnosis != null ? diagnosis : "N/A").append("|")
              .append(expectedDuration != null ? expectedDuration : "N/A").append("|")
              .append(status != null ? status : "Pending");
            
            formattedRequests.add(sb.toString());
        }
        
        return formattedRequests;
    }
    
    // Chain of Responsibility - Get ICU request details by ID as formatted string
    @Override
    public String getICURequestDetails(int requestID) throws RemoteException {
        Document doc = db.getICURequestByID(requestID);
        if (doc == null) {
            return "ICU Request not found with ID: " + requestID;
        }
        
        Document patientDoc = (Document) doc.get("patient");
        Document doctorDoc = (Document) doc.get("doctor");
        
        StringBuilder result = new StringBuilder();
        result.append("ICU Request Details\n");
        result.append("==================\n");
        result.append("Request ID: ").append(doc.getInteger("requestID")).append("\n");
        result.append("Patient: ").append(patientDoc != null ? patientDoc.getString("name") : "N/A").append("\n");
        result.append("Doctor: ").append(doctorDoc != null ? doctorDoc.getString("name") : "N/A").append("\n");
        result.append("Date: ").append(doc.getString("date")).append("\n");
        result.append("Time: ").append(doc.getString("time")).append("\n");
        result.append("Urgency: ").append(doc.getString("urgency")).append("\n");
        result.append("Diagnosis: ").append(doc.getString("diagnosis")).append("\n");
        result.append("Expected Duration: ").append(doc.getString("expectedDuration")).append("\n");
        result.append("Status: ").append(doc.getString("status")).append("\n");
        
        return result.toString();
    }
    
    // Chain of Responsibility - Update ICU request status
    @Override
    public boolean updateICURequestStatus(int requestID, String status) throws RemoteException {
        return db.updateICURequestStatus(requestID, status);
    }
    
    // Chain of Responsibility - Process request through chain and return result
    @Override
    public String processRequestThroughChain(int requestID, String handlerRole) throws RemoteException {
        Document doc = db.getICURequestByID(requestID);
        if (doc == null) {
            return "ERROR: ICU Request not found with ID: " + requestID;
        }
        
        // Extract request data
        Integer reqID = doc.getInteger("requestID");
        Document patientDoc = (Document) doc.get("patient");
        Document doctorDoc = (Document) doc.get("doctor");
        
        String patientName = patientDoc != null ? patientDoc.getString("name") : "N/A";
        String doctorName = doctorDoc != null ? doctorDoc.getString("name") : "N/A";
        String date = doc.getString("date");
        String time = doc.getString("time");
        String urgency = doc.getString("urgency");
        String diagnosis = doc.getString("diagnosis");
        String expectedDuration = doc.getString("expectedDuration");
        String currentStatus = doc.getString("status");
        
        // Check if already processed
        if (!"Pending".equals(currentStatus)) {
            return "INFO: Request already processed. Current status: " + currentStatus;
        }
        
        // Get patient and doctor objects
        Patient patient = db.getPatientByName(patientName);
        Doctor doctor = db.getDoctorByName(doctorName);
        
        if (patient == null) {
            return "ERROR: Patient not found: " + patientName;
        }
        if (doctor == null) {
            doctor = new Doctor(0, doctorName, "", "", 0, "", "");
        }
        
        // Create ICURequest object
        ICURequest request = new ICURequest(
            reqID, doctor, patient, date, time, urgency, diagnosis, expectedDuration
        );
        
        // Process through Chain of Responsibility
        ICURequestChain chain = new ICURequestChain();
        String handlerThatProcessed = chain.processRequest(request);
        
        String newStatus = request.getStatus();
        String resultMessage;
        
        // Admin has final authority and can approve/reject any urgency type
        if (handlerRole.equalsIgnoreCase("Admin")) {
            if ("Approved".equals(newStatus)) {
                db.updateICURequestStatus(requestID, "Approved");
                resultMessage = "SUCCESS: Request approved by " + handlerRole + " handler.";
            } else {
                db.updateICURequestStatus(requestID, newStatus);
                resultMessage = "PROCESSED: Request status updated to: " + newStatus;
            }
        } else if (handlerThatProcessed != null && handlerThatProcessed.equalsIgnoreCase(handlerRole)) {
            // Check if the current handler role matches the handler that processed the request
            if ("Approved".equals(newStatus)) {
                db.updateICURequestStatus(requestID, "Approved");
                resultMessage = "SUCCESS: Request approved by " + handlerRole + " handler.";
            } else {
                db.updateICURequestStatus(requestID, newStatus);
                resultMessage = "PROCESSED: Request status updated to: " + newStatus;
            }
        } else if (handlerThatProcessed != null && !handlerThatProcessed.equalsIgnoreCase(handlerRole)) {
            resultMessage = "ESCALATE: Request requires " + handlerThatProcessed + 
                          " approval. Urgency: " + urgency + 
                          ". Current user (" + handlerRole + ") cannot approve this request.";
        } else {
            db.updateICURequestStatus(requestID, newStatus);
            resultMessage = "PROCESSED: Request status updated to: " + newStatus;
        }
        
        return resultMessage;
    }
    
    // Chain of Responsibility - Reject request if handler has authority
    @Override
    public String rejectRequestThroughChain(int requestID, String handlerRole) throws RemoteException {
        String checkResult = canRejectRequest(requestID, handlerRole);
        
        if (!checkResult.startsWith("ALLOWED:")) {
            return checkResult;
        }
        
        boolean success = db.updateICURequestStatus(requestID, "Rejected");
        if (success) {
            return "SUCCESS: Request rejected by " + handlerRole + " handler.";
        } else {
            return "ERROR: Failed to update request status in database.";
        }
    }
    
    // Chain of Responsibility - Check if handler can reject request based on urgency
    @Override
    public String canRejectRequest(int requestID, String handlerRole) throws RemoteException {
        Document doc = db.getICURequestByID(requestID);
        if (doc == null) {
            return "ERROR: ICU Request not found with ID: " + requestID;
        }
        
        String urgency = doc.getString("urgency");
        String currentStatus = doc.getString("status");
        
        // Check if already processed
        if (!"Pending".equals(currentStatus)) {
            return "INFO: Request already processed. Current status: " + currentStatus;
        }
        
        // Check if this handler has authority to reject based on urgency
        boolean canReject = false;
        if (handlerRole.equals("Receptionist")) {
            // Receptionist can only reject low and normal urgency requests
            canReject = urgency != null && (urgency.equalsIgnoreCase("low") || 
                                           urgency.equalsIgnoreCase("normal"));
        } else if (handlerRole.equals("Admin")) {
            // Admin can reject any request (has final authority)
            canReject = true;
        }
        
        if (!canReject) {
            // This handler cannot reject - needs escalation
            String requiredHandler = handlerRole.equals("Receptionist") ? "Admin" : "higher authority";
            return "ESCALATE: You cannot reject this request. " +
                   "Urgency level '" + urgency + "' requires " + requiredHandler + " to handle. " +
                   "Please escalate to the appropriate handler instead of rejecting.";
        }
        
        // Handler has authority to reject
        return "ALLOWED: " + handlerRole + " can reject this request.";
    }

    public int getRoomID() { return roomID; }
    public void setRoomID(int roomID) { this.roomID = roomID; }
    
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    
   public void setState(ICUState state) {
    this.state = state;
}

public String getState() {
    return state.getStateName();
}

}
