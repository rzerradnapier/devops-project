package com.napier.devops.config;

import com.napier.devops.database.DatabaseConnection;
import com.napier.devops.ui.MenuUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ApplicationFactory class.
 */
public class ApplicationFactoryTest {

    private ApplicationFactory applicationFactory;
    private DatabaseConnection databaseConnection;

    @BeforeEach
    void setUp() {
        databaseConnection = new DatabaseConnection();
        applicationFactory = new ApplicationFactory(databaseConnection);
    }

    @Test
    void testConstructorWithValidConnection() {
        DatabaseConnection dbConn = new DatabaseConnection();
        ApplicationFactory factory = new ApplicationFactory(dbConn);
        assertNotNull(factory);
    }

    @Test
    void testConstructorWithNullConnection() {
        ApplicationFactory factory = new ApplicationFactory(null);
        assertNotNull(factory);
    }

    @Test
    void testCreateApplication() {
        MenuUI menuUI = applicationFactory.createApplication();
        
        assertNotNull(menuUI);
    }

    @Test
    void testCreateApplicationWithNullConnection() {
        ApplicationFactory factory = new ApplicationFactory(null);
        MenuUI menuUI = factory.createApplication();
        
        assertNotNull(menuUI);
    }

    @Test
    void testMultipleApplicationCreation() {
        // Test that multiple calls work
        MenuUI menuUI1 = applicationFactory.createApplication();
        MenuUI menuUI2 = applicationFactory.createApplication();
        
        assertNotNull(menuUI1);
        assertNotNull(menuUI2);
        // Should be different instances
        assertNotSame(menuUI1, menuUI2);
    }
}