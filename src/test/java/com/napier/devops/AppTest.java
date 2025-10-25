package com.napier.devops;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the App class.
 * Tests the integration between App and its services.
 */
public class AppTest {

    @Mock
    private Connection mockConnection;

    private App app;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        app = new App();
        app.setCon(mockConnection);
    }

    /**
     * Test that services are properly initialized when connection is set.
     */
    @Test
    void testServicesInitialization() {
        // Verify that services are initialized after setting connection
        assertNotNull(app.getCityReportService());
        assertNotNull(app.getCountryReportService());
    }

    /**
     * Test that connection is properly set.
     */
    @Test
    void testConnectionSetting() {
        // Verify that connection is set correctly
        assertEquals(mockConnection, app.getCon());
    }
}