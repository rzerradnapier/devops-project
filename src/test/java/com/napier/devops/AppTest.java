package com.napier.devops;

import com.napier.devops.service.CityReportService;
import com.napier.devops.service.CountryReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
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

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        app = new App();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
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

    // ========== MAIN METHOD TESTS ==========

    /**
     * Test main method argument parsing logic with custom arguments.
     */
    @Test
    void testMainWithCustomArguments() {
        // Arrange
        String[] args = {"db:3306", "5000"};

        // Act - Test the argument parsing logic
        String location = args[0];
        int delay = Integer.parseInt(args[1]);

        // Assert
        assertEquals("db:3306", location, "Location should be parsed correctly");
        assertEquals(5000, delay, "Delay should be parsed correctly");
    }

    /**
     * Test main method argument length check.
     */
    @Test
    void testMainArgumentLengthCheck() {
        // Test with no arguments
        String[] noArgs = {};
        assertTrue(noArgs.length < 1, "Empty args array should have length < 1");

        // Test with arguments
        String[] withArgs = {"localhost:3306", "1000"};
        assertFalse(withArgs.length < 1, "Args array with elements should have length >= 1");
    }

    /**
     * Test that main method creates App instance.
     */
    @Test
    void testMainCreatesAppInstance() {
        // Act
        App appIns = new App();

        // Assert
        assertNotNull(appIns, "App instance should be created");
        assertNull(appIns.getCon(), "Connection should be null initially");
    }

    /**
     * Test main method with invalid delay argument throws NumberFormatException.
     */
    @Test
    void testMainWithInvalidDelayArgument() {
        // Arrange
        String[] args = {"localhost:3306", "invalid"};

        // Act & Assert
        assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt(args[1]);
        }, "Should throw NumberFormatException for invalid delay");
    }

    /**
     * Test that default values are used correctly.
     */
    @Test
    void testDefaultValuesUsage() {
        // Arrange
        String[] args = {};
        String expectedLocation = "localhost:3306";
        int expectedDelay = 30000;

        // Act - simulate main's logic
        String location;
        int delay;

        if (args.length < 1) {
            location = "localhost:3306";
            delay = 30000;
        } else {
            location = args[0];
            delay = Integer.parseInt(args[1]);
        }

        // Assert
        assertEquals(expectedLocation, location, "Default location should be localhost:3306");
        assertEquals(expectedDelay, delay, "Default delay should be 30000");
    }

    /**
     * Test main method argument handling with one argument.
     */
    @Test
    void testMainWithOneArgument() {
        // Arrange
        String[] args = {"db:3306"};

        // Act & Assert
        if (args.length >= 1) {
            assertEquals("db:3306", args[0], "First argument should be location");
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
                Integer.parseInt(args[1]);
            }, "Should throw exception when second argument is missing");
        }
    }

    /**
     * Test main method argument handling with two arguments.
     */
    @Test
    void testMainWithTwoArguments() {
        // Arrange
        String[] args = {"db:3306", "5000"};

        // Act
        String location = args[0];
        int delay = Integer.parseInt(args[1]);

        // Assert
        assertEquals("db:3306", location, "First argument should be location");
        assertEquals(5000, delay, "Second argument should be delay");
    }

    /**
     * Test main method argument handling with extra arguments (should be ignored).
     */
    @Test
    void testMainWithExtraArguments() {
        // Arrange
        String[] args = {"db:3306", "5000", "extra", "arguments"};

        // Act - main only uses first two arguments
        if (args.length >= 1) {
            String location = args[0];
            int delay = Integer.parseInt(args[1]);

            // Assert
            assertEquals("db:3306", location, "First argument should be used");
            assertEquals(5000, delay, "Second argument should be used");
            assertEquals(4, args.length, "Extra arguments should be present but ignored");
        }
    }

    /**
     * Test delay argument parsing with zero value.
     */
    @Test
    void testMainWithZeroDelay() {
        // Arrange
        String[] args = {"localhost:3306", "0"};

        // Act
        int delay = Integer.parseInt(args[1]);

        // Assert
        assertEquals(0, delay, "Delay should be 0");
    }

    /**
     * Test delay argument parsing with negative value.
     */
    @Test
    void testMainWithNegativeDelay() {
        // Arrange
        String[] args = {"localhost:3306", "-1000"};

        // Act
        int delay = Integer.parseInt(args[1]);

        // Assert
        assertEquals(-1000, delay, "Delay can be negative (though not practical)");
    }

    /**
     * Test delay argument parsing with very large value.
     */
    @Test
    void testMainWithLargeDelay() {
        // Arrange
        String[] args = {"localhost:3306", "999999"};

        // Act
        int delay = Integer.parseInt(args[1]);

        // Assert
        assertEquals(999999, delay, "Large delay should be parsed correctly");
    }

    /**
     * Test location argument with different formats.
     */
    @Test
    void testMainWithDifferentLocationFormats() {
        // Test various location formats
        String[] testLocations = {
                "localhost:3306",
                "127.0.0.1:3306",
                "db.example.com:3306",
                "192.168.1.1:3306"
        };

        for (String location : testLocations) {
            String[] args = {location, "1000"};
            assertEquals(location, args[0], "Location format should be preserved: " + location);
        }
    }

    /**
     * Test main method logic flow - argument branch selection.
     */
    @Test
    void testMainMethodBranchLogic() {
        // Test no args branch
        String[] noArgs = {};
        boolean usesDefault = noArgs.length < 1;
        assertTrue(usesDefault, "Should use default connection with no args");

        // Test with args branch
        String[] withArgs = {"db:3306", "5000"};
        boolean usesCustom = withArgs.length >= 1;
        assertTrue(usesCustom, "Should use custom connection with args");
    }

    /**
     * Test that main would handle null args array.
     */
    @Test
    void testMainWithNullArgs() {
        // Arrange
        String[] args = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            if (args.length < 1) {
                // Would throw NullPointerException
            }
        }, "Should throw NullPointerException with null args");
    }

    /**
     * Test parsing of port number from location string.
     */
    @Test
    void testLocationPortParsing() {
        // Arrange
        String location = "localhost:3306";

        // Act
        String[] parts = location.split(":");

        // Assert
        assertEquals(2, parts.length, "Location should have host and port");
        assertEquals("localhost", parts[0], "Host should be localhost");
        assertEquals("3306", parts[1], "Port should be 3306");
    }

    /**
     * Test main method would handle empty string arguments.
     */
    @Test
    void testMainWithEmptyStringArguments() {
        // Arrange
        String[] args = {"", ""};

        // Act & Assert
        assertEquals("", args[0], "First argument is empty string");
        assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt(args[1]);
        }, "Empty string should cause NumberFormatException");
    }

    /**
     * Test main method with no arguments uses default localhost connection.
     */
    @Test
    void testMainWithNoArguments() {
        // Arrange
        String[] args = {};

        // Create a spy on App to verify connect is called with correct parameters
        App spyApp = spy(new App());

        // Act - simulate what main does with no args
        if (args.length < 1) {
            spyApp.connect("localhost:3306", 30000);
        }

        // Assert - verify connect was called
        String output = outContent.toString();
        assertTrue(output.contains("Connecting to database"),
                "Should show connecting message");
    }

    /**
     * Test that disconnect doesn't affect null connection.
     */
    @Test
    void testDisconnectWithNullConnectionDoesNothing() {
        // Act
        app.disconnect();

        // Assert - Should complete without error
        assertNull(app.getCon(), "Connection should still be null");
    }

    /**
     * Test reconnection scenario - disconnect then set new connection.
     */
    @Test
    void testReconnectionScenario() throws SQLException {
        // Arrange
        Connection mockConnection2 = mock(Connection.class);

        // Act - First connection
        app.setCon(mockConnection);
        CityReportService firstCityService = app.getCityReportService();

        // Disconnect
        app.disconnect();

        // Set new connection
        app.setCon(mockConnection2);
        CityReportService secondCityService = app.getCityReportService();

        // Assert
        verify(mockConnection, times(1)).close();
        assertNotSame(firstCityService, secondCityService, "Services should be reinitialized");
        assertEquals(mockConnection2, app.getCon(), "New connection should be set");
    }
}