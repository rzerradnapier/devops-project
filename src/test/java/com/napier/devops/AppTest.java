package com.napier.devops;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import static com.napier.constant.Constant.DEFAULT_COUNTRY_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AppTest {

    // We will be testing methods in the App class
    App app;
    // Connection to the test database
    Connection con;

    // Before running each test, we need to set up mock behaviors.
    @BeforeEach
    public void setUp() {
        // Creating a spy of App class
        app = Mockito.spy(new App());
        // Try-catch block for handling exceptions
        try (MockedStatic<DriverManager> mockDriverManager = Mockito.mockStatic(DriverManager.class)) {
            // Mocking Connection class
            Connection mockCon = mock(Connection.class);
            // When DriverManager.getConnection() is called, return the above mocked Connection object.
            mockDriverManager.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString())).thenReturn(mockCon);

            con = DriverManager.getConnection("jdbc:mysql://db:3306/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "ei:UA@_oSnDZ");

            // Mocking PreparedStatement class
            PreparedStatement ps = mock(PreparedStatement.class);
            // When connection.prepareStatement() is called, return the above mocked PreparedStatement object.
            when(mockCon.prepareStatement(anyString())).thenReturn(ps);
            // When PreparedStatement.execute() is called, return true indicating that the SQL operation was successful.
            when(ps.execute()).thenReturn(true);

            // Mocking behavior of App.connect() —— It should set the connection (to a mocked one for testing).
            Mockito.doAnswer(invocation -> {
                app.setCon(mock(Connection.class));
                return null;
            }).when(app).connect(anyString(),anyInt());

            // Mocking behavior of App.disconnect() —— It should nullify the connection.
            Mockito.doAnswer(invocation -> {
                app.setCon(null);
                return null;
            }).when(app).disconnect();

            // Mocking behavior of App.getCountryByCode() —— It should return a mocked Country object.
            Country mockedCountry = mock(Country.class);
            when(mockedCountry.getCode()).thenReturn(DEFAULT_COUNTRY_CODE);

            Mockito.doReturn(mockedCountry).when(app).getCountryByCode(anyString());
        } catch (Exception ignored) {}
        // Initializing a null connection to test getCon() when connection is null
        app.setCon(null);
    }

    // After testing each method, we need to clean up any changes made to the test database.
    @AfterEach
    public void tearDown() {
        try {
            // Removing the Country record that was created for testing purpose.
            PreparedStatement ps = con.prepareStatement("DELETE FROM country where Code = ?");
            ps.setString(1, DEFAULT_COUNTRY_CODE);
            ps.execute();
            // Closing the test database connection.
            con.close();
        } catch (Exception ignored) {
        }
    }

    // Getters and setters for the Connection object
    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    // Testing App.connect() method by checking if it rightly sets up the connection.
    @Test
    public void testConnect() {
        app.connect("test",1);
        assertNotNull(app.getCon());
    }

    // Testing App.disconnect() method by checking if it rightly nullifies the connection.
    @Test
    public void testDisconnect() {
        app.connect("test",1);
        app.disconnect();
        assertNull(app.getCon());
    }


    // Test printAllCountriesByPopulationLargestToSmallest
    @Test
    public void testPrintAllCountriesByPopulationLargestToSmallest() {
        List<Country> mockedCountries = Arrays.asList(
                new Country().setAll("Code1", "Name1", "Continent1", "Region1", 120, 1),
                new Country().setAll("Code2", "Name2", "Continent2", "Region2", 100, 2),
                new Country().setAll("Code3", "Name3", "Continent3", "Region3", 90, 3));

        Mockito.doReturn(mockedCountries).when(app).getAllCountriesByPopulationLargestToSmallest();

        App.printAllCountriesByPopulationLargestToSmallest(app);
        Mockito.verify(app, Mockito.times(1)).getAllCountriesByPopulationLargestToSmallest();
    }
    // Testing getAllCountriesByPopulationLargestToSmallest method
    // Testing setCon method
    @Test
    public void testGetAllCountriesByPopulationLargestToSmallest() {
        List<Country> mockedCountries = Arrays.asList(
            new Country().setAll("Code1", "Name1", "Continent1", "Region1", 120, 1),
            new Country().setAll("Code2", "Name2", "Continent2", "Region2", 100, 2),
            new Country().setAll("Code3", "Name3", "Continent3", "Region3", 90, 3));

        Mockito.doReturn(mockedCountries).when(app).getAllCountriesByPopulationLargestToSmallest();

        List<Country> countryList = app.getAllCountriesByPopulationLargestToSmallest();
        assertNotNull(countryList);
        assertEquals(3, countryList.size());
        assertEquals("Code1", countryList.get(0).getCode());
        assertEquals("Code2", countryList.get(1).getCode());
        assertEquals("Code3", countryList.get(2).getCode());
    }

    // Testing getCon() method by checking if it correctly returns the connection object.
    @Test
    public void testGetCon() {
        app.connect("test",1);
        assertNotNull(app.getCon());
    }
    // Testing setCon() method by checking if it correctly sets the connection object.
    @Test
    public void testSetCon() {
        Connection connection = mock(Connection.class);
        app.setCon(connection);
        assertEquals(connection, app.getCon());
    }
}
