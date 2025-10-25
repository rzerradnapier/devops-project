package com.napier.devops.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DatabaseConnection class.
 */
public class DatabaseConnectionTest {

    private DatabaseConnection databaseConnection;

    @BeforeEach
    void setUp() {
        databaseConnection = new DatabaseConnection();
    }

    @Test
    void testConstructor() {
        DatabaseConnection dbConn = new DatabaseConnection();
        assertNotNull(dbConn);
        assertNull(dbConn.getConnection());
    }

    @Test
    void testConnectWithValidLocation() {
        // Test with a mock location (this will fail but should not crash)
        String location = "localhost:3306";
        int delay = 1000;
        
        boolean result = databaseConnection.connect(location, delay);
        
        // Should return false since we don't have a real database
        assertFalse(result);
        // Connection should remain null
        assertNull(databaseConnection.getConnection());
    }

    @Test
    void testConnectWithNullLocation() {
        boolean result = databaseConnection.connect(null, 1000);
        
        assertFalse(result);
        assertNull(databaseConnection.getConnection());
    }

    @Test
    void testConnectWithEmptyLocation() {
        boolean result = databaseConnection.connect("", 1000);
        
        assertFalse(result);
        assertNull(databaseConnection.getConnection());
    }

    @Test
    void testConnectWithInvalidLocation() {
        boolean result = databaseConnection.connect("invalid:location", 1000);
        
        assertFalse(result);
        assertNull(databaseConnection.getConnection());
    }

    @Test
    void testConnectWithZeroDelay() {
        boolean result = databaseConnection.connect("localhost:3306", 0);
        
        assertFalse(result);
        assertNull(databaseConnection.getConnection());
    }

    @Test
    void testConnectWithNegativeDelay() {
        // Should throw IllegalArgumentException due to negative delay
        assertThrows(IllegalArgumentException.class, () -> 
            databaseConnection.connect("localhost:3306", -1000));
    }

    @Test
    void testDisconnectWithNullConnection() {
        // Should not throw exception
        assertDoesNotThrow(() -> databaseConnection.disconnect());
    }

    @Test
    void testGetConnectionInitiallyNull() {
        assertNull(databaseConnection.getConnection());
    }

    @Test
    void testMultipleConnectAttempts() {
        // Test multiple connection attempts don't cause issues
        // Note: One might succeed if database is available
        boolean result1 = databaseConnection.connect("localhost:3306", 100);
        boolean result2 = databaseConnection.connect("localhost:3307", 100);
        
        // At least one should be false (can't connect to both different ports)
        // But we can't guarantee both will be false if database is running
        assertTrue(result1 == false || result2 == false || (result1 == false && result2 == false));
    }

    @Test
    void testConnectAndDisconnect() {
        // Test connection and disconnection flow
        boolean connectResult = databaseConnection.connect("localhost:3306", 100);
        assertFalse(connectResult); // Will fail without real DB
        
        // Should not throw exception even with null connection
        assertDoesNotThrow(() -> databaseConnection.disconnect());
        assertNull(databaseConnection.getConnection());
    }
}