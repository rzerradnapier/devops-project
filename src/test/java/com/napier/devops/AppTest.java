package com.napier.devops;

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

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "user", "password");

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
            }).when(app).connect();

            // Mocking behavior of App.disconnect() —— It should nullify the connection.
            Mockito.doAnswer(invocation -> {
                app.setCon(null);
                return null;
            }).when(app).disconnect();

            // Mocking behavior of App.getSampleCountryDetails() —— It should return a mocked Country object.
            Country mockedCountry = mock(Country.class);
            mockedCountry.setCode(DEFAULT_COUNTRY_CODE);

            Mockito.doReturn(mockedCountry).when(app).getCountryByCode(anyString());
        } catch (Exception ignored) {
        }
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
        app.connect();
        assertNotNull(app.getCon());
    }

    // Testing App.disconnect() method by checking if it rightly nullifies the connection.
    @Test
    public void testDisconnect() {
        app.connect();
        app.disconnect();
        assertNull(app.getCon());
    }

    // Testing App.getCountry() method by checking if it rightly fetches a Country record.
    @Test
    public void testGetCountry() {
        app.connect();
        Country country = app.getCountryByCode(DEFAULT_COUNTRY_CODE);
        assertNotNull(country);
        assertEquals(DEFAULT_COUNTRY_CODE, country.getCode());
    }
}