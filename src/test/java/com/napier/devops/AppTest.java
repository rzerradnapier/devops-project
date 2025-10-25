package com.napier.devops;

import com.napier.devops.database.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the main App class.
 * These tests focus on public methods and behavior without complex mocking.
 */
public class AppTest {

    private App app;
    private DatabaseConnection dbConnection;

    @BeforeEach
    public void setUp() {
        app = new App();
        dbConnection = new DatabaseConnection();
    }

    @Test
    public void testAppInstantiation() {
        App testApp = new App();
        assertNotNull(testApp);
    }

    @Test
    public void testGetDbConnection() {
        app.setDbConnection(dbConnection);
        assertEquals(dbConnection, app.getDbConnection());
    }

    @Test
    public void testSetDbConnection() {
        app.setDbConnection(dbConnection);
        assertEquals(dbConnection, app.getDbConnection());
    }

    @Test
    public void testSetDbConnectionWithNull() {
        app.setDbConnection(null);
        assertNull(app.getDbConnection());
    }

    @Test
    public void testAppStateManagement() {
        assertNull(app.getDbConnection());
        
        app.setDbConnection(dbConnection);
        assertNotNull(app.getDbConnection());
        
        app.setDbConnection(null);
        assertNull(app.getDbConnection());
    }

    @Test
    public void testAppWithRealDatabaseConnection() {
        DatabaseConnection realDbConnection = new DatabaseConnection();
        app.setDbConnection(realDbConnection);
        
        assertEquals(realDbConnection, app.getDbConnection());
        assertNotNull(app.getDbConnection());
    }

    @Test
    public void testAppCleanupScenario() {
        app.setDbConnection(dbConnection);
        
        // Simulate cleanup
        if (app.getDbConnection() != null) {
            app.getDbConnection().disconnect();
        }
        
        // Should not throw exception
        assertDoesNotThrow(() -> app.getDbConnection().disconnect());
    }

    @Test
    public void testAppMethodsExist() {
        // Test that App has expected methods
        assertDoesNotThrow(() -> {
            App testApp = new App();
            assertNotNull(testApp);
            assertTrue(App.class.getDeclaredMethods().length > 0);
        });
    }

    @Test
    public void testAppMultipleInstances() {
        App app1 = new App();
        App app2 = new App();
        
        assertNotNull(app1);
        assertNotNull(app2);
        assertNotSame(app1, app2);
        
        // Test independent state
        app1.setDbConnection(dbConnection);
        app2.setDbConnection(null);
        
        assertNotNull(app1.getDbConnection());
        assertNull(app2.getDbConnection());
    }

    @Test
    public void testAppDatabaseConnectionLifecycle() {
        // Test full lifecycle
        assertNull(app.getDbConnection());
        
        DatabaseConnection conn1 = new DatabaseConnection();
        app.setDbConnection(conn1);
        assertEquals(conn1, app.getDbConnection());
        
        DatabaseConnection conn2 = new DatabaseConnection();
        app.setDbConnection(conn2);
        assertEquals(conn2, app.getDbConnection());
        
        app.setDbConnection(null);
        assertNull(app.getDbConnection());
    }

    @Test
    public void testAppErrorHandling() {
        // Test that App handles various scenarios gracefully
        assertDoesNotThrow(() -> {
            app.setDbConnection(null);
            app.setDbConnection(new DatabaseConnection());
            app.setDbConnection(null);
        });
    }
}