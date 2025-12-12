package model;

import org.junit.Test;
import static org.junit.Assert.*;
import java.rmi.RemoteException;

/**
 * JUnit test case for User class
 * Tests the login functionality with correct credentials
 */
public class UserTest {

    @Test
    public void testLoginSuccess() throws RemoteException {
        System.out.println("=== Testing User Login ===");
        
        // Create a test user (using Patient as concrete implementation)
        Patient user = new Patient(1, "John Doe", "john@example.com", "password123", 
                                   1, "contact", "Male", 30, 
                                   "No history", "1993-01-01", "123 Main St", "1234567890", null);
        
        System.out.println("Created user: " + user.getName() + " with email: " + user.getEmail());
        
        // Test successful login with correct credentials
        boolean result = user.login("john@example.com", "password123");
        
        System.out.println("Login attempt result: " + (result ? "SUCCESS" : "FAILED"));
        assertTrue("Login should succeed with correct credentials", result);
        
        System.out.println("✓ Login test passed!\n");
    }
}
