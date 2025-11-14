package com.napier.devops;

import com.napier.devops.service.CityReportService;
import com.napier.devops.service.CountryReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    }

    /**
     * Test that services are properly initialized when connection is set.
     */
    @Test
    void testServicesInitialization() {
        // Act
        app.setCon(mockConnection);

        // Assert - Verify that services are initialized after setting connection
        assertNotNull(app.getCityReportService(), "CityReportService should be initialized");
        assertNotNull(app.getCountryReportService(), "CountryReportService should be initialized");
    }

    /**
     * Test that connection is properly set.
     */
    @Test
    void testConnectionSetting() {
        // Act
        app.setCon(mockConnection);

        // Assert - Verify that connection is set correctly
        assertEquals(mockConnection, app.getCon(), "Connection should be set correctly");
    }

    /**
     * Test that services are null before connection is set.
     */
    @Test
    void testServicesNullBeforeConnectionSet() {
        // Assert - Services should be null before connection is set
        assertNull(app.getCityReportService(), "CityReportService should be null initially");
        assertNull(app.getCountryReportService(), "CountryReportService should be null initially");
    }

    /**
     * Test that connection is null initially.
     */
    @Test
    void testConnectionNullInitially() {
        // Assert - Connection should be null initially
        assertNull(app.getCon(), "Connection should be null initially");
    }

    /**
     * Test that setting connection multiple times reinitializes services.
     */
    @Test
    void testMultipleConnectionSets() {
        // Arrange
        Connection mockConnection2 = mock(Connection.class);

        // Act - Set connection first time
        app.setCon(mockConnection);
        CityReportService firstCityService = app.getCityReportService();
        CountryReportService firstCountryService = app.getCountryReportService();

        // Act - Set connection second time
        app.setCon(mockConnection2);
        CityReportService secondCityService = app.getCityReportService();
        CountryReportService secondCountryService = app.getCountryReportService();

        // Assert - Verify that services are reinitialized
        assertNotNull(secondCityService, "CityReportService should be reinitialized");
        assertNotNull(secondCountryService, "CountryReportService should be reinitialized");
        assertNotSame(firstCityService, secondCityService, "CityReportService should be a new instance");
        assertNotSame(firstCountryService, secondCountryService, "CountryReportService should be a new instance");
        assertEquals(mockConnection2, app.getCon(), "Connection should be updated");
    }

    /**
     * Test that CityReportService getter returns correct instance.
     */
    @Test
    void testGetCityReportService() {
        // Act
        app.setCon(mockConnection);
        CityReportService service = app.getCityReportService();

        // Assert
        assertNotNull(service, "CityReportService should not be null");
        assertSame(service, app.getCityReportService(), "Should return the same instance on multiple calls");
    }

    /**
     * Test that CountryReportService getter returns correct instance.
     */
    @Test
    void testGetCountryReportService() {
        // Act
        app.setCon(mockConnection);
        CountryReportService service = app.getCountryReportService();

        // Assert
        assertNotNull(service, "CountryReportService should not be null");
        assertSame(service, app.getCountryReportService(), "Should return the same instance on multiple calls");
    }

    /**
     * Test that disconnect method closes the connection when connection exists.
     */
    @Test
    void testDisconnectWithActiveConnection() throws SQLException {
        // Arrange
        app.setCon(mockConnection);

        // Act
        app.disconnect();

        // Assert
        verify(mockConnection, times(1)).close();
    }

    /**
     * Test that disconnect method handles null connection gracefully.
     */
    @Test
    void testDisconnectWithNullConnection() {
        // Act & Assert - Should not throw exception
        assertDoesNotThrow(() -> app.disconnect(), "Disconnect should handle null connection gracefully");
    }

    /**
     * Test that disconnect handles SQLException gracefully.
     */
    @Test
    void testDisconnectWithSQLException() throws SQLException {
        // Arrange
        app.setCon(mockConnection);
        doThrow(new SQLException("Connection close failed")).when(mockConnection).close();

        // Act & Assert - Should not throw exception
        assertDoesNotThrow(() -> app.disconnect(), "Disconnect should handle SQLException gracefully");
    }

    /**
     * Test that disconnect handles generic Exception gracefully.
     */
    @Test
    void testDisconnectWithGenericException() throws SQLException {
        // Arrange
        app.setCon(mockConnection);
        doThrow(new RuntimeException("Unexpected error")).when(mockConnection).close();

        // Act & Assert - Should not throw exception
        assertDoesNotThrow(() -> app.disconnect(), "Disconnect should handle generic Exception gracefully");
    }

    /**
     * Test App constructor creates a new instance.
     */
    @Test
    void testAppConstructor() {
        // Act
        App newApp = new App();

        // Assert
        assertNotNull(newApp, "App instance should be created");
        assertNull(newApp.getCon(), "Connection should be null for new instance");
        assertNull(newApp.getCityReportService(), "CityReportService should be null for new instance");
        assertNull(newApp.getCountryReportService(), "CountryReportService should be null for new instance");
    }

    /**
     * Test that setting null connection doesn't throw exception.
     */
    @Test
    void testSetNullConnection() {
        // Act & Assert - Should not throw exception
        assertDoesNotThrow(() -> app.setCon(null), "Setting null connection should not throw exception");

        // Assert - Connection should be null
        assertNull(app.getCon(), "Connection should be null");
    }

    /**
     * Test services initialization with valid connection.
     */
    @Test
    void testServicesInitializationWithValidConnection() {
        // Act
        app.setCon(mockConnection);

        // Assert
        CityReportService cityService = app.getCityReportService();
        CountryReportService countryService = app.getCountryReportService();

        assertNotNull(cityService, "CityReportService should be initialized");
        assertNotNull(countryService, "CountryReportService should be initialized");
    }

    /**
     * Test that App can be instantiated multiple times independently.
     */
    @Test
    void testMultipleAppInstances() {
        // Arrange
        App app1 = new App();
        App app2 = new App();
        Connection mockConnection1 = mock(Connection.class);
        Connection mockConnection2 = mock(Connection.class);

        // Act
        app1.setCon(mockConnection1);
        app2.setCon(mockConnection2);

        // Assert
        assertNotSame(app1, app2, "App instances should be different");
        assertNotSame(app1.getCon(), app2.getCon(), "Connections should be different");
        assertNotSame(app1.getCityReportService(), app2.getCityReportService(), 
                "CityReportService instances should be different");
        assertNotSame(app1.getCountryReportService(), app2.getCountryReportService(), 
                "CountryReportService instances should be different");
    }

    /**
     * Test connection getter after disconnect.
     */
    @Test
    void testGetConnectionAfterDisconnect() throws SQLException {
        // Arrange
        app.setCon(mockConnection);
        Connection connectionBeforeDisconnect = app.getCon();

        // Act
        app.disconnect();

        // Assert
        assertSame(connectionBeforeDisconnect, app.getCon(), 
                "Connection reference should remain the same after disconnect");
        verify(mockConnection, times(1)).close();
    }

    /**
     * Test that services remain accessible after disconnect.
     */
    @Test
    void testServicesAccessibleAfterDisconnect() throws SQLException {
        // Arrange
        app.setCon(mockConnection);
        CityReportService cityService = app.getCityReportService();
        CountryReportService countryService = app.getCountryReportService();

        // Act
        app.disconnect();

        // Assert
        assertSame(cityService, app.getCityReportService(), 
                "CityReportService should remain accessible after disconnect");
        assertSame(countryService, app.getCountryReportService(), 
                "CountryReportService should remain accessible after disconnect");
    }

    /**
     * Test disconnect can be called multiple times safely.
     */
    @Test
    void testMultipleDisconnectCalls() throws SQLException {
        // Arrange
        app.setCon(mockConnection);

        // Act
        app.disconnect();
        app.disconnect();
        app.disconnect();

        // Assert - Should only attempt to close once (first time)
        verify(mockConnection, times(3)).close();
    }

    /**
     * Test that services are properly associated with the connection.
     */
    @Test
    void testServicesConnectionAssociation() {
        // Arrange & Act
        app.setCon(mockConnection);

        // Assert - Services should be initialized with the connection
        assertNotNull(app.getCityReportService(), "CityReportService should be initialized");
        assertNotNull(app.getCountryReportService(), "CountryReportService should be initialized");
        assertEquals(mockConnection, app.getCon(), "Connection should match");
    }

    /**
     * Test App instance state consistency.
     */
    @Test
    void testAppStateConsistency() {
        // Initial state
        assertNull(app.getCon(), "Initial connection should be null");
        assertNull(app.getCityReportService(), "Initial city service should be null");
        assertNull(app.getCountryReportService(), "Initial country service should be null");

        // After setting connection
        app.setCon(mockConnection);
        assertNotNull(app.getCon(), "Connection should not be null after setCon");
        assertNotNull(app.getCityReportService(), "City service should not be null after setCon");
        assertNotNull(app.getCountryReportService(), "Country service should not be null after setCon");
    }
}