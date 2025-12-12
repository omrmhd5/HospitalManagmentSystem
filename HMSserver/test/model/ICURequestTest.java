package model;

import DesignPattern.Strategy.ICURequest;
import org.junit.Test;
import static org.junit.Assert.*;
import java.rmi.RemoteException;

/**
 * JUnit test case for ICU Request
 * Tests ICU request creation and status management (without strategy pattern)
 */
public class ICURequestTest {

    @Test
    public void testApproveICURequest() throws RemoteException {
        System.out.println("=== Testing ICU Request Approval ===");
        
        // Create a doctor
        Doctor doctor = new Doctor(1, "Dr. Smith", "dr.smith@hospital.com", 
                                   "password123", 101, "Cardiology", "Mon-Fri 9-5");
        
        // Create a patient
        Patient patient = new Patient(1, "John Doe", "john@example.com", "password123",
                                      1, "123-456-7890", "Male", 30,
                                      "Hypertension", "1993-01-01", 
                                      "123 Main St", "1234567890", null);
        
        System.out.println("Created ICU request for patient: " + patient.getName());
        System.out.println("Doctor: " + doctor.getName());
        
        // Create an ICU request (without strategy pattern - using direct constructor)
        ICURequest icuRequest = new ICURequest(1001, doctor, patient, 
                                               "2024-12-20", "14:00", "emergency",
                                               "Cardiac arrest", "3 days");
        
        System.out.println("Initial ICU request status: " + icuRequest.getStatus());
        
        // Verify initial status is Pending
        assertEquals("Initial status should be Pending", "Pending", icuRequest.getStatus());
        
        // Approve the request
        icuRequest.approve();
        
        System.out.println("After approval, status: " + icuRequest.getStatus());
        
        // Verify status changed to Approved
        assertEquals("Status should be Approved", "Approved", icuRequest.getStatus());
        
        System.out.println("✓ ICU request approval test passed!\n");
    }
}

