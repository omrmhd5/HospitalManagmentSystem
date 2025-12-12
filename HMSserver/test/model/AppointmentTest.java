package model;

import org.junit.Test;
import static org.junit.Assert.*;
import java.rmi.RemoteException;

/**
 * JUnit test case for Appointment class
 * Tests appointment confirmation functionality
 */
public class AppointmentTest {

    @Test
    public void testConfirmReservation() throws RemoteException {
        System.out.println("=== Testing Appointment Confirmation ===");
        
        // Create a mock DB (null for testing)
        Appointment appointment = new Appointment(null);
        appointment.setStatus("Pending");
        
        System.out.println("Initial appointment status: " + appointment.getStatus());
        
        // Confirm the reservation
        appointment.confirmReservation();
        
        System.out.println("After confirmation, status: " + appointment.getStatus());
        
        // Verify status changed to "Confirmed"
        assertEquals("Status should be 'Confirmed'", "Confirmed", appointment.getStatus());
        
        System.out.println("✓ Appointment confirmation test passed!\n");
    }
}

